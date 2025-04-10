package org.kgusarov.krono.locales.en.parsers

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.kgusarov.krono.ParserResult
import org.kgusarov.krono.ParsingContext
import org.kgusarov.krono.RegExpMatchArray
import org.kgusarov.krono.TextOrEndIndexInputFactory
import org.kgusarov.krono.common.parsers.AbstractParserWithWordBoundaryChecking
import org.kgusarov.krono.common.parsers.MonthNameParserSupport
import org.kgusarov.krono.extensions.plus
import org.kgusarov.krono.locales.en.EnConstants
import org.kgusarov.krono.locales.en.parseYear
import org.kgusarov.krono.utils.matchAnyPattern

@SuppressFBWarnings("EI_EXPOSE_REP")
class EnMonthNameParser : AbstractParserWithWordBoundaryChecking(), MonthNameParserSupport {
    override fun innerPattern(context: ParsingContext) = PATTERN

    override fun innerExtract(
        context: ParsingContext,
        match: RegExpMatchArray,
    ): ParserResult? {
        val monthName = match[MONTH_NAME_GROUP]!!.lowercase()
        val matched = match[0]!!
        if (matched.length <= 3 && !EnConstants.FULL_MONTH_NAME_DICTIONARY.containsKey(monthName)) {
            return null
        }

        val result =
            context.createParsingResult(
                match.index + match[PREFIX_GROUP]!!.length,
                TextOrEndIndexInputFactory(match.index + matched.length),
            )

        result.start.addTag("parser/ENMonthNameParser")
        return applyMonth(
            context,
            match,
            result,
            monthName,
            EnConstants.MONTH_DICTIONARY,
            YEAR_GROUP,
            ::parseYear,
        )
    }

    @Suppress(
        "RegExpRedundantEscape",
        "RegExpSimplifiable",
        "RegExpSingleCharAlternation",
        "RegExpUnnecessaryNonCapturingGroup",
    )
    companion object {
        @JvmStatic
        private val PATTERN =
            Regex(
                "((?:in)\\s*)?" +
                    "(${matchAnyPattern(EnConstants.MONTH_DICTIONARY)})" +
                    "\\s*" +
                    "(?:" +
                    "[,-]?\\s*(${EnConstants.YEAR_PATTERN})?" +
                    ")?" +
                    "(?=[^\\s\\w]|\\s+[^0-9]|\\s+\$|$)",
                RegexOption.IGNORE_CASE,
            )

        private const val PREFIX_GROUP = 1
        private const val MONTH_NAME_GROUP = 2
        private const val YEAR_GROUP = 3
    }
}
