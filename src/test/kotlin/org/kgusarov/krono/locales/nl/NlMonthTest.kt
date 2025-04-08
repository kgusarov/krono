package org.kgusarov.krono.locales.nl

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.kgusarov.krono.Krono
import org.kgusarov.krono.assertDate
import org.kgusarov.krono.testSingleCase
import java.util.stream.Stream

internal class NlMonthTest {
    @ParameterizedTest
    @MethodSource("monthYearExpressionArgs")
    internal fun `month year expression`(text: String, refDate: String, expectedDate: String) {
        testSingleCase(Krono.nlCasual, text, refDate) {
            it.start.assertDate(expectedDate)
        }
    }

    @ParameterizedTest
    @MethodSource("monthExpressionArgs")
    internal fun `month expression`(text: String, refDate: String, expectedDate: String) {
        testSingleCase(Krono.nlCasual, text, refDate) {
            it.start.assertDate(expectedDate)
        }
    }

    companion object {
        @JvmStatic
        fun monthYearExpressionArgs(): Stream<Arguments> = Stream.of(
            Arguments.of("september 2012", "2012-08-10T12:00:00", "2012-09-01T12:00:00"),
            Arguments.of("sept 2012", "2012-08-10T12:00:00", "2012-09-01T12:00:00"),
            Arguments.of("sep 2012", "2012-08-10T12:00:00", "2012-09-01T12:00:00"),
            Arguments.of("sep. 2012", "2012-08-10T12:00:00", "2012-09-01T12:00:00"),
            Arguments.of("sep-2012", "2012-08-10T12:00:00", "2012-09-01T12:00:00"),
            Arguments.of("mrt 2012", "2012-08-10T12:00:00", "2012-03-01T12:00:00"),
            Arguments.of("jan 2021", "2020-11-22T12:00:00", "2021-01-01T12:00:00"),
            Arguments.of("mei 2021", "2020-11-22T12:00:00", "2021-05-01T12:00:00"),
            Arguments.of("The date is sep 2012 is the date", "2012-08-10T12:00:00", "2012-09-01T12:00:00"),
            Arguments.of("By Angie ja november 2019", "2012-08-10T12:00:00", "2019-11-01T12:00:00"),
            Arguments.of("Op 23 MRT. 2022", "2012-08-10T12:00:00", "2022-03-23T12:00:00"),
            Arguments.of("9/2012", "2012-08-10T12:00:00", "2012-09-01T12:00:00"),
            Arguments.of("09/2012", "2012-08-10T12:00:00", "2012-09-01T12:00:00"),
            Arguments.of("aug 96", "2012-08-10T12:00:00", "1996-08-01T12:00:00"),
            Arguments.of("96 aug 96", "2012-08-10T12:00:00", "1996-08-01T12:00:00"),
        )

        @JvmStatic
        fun monthExpressionArgs(): Stream<Arguments> = Stream.of(
            Arguments.of("In januari", "2020-11-22T12:00:00", "2021-01-01T12:00:00"),
            Arguments.of("in jan", "2020-11-22T12:00:00", "2021-01-01T12:00:00"),
            Arguments.of("mei", "2020-11-22T12:00:00", "2021-05-01T12:00:00"),
        )
    }
}
