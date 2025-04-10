package org.kgusarov.krono.locales.ru

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.kgusarov.krono.Krono
import org.kgusarov.krono.testSingleExpression
import org.kgusarov.krono.testUnexpectedResult
import java.util.stream.Stream

internal class RuTimeUnitsAgoTest {
    @ParameterizedTest
    @MethodSource("singleExpressionArgs")
    internal fun `single expression`(text: String, refDate: String, expectedDate: String) {
        testSingleExpression(Krono.ruCasual, text, refDate, expectedDate)
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
            Arguments.of("5 дней назад что-то было", "2012-08-10T12:00:00", "2012-08-05T12:00:00"),
            Arguments.of("5 минут назад что-то было", "2012-08-10T12:00:00", "2012-08-10T11:55:00"),
            Arguments.of("полчаса назад что-то было", "2012-08-10T12:00:00", "2012-08-10T11:30:00"),
            Arguments.of("5 дней 2 часа назад что-то было", "2012-08-10T12:00:00", "2012-08-05T10:00:00"),
            Arguments.of("5 минут 20 секунд назад что-то было", "2012-08-10T12:00:00", "2012-08-10T11:54:40"),
            Arguments.of("2 часа 5 минут назад что-то было", "2012-08-10T12:00:00", "2012-08-10T09:55:00"),
        )

        @JvmStatic
        fun negativeTextArgs(): Stream<Arguments> = Stream.of(
            Arguments.of(true, "15 часов 29 мин"),
            Arguments.of(true, "несколько часов"),
            Arguments.of(true, "5 дней"),
            Arguments.of(false, "15 часов 29 мин"),
            Arguments.of(false, "несколько часов"),
            Arguments.of(false, "5 дней"),
        )
    }
}
