package org.kgusarov.krono.locales.ru

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.kgusarov.krono.Krono
import org.kgusarov.krono.testRangeExpression
import org.kgusarov.krono.testSingleExpression
import org.kgusarov.krono.testUnexpectedResult
import java.util.stream.Stream

internal class RuTimeExpressionTest {
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

    @ParameterizedTest
    @MethodSource("negativeTextArgs")
    internal fun `negative text`(
        strict: Boolean,
        text: String
    ) {
        testUnexpectedResult(
            Krono.ruStrict,
            Krono.ruCasual,
            strict,
            text,
        )
    }

    companion object {
        @JvmStatic
        fun singleExpressionArgs(): Stream<Arguments> = Stream.of(
            Arguments.of("20:32:13", "2016-10-01T08:00:00", "2016-10-01T20:32:13"),
            Arguments.of("в 11 утра", "2016-10-01T08:00:00", "2016-10-01T11:00:00"),
            Arguments.of("в 11 вечера", "2016-10-01T08:00:00", "2016-10-01T23:00:00"),
            Arguments.of("в 1", "2016-10-01T08:00:00", "2016-10-01T01:00:00"),
            Arguments.of("в 12", "2016-10-01T08:00:00", "2016-10-01T12:00:00"),
            Arguments.of("в 12.30", "2016-10-01T08:00:00", "2016-10-01T12:30:00"),
        )

        @JvmStatic
        fun rangeExpressionArgs(): Stream<Arguments> = Stream.of(
            Arguments.of("10:00:00 - 21:45:01", "2016-10-01T08:00:00", "2016-10-01T10:00:00", "2016-10-01T21:45:01"),
            Arguments.of("с 10 до 11 утра", "2016-10-01T08:00:00", "2016-10-01T10:00:00", "2016-10-01T11:00:00"),
            Arguments.of("с 10 до 11 вечера", "2016-10-01T08:00:00", "2016-10-01T22:00:00", "2016-10-01T23:00:00"),
        )

        @JvmStatic
        fun negativeTextArgs(): Stream<Arguments> = Stream.of(
            Arguments.of(false, "2020"),
            Arguments.of(false, "2020  "),
            Arguments.of(false, "Температура 101,194 градусов!"),
            Arguments.of(false, "Температура 101 градусов!"),
            Arguments.of(false, "Температура 10.1"),
            Arguments.of(false, "Это в 10.1 - 10.12"),
            Arguments.of(false, "Это в 10 - 10.1"),
            Arguments.of(true, "Это в 101,194 телефон!"),
            Arguments.of(true, "Это в 101 стул!"),
            Arguments.of(true, "Это в 10.1"),
            Arguments.of(true, "Это в 10"),
            Arguments.of(true, "2020"),
            Arguments.of(true, "Это в 10.1 - 10.12"),
            Arguments.of(true, "Это в 10 - 10.1"),
            Arguments.of(true, "Это в 10 - 20"),
            Arguments.of(true, "7-730"),
        )
    }
}