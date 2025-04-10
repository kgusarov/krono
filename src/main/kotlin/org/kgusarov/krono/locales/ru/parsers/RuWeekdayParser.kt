package org.kgusarov.krono.locales.ru.parsers

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.kgusarov.krono.ParserResult
import org.kgusarov.krono.ParserResultFactory
import org.kgusarov.krono.ParsingContext
import org.kgusarov.krono.RegExpMatchArray
import org.kgusarov.krono.common.calculation.createParsingComponentsAtWeekday
import org.kgusarov.krono.common.parsers.WeekdayParserSupport
import org.kgusarov.krono.locales.ru.RuConstants
import org.kgusarov.krono.utils.matchAnyPattern

@SuppressFBWarnings("EI_EXPOSE_REP")
class RuWeekdayParser : AbstractRuParserWithLeftRightBoundaryChecking(), WeekdayParserSupport {
    override fun innerPatternString(context: ParsingContext) = PATTERN

    override fun innerExtract(
        context: ParsingContext,
        match: RegExpMatchArray,
    ): ParserResult {
        val dayOfWeek = match[WEEKDAY_GROUP]!!.lowercase()
        val weekday = RuConstants.WEEKDAY_DICTIONARY[dayOfWeek]!!
        val modifierWord = getModifierWord(match, PREFIX_GROUP, POSTFIX_GROUP)
        val modifier: String? =
            when (modifierWord) {
                "прошлый", "прошлую", "прошлой" -> "last"
                "следующий", "следующую", "следующей", "следующего" -> "next"
                "этот", "эту", "этой" -> "this"
                else -> null
            }

        return ParserResultFactory(createParsingComponentsAtWeekday(context.reference, weekday, modifier))
    }

    companion object {
        @JvmStatic
        private val PATTERN =
            "(?:(?:,|\\(|（)\\s*)?" +
                "(?:в\\s*?)?" +
                "(?:(эту|этот|прошлый|прошлую|следующий|следующую|следующего)\\s*)?" +
                "(${matchAnyPattern(RuConstants.WEEKDAY_DICTIONARY)})" +
                "(?:\\s*(?:,|\\)|）))?" +
                "(?:\\s*на\\s*(этой|прошлой|следующей)\\s*неделе)?"

        private const val PREFIX_GROUP = 1
        private const val WEEKDAY_GROUP = 2
        private const val POSTFIX_GROUP = 3
    }
}
