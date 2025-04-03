package org.kgusarov.krono.locales.nl.parsers

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.kgusarov.krono.KronoComponents
import org.kgusarov.krono.KronoMeridiem
import org.kgusarov.krono.ParserResult
import org.kgusarov.krono.ParserResultFactory
import org.kgusarov.krono.ParsingContext
import org.kgusarov.krono.RegExpMatchArray
import org.kgusarov.krono.common.parsers.AbstractParserWithWordBoundaryChecking
import org.kgusarov.krono.extensions.assignTheNextDay

@SuppressFBWarnings("EI_EXPOSE_REP")
class NlCasualTimeParser : AbstractParserWithWordBoundaryChecking() {
    override fun innerPattern(context: ParsingContext) = PATTERN

    override fun innerExtract(
        context: ParsingContext,
        match: RegExpMatchArray,
    ): ParserResult {
        val targetDate = context.instant
        val component = context.createParsingComponents()

        if (match[DAY_GROUP] == "deze") {
            component.assign(KronoComponents.Day, targetDate.dayOfMonth)
            component.assign(KronoComponents.Month, targetDate.monthValue)
            component.assign(KronoComponents.Year, targetDate.year)
        }

        when (match[MOMENT_GROUP]!!.lowercase()) {
            "namiddag", "'s namiddags" -> {
                component.imply(KronoComponents.Meridiem, KronoMeridiem.PM)
                component.imply(KronoComponents.Hour, 15)
            }

            "avond", "'s avonds'" -> {
                component.imply(KronoComponents.Meridiem, KronoMeridiem.PM)
                component.imply(KronoComponents.Hour, 20)
            }

            "middernacht" -> {
                targetDate.assignTheNextDay(component)
                component.imply(KronoComponents.Hour, 0)
                component.imply(KronoComponents.Minute, 0)
                component.imply(KronoComponents.Second, 0)
            }

            "ochtend", "'s ochtends" -> {
                component.imply(KronoComponents.Meridiem, KronoMeridiem.AM)
                component.imply(KronoComponents.Hour, 6)
            }

            "middag", "'s middags" -> {
                component.imply(KronoComponents.Meridiem, KronoMeridiem.AM)
                component.imply(KronoComponents.Hour, 12)
            }
        }

        return ParserResultFactory(component)
    }

    companion object {
        @JvmStatic
        private val PATTERN =
            Regex(
                "(deze)?\\s*(namiddag|avond|middernacht|ochtend|middag|'s middags|'s avonds|'s ochtends)(?=\\W|$)",
                RegexOption.IGNORE_CASE,
            )

        private const val DAY_GROUP = 1
        private const val MOMENT_GROUP = 2
    }
}
