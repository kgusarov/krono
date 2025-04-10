package org.kgusarov.krono.common.parsers

import org.kgusarov.krono.KronoComponents
import org.kgusarov.krono.ParsedResult
import org.kgusarov.krono.ParserResult
import org.kgusarov.krono.ParserResultFactory
import org.kgusarov.krono.ParsingContext
import org.kgusarov.krono.RegExpMatchArray
import org.kgusarov.krono.calculation.findYearClosestToRef
import org.kgusarov.krono.common.ParseYear
import org.kgusarov.krono.extensions.not

interface MonthNameParserSupport {
    fun applyMonth(
        context: ParsingContext,
        match: RegExpMatchArray,
        result: ParsedResult,
        monthName: String,
        monthDictionary: Map<String, Int>,
        yearGroup: Int,
        parseYear: ParseYear,
    ): ParserResult {
        result.start.imply(KronoComponents.Day, 1)

        val month = monthDictionary[monthName]!!
        result.start.assign(KronoComponents.Month, month)

        if (!match[yearGroup]) {
            val year = findYearClosestToRef(context.instant, 1, month)
            result.start.imply(KronoComponents.Year, year)
        } else {
            val yearNumber = parseYear(match[yearGroup]!!)
            result.start.assign(KronoComponents.Year, yearNumber)
        }

        return ParserResultFactory(result)
    }
}
