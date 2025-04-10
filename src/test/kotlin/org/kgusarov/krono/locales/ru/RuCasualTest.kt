package org.kgusarov.krono.locales.ru

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.kgusarov.krono.Krono
import org.kgusarov.krono.testRangeExpression
import org.kgusarov.krono.testSingleExpression
import org.kgusarov.krono.testUnexpectedResult
import java.util.stream.Stream

internal class RuCasualTest {
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
    internal fun `random negative text`() {
        testUnexpectedResult(Krono.ruCasual, "несегодня");
        testUnexpectedResult(Krono.ruCasual, "зявтра");
        testUnexpectedResult(Krono.ruCasual, "вчеера");
        testUnexpectedResult(Krono.ruCasual, "январ");
    }

    companion object {
        @JvmStatic
        fun singleExpressionArgs(): Stream<Arguments> = Stream.of(
            Arguments.of("Дедлайн сегодня", "2012-08-10T17:10:00", "2012-08-10T17:10:00"),
            Arguments.of("Дедлайн завтра", "2012-08-10T17:10:00", "2012-08-11T17:10:00"),
            Arguments.of("Дедлайн послезавтра", "2012-08-10T17:10:00", "2012-08-12T17:10:00"),
            Arguments.of("Дедлайн послепослезавтра", "2012-08-10T17:10:00", "2012-08-13T17:10:00"),
            Arguments.of("Дедлайн вчера", "2012-08-10T17:10:00", "2012-08-09T17:10:00"),
            Arguments.of("Дедлайн позавчера", "2012-08-10T17:10:00", "2012-08-08T17:10:00"),
            Arguments.of("Дедлайн позапозавчера", "2012-08-10T17:10:00", "2012-08-07T17:10:00"),
            Arguments.of("Дедлайн сейчас", "2012-08-10T08:09:10.11", "2012-08-10T08:09:10.11"),
            Arguments.of("Дедлайн утром", "2012-08-10T08:09:10.11", "2012-08-10T06:00:00.00"),
            Arguments.of("Дедлайн этим утром", "2012-08-10T08:09:10.11", "2012-08-10T06:00:00.00"),
            Arguments.of("Дедлайн в полдень", "2012-08-10T08:09:10.11", "2012-08-10T12:00:00.00"),
            Arguments.of("Дедлайн прошлым вечером", "2012-08-10T08:09:10.11", "2012-08-09T20:00:00.00"),
            Arguments.of("Дедлайн вечером", "2012-08-10T08:09:10.11", "2012-08-10T20:00:00.00"),
            Arguments.of("Дедлайн прошлой ночью", "2012-08-10T08:09:10.11", "2012-08-10T00:00:00.00"),
            Arguments.of("Дедлайн прошлой ночью", "2012-08-10T02:09:10.11", "2012-08-09T00:00:00.00"),
            Arguments.of("Дедлайн этой ночью", "2012-08-10T08:09:10.11", "2012-08-11T00:00:00.00"),
            Arguments.of("Дедлайн этой ночью", "2012-08-10T02:09:10.11", "2012-08-10T00:00:00.00"),
            Arguments.of("Дедлайн сегодня ночью", "2012-08-10T08:09:10.11", "2012-08-11T00:00:00.00"),
            Arguments.of("Дедлайн сегодня ночью", "2012-08-10T02:09:10.11", "2012-08-10T00:00:00.00"),
            Arguments.of("Дедлайн ночью", "2012-08-10T08:09:10.11", "2012-08-11T00:00:00.00"),
            Arguments.of("Дедлайн ночью", "2012-08-10T02:09:10.11", "2012-08-10T00:00:00.00"),
            Arguments.of("Дедлайн в полночь", "2012-08-10T08:09:10.11", "2012-08-11T00:00:00.00"),
            Arguments.of("Дедлайн в полночь", "2012-08-10T02:09:10.11", "2012-08-10T00:00:00.00"),
            Arguments.of("Дедлайн вчера вечером", "2012-08-10T08:09:10.11", "2012-08-09T20:00:00.00"),
            Arguments.of("Дедлайн завтра утром", "2012-08-10T08:09:10.11", "2012-08-11T06:00:00.00"),
        )

        @JvmStatic
        fun rangeExpressionArgs(): Stream<Arguments> = Stream.of(
            Arguments.of(
                "Событие с сегодня и до послезавтра",
                "2012-08-10T12:00:00",
                "2012-08-10T12:00:00",
                "2012-08-12T12:00:00"
            ),
            Arguments.of(
                "Событие сегодня-завтра",
                "2012-08-10T12:00:00",
                "2012-08-10T12:00:00",
                "2012-08-11T12:00:00"
            ),
        )
    }
}