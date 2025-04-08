package org.kgusarov.krono.locales.nl.parsers

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.kgusarov.krono.KronoComponents
import org.kgusarov.krono.KronoMeridiem
import org.kgusarov.krono.ParserResult
import org.kgusarov.krono.ParserResultFactory
import org.kgusarov.krono.ParsingContext
import org.kgusarov.krono.RegExpMatchArray
import org.kgusarov.krono.common.parsers.AbstractParserWithWordBoundaryChecking
import org.kgusarov.krono.extensions.assignSimilarDate
import org.kgusarov.krono.extensions.assignTheNextDay

@SuppressFBWarnings("EI_EXPOSE_REP")
class NlCasualDateTimeParser : AbstractParserWithWordBoundaryChecking() {
    override fun innerPattern(context: ParsingContext) = PATTERN

    override fun innerExtract(
        context: ParsingContext,
        match: RegExpMatchArray,
    ): ParserResult {
        val dateText = match[DATE_GROUP]!!.lowercase()
        val timeText = match[TIME_OF_DAY_GROUP]!!.lowercase()
        val component = context.createParsingComponents()
        val targetDate = context.instant

        when (dateText) {
            "gisteren" -> targetDate.minusDays(1).assignSimilarDate(component)
            "van" -> targetDate.assignSimilarDate(component)
            "morgen" -> targetDate.assignTheNextDay(component)
        }

        when (timeText) {
            "ochtend" -> {
                component.imply(KronoComponents.Meridiem, KronoMeridiem.AM)
                component.imply(KronoComponents.Hour, 6)
            }

            "middag" -> {
                component.imply(KronoComponents.Meridiem, KronoMeridiem.AM)
                component.imply(KronoComponents.Hour, 12)
            }

            "namiddag" -> {
                component.imply(KronoComponents.Meridiem, KronoMeridiem.PM)
                component.imply(KronoComponents.Hour, 15)
            }

            "avond" -> {
                component.imply(KronoComponents.Meridiem, KronoMeridiem.PM)
                component.imply(KronoComponents.Hour, 20)
            }
        }

        return ParserResultFactory(component)
    }

    companion object {
        @JvmStatic
        private val PATTERN =
            Regex(
                "(gisteren|morgen|van)(ochtend|middag|namiddag|avond|nacht)(?=\\W|$)",
                RegexOption.IGNORE_CASE,
            )

        private const val DATE_GROUP = 1
        private const val TIME_OF_DAY_GROUP = 2
    }
}
