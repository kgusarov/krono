package org.kgusarov.krono.locales.ru

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.kgusarov.krono.Krono
import org.kgusarov.krono.ParsingOption
import org.kgusarov.krono.assertDate
import org.kgusarov.krono.testRangeExpression
import org.kgusarov.krono.testSingleCase
import org.kgusarov.krono.testSingleExpression
import org.kgusarov.krono.testUnexpectedResult
import java.util.stream.Stream

internal class RuMonthNameLittleEndianTest {
    @ParameterizedTest
    @MethodSource("singleExpressionArgs")
    internal fun `single expression`(text: String, refDate: String, expectedDate: String) {
        testSingleExpression(Krono.ruCasual, text, refDate, expectedDate)
    }

    @ParameterizedTest
    @MethodSource("rangeExpressionArgs")
    internal fun `range expression`(
        text: String,
        refDate: String,
        expectedStartDate: String,
        expectedEndDate: String,
    ) {
        testRangeExpression(Krono.ruCasual, text, refDate, expectedStartDate, expectedEndDate)
    }

    @Test
    internal fun forwardOption() {
        testSingleCase(
            Krono.ruCasual,
            "22-23 фев в 7",
            "2016-02-15T12:00:00",
            ParsingOption(forwardDate = true)
        ) {
            //it.start.assertDate("2017-02-22T07:00:00")
            //it.end!!.assertDate("2017-02-23T07:00:00")
        }
    }

    @Test
    internal fun `random negative text`() {
        testUnexpectedResult(Krono.ruCasual, "32 августа 2014")
        testUnexpectedResult(Krono.ruCasual, "29 февраля 2014")
        testUnexpectedResult(Krono.ruCasual, "32 августа")
        testUnexpectedResult(Krono.ruCasual, "29 февраля")
    }

    companion object {
        @JvmStatic
        fun singleExpressionArgs(): Stream<Arguments> = Stream.of(
            Arguments.of("10.08.2012", "2012-08-10T12:00:00", "2012-08-10T12:00:00"),
            Arguments.of("10 августа 2012", "2012-08-10T12:00:00", "2012-08-10T12:00:00"),
            Arguments.of("третье фев 82", "2012-08-10T12:00:00", "1982-02-03T12:00:00"),
            Arguments.of("Дедлайн 10 августа", "2012-08-10T12:00:00", "2012-08-10T12:00:00"),
            Arguments.of("Дедлайн Четверг, 10 января", "2012-08-10T12:00:00", "2013-01-10T12:00:00"),
            Arguments.of("10-августа 2012", "2012-08-10T12:00:00", "2012-08-10T12:00:00"),
            Arguments.of("10-августа-2012", "2012-08-10T12:00:00", "2012-08-10T12:00:00"),
            Arguments.of("10/августа 2012", "2012-08-10T12:00:00", "2012-08-10T12:00:00"),
            Arguments.of("10/августа/2012", "2012-08-10T12:00:00", "2012-08-10T12:00:00"),
            Arguments.of("5 мая 12:00", "2012-08-10T12:00:00", "2012-05-05T12:00:00"),
            Arguments.of("двадцать пятое мая", "2012-08-10T12:00:00", "2012-05-25T12:00:00"),
            Arguments.of("двадцать пятое мая 2020 года", "2012-08-10T12:00:00", "2020-05-25T12:00:00"),
            //Arguments.of("24го октября, 9:00", "2017-08-10T12:00:00", "2017-10-24T09:00:00"),
            Arguments.of("03 авг 96", "2012-08-10T12:00:00", "1996-08-03T12:00:00"),
        )

        @JvmStatic
        fun rangeExpressionArgs(): Stream<Arguments> = Stream.of(
            Arguments.of("10 - 22 августа 2012", "2012-08-10T12:00:00", "2012-08-10T12:00:00", "2012-08-22T12:00:00"),
            Arguments.of("с 10 по 22 августа 2012", "2012-08-10T12:00:00", "2012-08-10T12:00:00", "2012-08-22T12:00:00"),
            Arguments.of("10 августа - 12 сентября", "2012-08-10T12:00:00", "2012-08-10T12:00:00", "2012-09-12T12:00:00"),
            Arguments.of("10 августа - 12 сентября 2013", "2012-08-10T12:00:00", "2013-08-10T12:00:00", "2013-09-12T12:00:00"),
        )
    }
}
