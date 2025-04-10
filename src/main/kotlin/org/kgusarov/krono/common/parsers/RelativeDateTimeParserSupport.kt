package org.kgusarov.krono.common.parsers

import org.kgusarov.krono.KronoComponents
import org.kgusarov.krono.KronoMeridiem
import org.kgusarov.krono.KronoUnit
import org.kgusarov.krono.ParsedComponents
import org.kgusarov.krono.ParsingContext
import org.kgusarov.krono.RegExpMatchArray
import org.kgusarov.krono.extensions.add
import org.kgusarov.krono.extensions.compareTo
import org.kgusarov.krono.extensions.plus

interface RelativeDateTimeParserSupport {
    fun adjust(
        context: ParsingContext,
        unit: KronoUnit,
    ): ParsedComponents {
        val components = context.createParsingComponents()
        var date = context.reference.instant

        if (unit == KronoUnit.Week) {
            val adjustment = -(date.dayOfWeek.value % 7)
            date = date.add(KronoUnit.Day, adjustment)

            components.imply(KronoComponents.Day, date.dayOfMonth)
            components.imply(KronoComponents.Month, date.monthValue)
            components.imply(KronoComponents.Year, date.year)
        } else if (unit == KronoUnit.Month) {
            date = date.add(KronoUnit.Day, -date.dayOfMonth + 1)

            components.imply(KronoComponents.Day, date.dayOfMonth)
            components.assign(KronoComponents.Year, date.year)
            components.assign(KronoComponents.Month, date.monthValue)
        } else if (unit == KronoUnit.Year) {
            date = date.add(KronoUnit.Day, -date.dayOfMonth + 1)
            date = date.add(KronoUnit.Month, -date.monthValue + 1)

            components.imply(KronoComponents.Day, date.dayOfMonth)
            components.imply(KronoComponents.Month, date.monthValue)
            components.assign(KronoComponents.Year, date.year)
        }

        return components
    }

    fun adjust(
        context: ParsingContext,
        weekPattern: Regex,
        monthPattern: Regex,
        yearPattern: Regex,
        unitWord: String,
    ): ParsedComponents {
        val unit =
            when {
                unitWord.matches(weekPattern) -> KronoUnit.Week
                unitWord.matches(monthPattern) -> KronoUnit.Month
                unitWord.matches(yearPattern) -> KronoUnit.Year
                else -> null
            } ?: return context.createParsingComponents()

        return adjust(context, unit)
    }

    fun extractPrimaryTimeComponents(
        context: ParsingContext,
        match: RegExpMatchArray,
        nightSuffix: String,
        afternoonSuffix: String,
        morningSuffix: String,
        extractPrimaryTimeComponents: (ParsingContext, RegExpMatchArray) -> ParsedComponents?,
    ): ParsedComponents? {
        val components = extractPrimaryTimeComponents(context, match) ?: return null
        val matched = match[0]!!.lowercase()

        if (matched.endsWith(nightSuffix)) {
            val hour = components.hour()
            if (hour >= 6 && hour < 12) {
                components.assign(KronoComponents.Hour, components.hour() + 12)
                components.assign(KronoComponents.Meridiem, KronoMeridiem.PM)
            } else if (hour < 6) {
                components.assign(KronoComponents.Meridiem, KronoMeridiem.AM)
            }
        }

        if (matched.endsWith(afternoonSuffix)) {
            components.assign(KronoComponents.Meridiem, KronoMeridiem.PM)
            val hour = components.hour()
            if (hour >= 0 && hour <= 6) {
                components.assign(KronoComponents.Hour, components.hour() + 12)
            }
        }

        if (matched.endsWith(morningSuffix)) {
            components.assign(KronoComponents.Meridiem, KronoMeridiem.AM)
            val hour = components.hour()
            if (hour < 12) {
                components.assign(KronoComponents.Hour, components.hour())
            }
        }

        return components
    }
}
