package org.kgusarov.krono.common.parsers

import org.kgusarov.krono.KronoComponents
import org.kgusarov.krono.KronoUnit
import org.kgusarov.krono.ParsedComponents
import org.kgusarov.krono.ParsingContext
import org.kgusarov.krono.extensions.add

abstract class AbstractRelativeDateFormatParser : AbstractParserWithWordBoundaryChecking() {
    protected fun adjust(
        context: ParsingContext,
        weekPattern: Regex,
        monthPattern: Regex,
        yearPattern: Regex,
        unitWord: String,
    ): ParsedComponents {
        val components = context.createParsingComponents()
        var date = context.reference.instant

        if (unitWord.matches(weekPattern)) {
            val adjustment = -(date.dayOfWeek.value % 7)
            date = date.add(KronoUnit.Day, adjustment)

            components.imply(KronoComponents.Day, date.dayOfMonth)
            components.imply(KronoComponents.Month, date.monthValue)
            components.imply(KronoComponents.Year, date.year)
        } else if (unitWord.matches(monthPattern)) {
            date = date.add(KronoUnit.Day, -date.dayOfMonth + 1)

            components.imply(KronoComponents.Day, date.dayOfMonth)
            components.assign(KronoComponents.Year, date.year)
            components.assign(KronoComponents.Month, date.monthValue)
        } else if (unitWord.matches(yearPattern)) {
            date = date.add(KronoUnit.Day, -date.dayOfMonth + 1)
            date = date.add(KronoUnit.Month, -date.monthValue + 1)

            components.imply(KronoComponents.Day, date.dayOfMonth)
            components.imply(KronoComponents.Month, date.monthValue)
            components.assign(KronoComponents.Year, date.year)
        }

        return components
    }
}
