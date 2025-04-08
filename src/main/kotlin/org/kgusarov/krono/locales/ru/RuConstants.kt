package org.kgusarov.krono.locales.ru

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.kgusarov.krono.KronoUnit
import org.kgusarov.krono.utils.matchAnyPattern
import org.kgusarov.krono.utils.repeatedTimeUnitPattern
import java.time.DayOfWeek

@Suppress("RegExpUnnecessaryNonCapturingGroup", "RegExpSimplifiable")
@SuppressFBWarnings("MS_EXPOSE_REP")
internal object RuConstants {
    @JvmStatic
    val WEEKDAY_DICTIONARY: Map<String, DayOfWeek> =
        mapOf(
            "воскресенье" to DayOfWeek.SUNDAY,
            "воскресенья" to DayOfWeek.SUNDAY,
            "вск" to DayOfWeek.SUNDAY,
            "вск." to DayOfWeek.SUNDAY,
            "понедельник" to DayOfWeek.MONDAY,
            "понедельника" to DayOfWeek.MONDAY,
            "пн" to DayOfWeek.MONDAY,
            "пн." to DayOfWeek.MONDAY,
            "вторник" to DayOfWeek.TUESDAY,
            "вторника" to DayOfWeek.TUESDAY,
            "вт" to DayOfWeek.TUESDAY,
            "вт." to DayOfWeek.TUESDAY,
            "среда" to DayOfWeek.WEDNESDAY,
            "среды" to DayOfWeek.WEDNESDAY,
            "среду" to DayOfWeek.WEDNESDAY,
            "ср" to DayOfWeek.WEDNESDAY,
            "ср." to DayOfWeek.WEDNESDAY,
            "четверг" to DayOfWeek.THURSDAY,
            "четверга" to DayOfWeek.THURSDAY,
            "чт" to DayOfWeek.THURSDAY,
            "чт." to DayOfWeek.THURSDAY,
            "пятница" to DayOfWeek.FRIDAY,
            "пятницу" to DayOfWeek.FRIDAY,
            "пятницы" to DayOfWeek.FRIDAY,
            "пт" to DayOfWeek.FRIDAY,
            "пт." to DayOfWeek.FRIDAY,
            "суббота" to DayOfWeek.SATURDAY,
            "субботу" to DayOfWeek.SATURDAY,
            "субботы" to DayOfWeek.SATURDAY,
            "сб" to DayOfWeek.SATURDAY,
            "сб." to DayOfWeek.SATURDAY,
        )

    @JvmStatic
    val FULL_MONTH_NAME_DICTIONARY: Map<String, Int> =
        mapOf(
            "январь" to 1,
            "января" to 1,
            "январе" to 1,
            "февраль" to 2,
            "февраля" to 2,
            "феврале" to 2,
            "март" to 3,
            "марта" to 3,
            "марте" to 3,
            "апрель" to 4,
            "апреля" to 4,
            "апреле" to 4,
            "май" to 5,
            "мая" to 5,
            "мае" to 5,
            "июнь" to 6,
            "июня" to 6,
            "июне" to 6,
            "июль" to 7,
            "июля" to 7,
            "июле" to 7,
            "август" to 8,
            "августа" to 8,
            "августе" to 8,
            "сентябрь" to 9,
            "сентября" to 9,
            "сентябре" to 9,
            "октябрь" to 10,
            "октября" to 10,
            "октябре" to 10,
            "ноябрь" to 11,
            "ноября" to 11,
            "ноябре" to 11,
            "декабрь" to 12,
            "декабря" to 12,
            "декабре" to 12,
        )

    @JvmStatic
    val MONTH_DICTIONARY: Map<String, Int> =
        mapOf(
            "янв" to 1,
            "янв." to 1,
            "фев" to 2,
            "фев." to 2,
            "мар" to 3,
            "мар." to 3,
            "апр" to 4,
            "апр." to 4,
            "авг" to 8,
            "авг." to 8,
            "сен" to 9,
            "сен." to 9,
            "окт" to 10,
            "окт." to 10,
            "ноя" to 11,
            "ноя." to 11,
            "дек" to 12,
            "дек." to 12,
        ) + FULL_MONTH_NAME_DICTIONARY

