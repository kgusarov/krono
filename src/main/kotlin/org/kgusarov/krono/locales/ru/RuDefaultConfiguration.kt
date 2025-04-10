package org.kgusarov.krono.locales.ru

import org.kgusarov.krono.KronoConfiguration
import org.kgusarov.krono.common.parsers.SlashDateFormatParser
import org.kgusarov.krono.includeCommonConfiguration
import org.kgusarov.krono.locales.ru.parsers.RuCasualDateParser
import org.kgusarov.krono.locales.ru.parsers.RuCasualTimeParser
import org.kgusarov.krono.locales.ru.parsers.RuMonthNameLittleEndianParser
import org.kgusarov.krono.locales.ru.parsers.RuMonthNameParser
import org.kgusarov.krono.locales.ru.parsers.RuRelativeDateFormatParser
import org.kgusarov.krono.locales.ru.parsers.RuTimeExpressionParser
import org.kgusarov.krono.locales.ru.parsers.RuTimeUnitAgoFormatParser
import org.kgusarov.krono.locales.ru.parsers.RuTimeUnitCasualRelativeFormatParser
import org.kgusarov.krono.locales.ru.parsers.RuTimeUnitWithinFormatParser
import org.kgusarov.krono.locales.ru.parsers.RuWeekdayParser
import org.kgusarov.krono.locales.ru.refiners.RuMergeDateRangeRefiner
import org.kgusarov.krono.locales.ru.refiners.RuMergeDateTimeRefiner

class RuDefaultConfiguration {
    fun createConfiguration(strictMode: Boolean = true): KronoConfiguration =
        includeCommonConfiguration(
            KronoConfiguration(
                mutableListOf(
                    SlashDateFormatParser(true),
                    RuTimeUnitWithinFormatParser(),
                    RuMonthNameLittleEndianParser(),
                    RuWeekdayParser(),
                    RuTimeExpressionParser(strictMode),
                    RuTimeUnitAgoFormatParser(),
                ),
                mutableListOf(
                    RuMergeDateTimeRefiner(),
                    RuMergeDateRangeRefiner(),
                ),
            ),
            strictMode,
        )

    fun createCasualConfiguration(): KronoConfiguration {
        val result = createConfiguration(false)

        result.parsers.addFirst(RuCasualDateParser())
        result.parsers.addFirst(RuCasualTimeParser())
        result.parsers.addFirst(RuMonthNameParser())
        result.parsers.addFirst(RuRelativeDateFormatParser())
        result.parsers.addFirst(RuTimeUnitCasualRelativeFormatParser())

        return result
    }
}
