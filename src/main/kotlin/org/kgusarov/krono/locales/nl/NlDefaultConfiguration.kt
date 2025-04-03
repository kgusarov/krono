package org.kgusarov.krono.locales.nl

import org.kgusarov.krono.KronoConfiguration
import org.kgusarov.krono.common.parsers.SlashDateFormatParser
import org.kgusarov.krono.includeCommonConfiguration
import org.kgusarov.krono.locales.nl.parsers.NlCasualDateParser
import org.kgusarov.krono.locales.nl.parsers.NlCasualDateTimeParser
import org.kgusarov.krono.locales.nl.parsers.NlCasualTimeParser
import org.kgusarov.krono.locales.nl.parsers.NlCasualYearMonthDayParser
import org.kgusarov.krono.locales.nl.parsers.NlMonthNameMiddleEndianParser
import org.kgusarov.krono.locales.nl.parsers.NlMonthNameParser
import org.kgusarov.krono.locales.nl.parsers.NlRelativeDateFormatParser
import org.kgusarov.krono.locales.nl.parsers.NlSlashMonthFormatParser
import org.kgusarov.krono.locales.nl.parsers.NlTimeExpressionParser
import org.kgusarov.krono.locales.nl.parsers.NlTimeUnitAgoFormatParser
import org.kgusarov.krono.locales.nl.parsers.NlTimeUnitCasualRelativeFormatParser
import org.kgusarov.krono.locales.nl.parsers.NlTimeUnitLaterFormatParser
import org.kgusarov.krono.locales.nl.parsers.NlTimeUnitWithinFormatParser
import org.kgusarov.krono.locales.nl.parsers.NlWeekdayParser
import org.kgusarov.krono.locales.nl.refiners.NlMergeDateRangeRefiner
import org.kgusarov.krono.locales.nl.refiners.NlMergeDateTimeRefiner

class NlDefaultConfiguration {
    fun createConfiguration(
        strictMode: Boolean = true,
        littleEndian: Boolean = true,
    ): KronoConfiguration =
        includeCommonConfiguration(
            KronoConfiguration(
                mutableListOf(
                    SlashDateFormatParser(littleEndian),
                    NlTimeUnitWithinFormatParser(),
                    NlMonthNameMiddleEndianParser(),
                    NlMonthNameParser(),
                    NlWeekdayParser(),
                    NlCasualYearMonthDayParser(),
                    NlSlashMonthFormatParser(),
                    NlTimeExpressionParser(strictMode),
                    NlTimeUnitAgoFormatParser(strictMode),
                    NlTimeUnitLaterFormatParser(strictMode),
                ),
                mutableListOf(
                    NlMergeDateTimeRefiner(),
                    NlMergeDateRangeRefiner(),
                ),
            ),
            strictMode,
        )

    fun createCasualConfiguration(littleEndian: Boolean = true): KronoConfiguration {
        val result = createConfiguration(false, littleEndian)

        result.parsers.addFirst(NlCasualDateParser())
        result.parsers.addFirst(NlCasualTimeParser())
        result.parsers.addFirst(NlCasualDateTimeParser())
        result.parsers.addFirst(NlMonthNameParser())
        result.parsers.addFirst(NlRelativeDateFormatParser())
        result.parsers.addFirst(NlTimeUnitCasualRelativeFormatParser())

        return result
    }
}