    @JvmStatic
    val INTEGER_WORD_DICTIONARY: Map<String, Int> =
        mapOf(
            "один" to 1,
            "одна" to 1,
            "одной" to 1,
            "одну" to 1,
            "две" to 2,
            "два" to 2,
            "двух" to 2,
            "три" to 3,
            "трех" to 3,
            "трёх" to 3,
            "четыре" to 4,
            "четырех" to 4,
            "четырёх" to 4,
            "пять" to 5,
            "пяти" to 5,
            "шесть" to 6,
            "шести" to 6,
            "семь" to 7,
            "семи" to 7,
            "восемь" to 8,
            "восьми" to 8,
            "девять" to 9,
            "девяти" to 9,
            "десять" to 10,
            "десяти" to 10,
            "одиннадцать" to 11,
            "одиннадцати" to 11,
            "двенадцать" to 12,
            "двенадцати" to 12,
        )

    @JvmStatic
    val ORDINAL_WORD_DICTIONARY: Map<String, Int> =
        mapOf(
            "первое" to 1,
            "первого" to 1,
            "второе" to 2,
            "второго" to 2,
            "третье" to 3,
            "третьего" to 3,
            "четвертое" to 4,
            "четвертого" to 4,
            "пятое" to 5,
            "пятого" to 5,
            "шестое" to 6,
            "шестого" to 6,
            "седьмое" to 7,
            "седьмого" to 7,
            "восьмое" to 8,
            "восьмого" to 8,
            "девятое" to 9,
            "девятого" to 9,
            "десятое" to 10,
            "десятого" to 10,
            "одиннадцатое" to 11,
            "одиннадцатого" to 11,
            "двенадцатое" to 12,
            "двенадцатого" to 12,
            "тринадцатое" to 13,
            "тринадцатого" to 13,
            "четырнадцатое" to 14,
            "четырнадцатого" to 14,
            "пятнадцатое" to 15,
            "пятнадцатого" to 15,
            "шестнадцатое" to 16,
            "шестнадцатого" to 16,
            "семнадцатое" to 17,
            "семнадцатого" to 17,
            "восемнадцатое" to 18,
            "восемнадцатого" to 18,
            "девятнадцатое" to 19,
            "девятнадцатого" to 19,
            "двадцатое" to 20,
            "двадцатого" to 20,
            "двадцать первое" to 21,
            "двадцать первого" to 21,
            "двадцать второе" to 22,
            "двадцать второго" to 22,
            "двадцать третье" to 23,
            "двадцать третьего" to 23,
            "двадцать четвертое" to 24,
            "двадцать четвертого" to 24,
            "двадцать пятое" to 25,
            "двадцать пятого" to 25,
            "двадцать шестое" to 26,
            "двадцать шестого" to 26,
            "двадцать седьмое" to 27,
            "двадцать седьмого" to 27,
            "двадцать восьмое" to 28,
            "двадцать восьмого" to 28,
            "двадцать девятое" to 29,
            "двадцать девятого" to 29,
            "тридцатое" to 30,
            "тридцатого" to 30,
            "тридцать первое" to 31,
            "тридцать первого" to 31,
        )

