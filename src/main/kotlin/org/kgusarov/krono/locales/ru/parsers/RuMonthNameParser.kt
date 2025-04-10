package org.kgusarov.krono.locales.ru.parsers

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.kgusarov.krono.ParserResult
import org.kgusarov.krono.ParsingContext
import org.kgusarov.krono.RegExpMatchArray
import org.kgusarov.krono.TextOrEndIndexInputFactory
import org.kgusarov.krono.common.parsers.MonthNameParserSupport
import org.kgusarov.krono.locales.ru.RuConstants
import org.kgusarov.krono.locales.ru.parseYear
import org.kgusarov.krono.utils.matchAnyPattern

@SuppressFBWarnings("EI_EXPOSE_REP")
class RuMonthNameParser : AbstractRuParserWithLeftBoundaryChecking(), MonthNameParserSupport {
    override fun innerPatternString(context: ParsingContext) = PATTERN

    override fun innerExtract(
        context: ParsingContext,
        match: RegExpMatchArray,
    ): ParserResult? {
        val monthName = match[MONTH_NAME_GROUP]!!.lowercase()
        val matched = match[0]!!
        val matchLength = matched.length
        if ((matchLength <= 3) && !RuConstants.FULL_MONTH_NAME_DICTIONARY.containsKey(monthName)) {
            return null
        }

        val result =
            context.createParsingResult(
                match.index!!,
                TextOrEndIndexInputFactory(match.index!! + matchLength),
            )

        return applyMonth(
            context,
            match,
            result,
            monthName,
            RuConstants.MONTH_DICTIONARY,
            YEAR_GROUP,
            ::parseYear,
        )
    }

    companion object {
        @JvmStatic
        private val PATTERN =
            "((?:в)\\s*)?" +
                "(${matchAnyPattern(RuConstants.MONTH_DICTIONARY)})" +
                "\\s*" +
                "(?:" +
                "[,-]?\\s*(${RuConstants.YEAR_PATTERN})?" +
                ")?" +
                "(?=[^\\s\\w]|\\s+[^0-9]|\\s+$|$)"

        private val MONTH_NAME_GROUP = 2
        private val YEAR_GROUP = 3
    }
}
