package org.kgusarov.krono.locales.ru

import org.kgusarov.krono.KronoDecimalTimeUnits
import org.kgusarov.krono.common.parseYearMatch
import java.math.BigDecimal

object Ru {
    @JvmStatic
    val configuration = RuDefaultConfiguration()

    @JvmStatic
    val casual = configuration.createCasualConfiguration()

    @JvmStatic
    val strict = configuration.createConfiguration(strictMode = true)
}

private val SOME = Regex("несколько", RegexOption.IGNORE_CASE)
private val HALF = Regex("пол", RegexOption.IGNORE_CASE)
private val PAIR = Regex("пар", RegexOption.IGNORE_CASE)

internal fun parseNumberPattern(match: String): BigDecimal {
    val num = match.lowercase()
    return when {
        RuConstants.INTEGER_WORD_DICTIONARY[num] != null -> RuConstants.INTEGER_WORD_DICTIONARY[num]!!.toBigDecimal()
        num.contains(SOME) -> BigDecimal(3)
        num.contains(HALF) -> BigDecimal(0.5)
        num.contains(PAIR) -> BigDecimal(2)
        num.isEmpty() -> BigDecimal(1)
        else -> num.toBigDecimal()
    }
}

@Suppress("RegExpUnnecessaryNonCapturingGroup")
private val ORDINAL_SUFFIX_REGEX =
    Regex(
        "(?:го|ого|е|ое)$",
        RegexOption.IGNORE_CASE,
    )

internal fun parseOrdinalNumberPattern(match: String): Int {
    var num = match.lowercase()
    if (RuConstants.ORDINAL_WORD_DICTIONARY[num] !== null) {
        return RuConstants.ORDINAL_WORD_DICTIONARY[num]!!
    }

    num = num.replace(ORDINAL_SUFFIX_REGEX, "")
    return if (num.isEmpty()) 0 else num.toInt()
}

private val WITH_YEAR = Regex("(год|года|г|г.)", RegexOption.IGNORE_CASE)
private val BEFORE_CHRIST = Regex("(до н.э.|до н. э.)", RegexOption.IGNORE_CASE)
private val AFTER_CHRIST = Regex("(н. э.|н.э.)", RegexOption.IGNORE_CASE)

internal fun parseYear(match: String): Int {
    val cleanMatch =
        when {
            WITH_YEAR.containsMatchIn(match) -> match.replace(WITH_YEAR, "")
            else -> match
        }

    return parseYearMatch(cleanMatch, BEFORE_CHRIST, AFTER_CHRIST)
}

internal fun parseTimeUnits(timeUnitText: String): KronoDecimalTimeUnits =
    org.kgusarov.krono.common.parseTimeUnits(
        timeUnitText,
        RuConstants.SINGLE_TIME_UNIT_REGEX,
        RuConstants.TIME_UNIT_DICTIONARY,
        ::parseNumberPattern,
    )