    @JvmStatic
    val TIME_UNIT_DICTIONARY: Map<String, KronoUnit> =
        mapOf(
            "сек" to KronoUnit.Second,
            "секунда" to KronoUnit.Second,
            "секунд" to KronoUnit.Second,
            "секунды" to KronoUnit.Second,
            "секунду" to KronoUnit.Second,
            "секундочка" to KronoUnit.Second,
            "секундочки" to KronoUnit.Second,
            "секундочек" to KronoUnit.Second,
            "секундочку" to KronoUnit.Second,
            "мин" to KronoUnit.Minute,
            "минута" to KronoUnit.Minute,
            "минут" to KronoUnit.Minute,
            "минуты" to KronoUnit.Minute,
            "минуту" to KronoUnit.Minute,
            "минуток" to KronoUnit.Minute,
            "минутки" to KronoUnit.Minute,
            "минутку" to KronoUnit.Minute,
            "минуточек" to KronoUnit.Minute,
            "минуточки" to KronoUnit.Minute,
            "минуточку" to KronoUnit.Minute,
            "час" to KronoUnit.Hour,
            "часов" to KronoUnit.Hour,
            "часа" to KronoUnit.Hour,
            "часу" to KronoUnit.Hour,
            "часиков" to KronoUnit.Hour,
            "часика" to KronoUnit.Hour,
            "часике" to KronoUnit.Hour,
            "часик" to KronoUnit.Hour,
            "день" to KronoUnit.Day,
            "дня" to KronoUnit.Day,
            "дней" to KronoUnit.Day,
            "суток" to KronoUnit.Day,
            "сутки" to KronoUnit.Day,
            "неделя" to KronoUnit.Week,
            "неделе" to KronoUnit.Week,
            "недели" to KronoUnit.Week,
            "неделю" to KronoUnit.Week,
            "недель" to KronoUnit.Week,
            "недельке" to KronoUnit.Week,
            "недельки" to KronoUnit.Week,
            "неделек" to KronoUnit.Week,
            "месяц" to KronoUnit.Month,
            "месяце" to KronoUnit.Month,
            "месяцев" to KronoUnit.Month,
            "месяца" to KronoUnit.Month,
            "квартал" to KronoUnit.Quarter,
            "квартале" to KronoUnit.Quarter,
            "кварталов" to KronoUnit.Quarter,
            "год" to KronoUnit.Year,
            "года" to KronoUnit.Year,
            "году" to KronoUnit.Year,
            "годов" to KronoUnit.Year,
            "лет" to KronoUnit.Year,
            "годик" to KronoUnit.Year,
            "годика" to KronoUnit.Year,
            "годиков" to KronoUnit.Year,
        )

    @JvmStatic
    val NUMBER_PATTERN =
        "(?:${matchAnyPattern(INTEGER_WORD_DICTIONARY)}|[0-9]+|[0-9]+\\.[0-9]+|пол|несколько|пар(?:ы|у)|\\s{0,3})"

    @JvmStatic
    val ORDINAL_NUMBER_PATTERN = "(?:${matchAnyPattern(ORDINAL_WORD_DICTIONARY)}|[0-9]{1,2}(?:го|ого|е|ое)?)"

    @JvmStatic
    val YEAR = "(?:\\s+(?:году|года|год|г|г.))?"

    @JvmStatic
    val YEAR_PATTERN =
        "(?:[1-9][0-9]{0,3}$YEAR\\s*(?:н.э.|до н.э.|н. э.|до н. э.)|[1-2][0-9]{3}$YEAR|[5-9][0-9]$YEAR)"

    @JvmStatic
    val SINGLE_TIME_UNIT_PATTERN = "(${NUMBER_PATTERN})\\s{0,3}(${matchAnyPattern(TIME_UNIT_DICTIONARY)})"

    @JvmStatic
    val SINGLE_TIME_UNIT_REGEX = Regex(SINGLE_TIME_UNIT_PATTERN, RegexOption.IGNORE_CASE)

    @JvmStatic
    val TIME_UNITS_PATTERN = repeatedTimeUnitPattern("(?:(?:около|примерно)\\s{0,3})?", SINGLE_TIME_UNIT_PATTERN)

    @JvmStatic
    val LEFT_BOUNDARY = "([^\\p{L}\\p{N}_]|^)"

    @JvmStatic
    val RIGHT_BOUNDARY = "(?=[^\\p{L}\\p{N}_]|$)"
}
