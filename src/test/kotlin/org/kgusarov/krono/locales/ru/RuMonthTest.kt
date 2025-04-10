package org.kgusarov.krono.locales.ru

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.kgusarov.krono.Krono
import org.kgusarov.krono.testSingleExpression
import java.util.stream.Stream

internal class RuMonthTest {
    @ParameterizedTest
    @MethodSource("singleExpressionArgs")
    internal fun `single expression`(text: String, refDate: String, expectedDate: String) {
        testSingleExpression(Krono.ruCasual, text, refDate, expectedDate)
    }

    companion object {
        @JvmStatic
        fun singleExpressionArgs(): Stream<Arguments> = Stream.of(
            Arguments.of("Сентябрь 2012", "2012-08-10T12:00:00", "2012-09-01T12:00:00"),
            Arguments.of("сен 2012", "2012-08-10T12:00:00", "2012-09-01T12:00:00"),
            Arguments.of("сен. 2012", "2012-08-10T12:00:00", "2012-09-01T12:00:00"),
            Arguments.of("сен-2012", "2012-08-10T12:00:00", "2012-09-01T12:00:00"),
            Arguments.of("в январе", "2012-08-10T12:00:00", "2013-01-01T12:00:00"),
            Arguments.of("в янв", "2012-08-10T12:00:00", "2013-01-01T12:00:00"),
            Arguments.of("май", "2012-11-22T12:00:00", "2013-05-01T12:00:00"),
            Arguments.of(
                "Это было в сентябре 2012 перед новым годом", "2012-08-10T12:00:00", "2012-09-01T12:00:00"
            ),
            Arguments.of("авг 96", "2012-08-10T12:00:00", "1996-08-01T12:00:00"),
            Arguments.of("96 авг 96", "2012-08-10T12:00:00", "1996-08-01T12:00:00"),
        )
    }
}