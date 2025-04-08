package org.kgusarov.krono.locales.ru.parsers

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.kgusarov.krono.KronoComponents
import org.kgusarov.krono.ParserResult
import org.kgusarov.krono.ParserResultFactory
import org.kgusarov.krono.ParsingContext
import org.kgusarov.krono.RegExpMatchArray
import org.kgusarov.krono.TextOrEndIndexInputFactory
import org.kgusarov.krono.calculation.findYearClosestToRef
import org.kgusarov.krono.extensions.not
import org.kgusarov.krono.extensions.plus
import org.kgusarov.krono.locales.ru.RuConstants
import org.kgusarov.krono.locales.ru.parseOrdinalNumberPattern
import org.kgusarov.krono.locales.ru.parseYear
import org.kgusarov.krono.utils.matchAnyPattern

@SuppressFBWarnings("EI_EXPOSE_REP")
class RuMonthNameLittleEndianParser : AbstractRuParserWithLeftRightBoundaryChecking() {
    override fun innerExtract(
        context: ParsingContext,
        match: RegExpMatchArray,
    ): ParserResult? {
        val result = context.createParsingResult(match.index!!, TextOrEndIndexInputFactory(match[0]!!))
        val month = RuConstants.MONTH_DICTIONARY[match[MONTH_NAME_GROUP]!!.lowercase()]!!
        val day = parseOrdinalNumberPattern(match[DATE_GROUP]!!)
        if (day > 31) {
            match.index += match[DATE_GROUP]!!.length
            return null
        }

        result.start.assign(KronoComponents.Month, month)
        result.start.assign(KronoComponents.Day, day)

        if (!match[YEAR_GROUP]) {
            val year = findYearClosestToRef(context.instant, day, month)
            result.start.imply(KronoComponents.Year, year)
        } else {
            val yearNumber = parseYear(match[YEAR_GROUP]!!)
            result.start.assign(KronoComponents.Year, yearNumber)
        }

        if (!match[DATE_TO_GROUP].isNullOrEmpty()) {
            val endDate = parseOrdinalNumberPattern(match[DATE_TO_GROUP]!!)
            result.end = result.start.copy()
            result.end!!.assign(KronoComponents.Day, endDate)
        }

        return ParserResultFactory(result)
    }

    override fun innerPatternString(context: ParsingContext) = PATTERN

    companion object {
        @JvmStatic
        private val PATTERN =
            "(?:с)?\\s*(${RuConstants.ORDINAL_NUMBER_PATTERN})" +
                "(?:" +
                "\\s{0,3}(?:по|-|–|до)?\\s{0,3}" +
                "(${RuConstants.ORDINAL_NUMBER_PATTERN})" +
                ")?" +
                "(?:-|\\/|\\s{0,3}(?:of)?\\s{0,3})" +
                "(${matchAnyPattern(RuConstants.MONTH_DICTIONARY)})" +
                "(?:" +
                "(?:-|\\/|,?\\s{0,3})" +
                "(${RuConstants.YEAR_PATTERN}(?![^\\s]\\d))" +
                ")?"

        private const val DATE_GROUP = 1
        private const val DATE_TO_GROUP = 2
        private const val MONTH_NAME_GROUP = 3
        private const val YEAR_GROUP = 4
    }
}
