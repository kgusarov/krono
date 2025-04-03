package org.kgusarov.krono.locales.nl.parsers

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.kgusarov.krono.KronoComponents
import org.kgusarov.krono.ParserResult
import org.kgusarov.krono.ParserResultFactory
import org.kgusarov.krono.ParsingContext
import org.kgusarov.krono.RegExpMatchArray
import org.kgusarov.krono.calculation.findYearClosestToRef
import org.kgusarov.krono.common.parsers.AbstractParserWithWordBoundaryChecking
import org.kgusarov.krono.locales.nl.NlConstants
import org.kgusarov.krono.locales.nl.parseYear
import org.kgusarov.krono.utils.matchAnyPattern

@SuppressFBWarnings("EI_EXPOSE_REP")
class NlMonthNameParser : AbstractParserWithWordBoundaryChecking() {
    override fun innerPattern(context: ParsingContext) = PATTERN

    override fun innerExtract(
        context: ParsingContext,
        match: RegExpMatchArray,
    ): ParserResult? {
        val components = context.createParsingComponents()
        components.imply(KronoComponents.Day, 1)

        val monthName = match[MONTH_NAME_GROUP]!!
        val month = NlConstants.MONTH_DICTIONARY[monthName.lowercase()]
        components.assign(KronoComponents.Month, month)

        if (!match[YEAR_GROUP].isNullOrEmpty()) {
            val year = parseYear(match[YEAR_GROUP]!!)
            components.assign(KronoComponents.Year, year)
        } else {
            val year = findYearClosestToRef(context.instant, 1, month!!)
            components.imply(KronoComponents.Year, year)
        }

        return ParserResultFactory(components)
    }

    @Suppress("RegExpUnnecessaryNonCapturingGroup")
    companion object {
        @JvmStatic
        private val PATTERN =
            Regex(
                "(${matchAnyPattern(NlConstants.MONTH_DICTIONARY)})" +
                    "\\s*" +
                    "(?:" +
                    "[,-]?\\s*(${NlConstants.YEAR_PATTERN})?" +
                    ")?" +
                    "(?=[^\\s\\w]|\\s+[^0-9]|\\s+$|$)",
            )

        private const val MONTH_NAME_GROUP = 1
        private const val YEAR_GROUP = 2
    }
}
