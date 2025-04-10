package org.kgusarov.krono.locales.ru.parsers

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.kgusarov.krono.KronoComponents
import org.kgusarov.krono.KronoUnit
import org.kgusarov.krono.ParsedComponents
import org.kgusarov.krono.ParserResult
import org.kgusarov.krono.ParserResultFactory
import org.kgusarov.krono.ParsingContext
import org.kgusarov.krono.RegExpMatchArray
import org.kgusarov.krono.common.evening
import org.kgusarov.krono.common.lastNight
import org.kgusarov.krono.common.midnight
import org.kgusarov.krono.common.morning
import org.kgusarov.krono.common.noon
import org.kgusarov.krono.common.now
import org.kgusarov.krono.common.yesterdayEvening
import org.kgusarov.krono.extensions.add
import org.kgusarov.krono.extensions.assignSimilarDate

@SuppressFBWarnings("EI_EXPOSE_REP")
class RuCasualTimeParser : AbstractRuParserWithLeftBoundaryChecking() {
    override fun innerPatternString(context: ParsingContext) = PATTERN

    override fun innerExtract(
        context: ParsingContext,
        match: RegExpMatchArray,
    ): ParserResult {
        val lowerText = match[0]!!.lowercase()
        val components: ParsedComponents =
            when {
                lowerText == "сейчас" -> now(context.reference)
                lowerText == "вечером" || lowerText == "вечера" -> evening(context.reference)
                lowerText.endsWith("утром") || lowerText.endsWith("утра") -> morning(context.reference)
                lowerText.matches(NOON_PATTERN) -> noon(context.reference)
                lowerText.matches(LAST_NIGHT_PATTERN) -> lastNight(context.reference)
                lowerText.matches(LAST_EVENING_PATTERN) -> yesterdayEvening(context.reference)
                lowerText.matches(NEXT_NIGHT_PATTERN) -> {
                    val daysToAdd = if (context.instant.hour < 22) 1 else 2
                    val targetDate = context.instant.add(KronoUnit.Day, daysToAdd)
                    val component = context.createParsingComponents()

                    targetDate.assignSimilarDate(component)
                    component.imply(KronoComponents.Hour, 0)
                }

                lowerText.matches(MIDNIGHT_PATTERN) || lowerText.endsWith("ночью") -> midnight(context.reference)
                else -> context.createParsingComponents()
            }

        return ParserResultFactory(components)
    }

    companion object {
        @JvmStatic
        private val PATTERN =
            "(сейчас|прошлым\\s*вечером|прошлой\\s*ночью|следующей\\s*ночью|" +
                "сегодня\\s*ночью|этой\\s*ночью|ночью|этим утром|утром|утра|в\\s*полдень|вечером|вечера|в\\s*полночь)"

        @JvmStatic
        private val NOON_PATTERN = Regex("в\\s*полдень")

        @JvmStatic
        private val LAST_NIGHT_PATTERN = Regex("прошлой\\s*ночью")

        @JvmStatic
        private val LAST_EVENING_PATTERN = Regex("прошлым\\s*вечером")

        @JvmStatic
        private val NEXT_NIGHT_PATTERN = Regex("следующей\\s*ночью")

        @JvmStatic
        private val MIDNIGHT_PATTERN = Regex("в\\s*полночь")
    }
}
