package org.kgusarov.krono.locales.ru

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.kgusarov.krono.Krono
import org.kgusarov.krono.testSingleExpression
import java.util.stream.Stream

internal class RuTimeUnitsWithinTest {
    @ParameterizedTest
    @MethodSource("singleExpressionArgs")
    internal fun `single expression`(text: String, refDate: String, expectedDate: String) {
        testSingleExpression(Krono.ruCasual, text, refDate, expectedDate)
    }

    companion object {
        @JvmStatic
        fun singleExpressionArgs(): Stream<Arguments> = Stream.of(
            Arguments.of("будет сделано в течение минуты", "2012-08-10T12:00:00", "2012-08-10T12:01:00"),
            Arguments.of("будет сделано в течение 2 часов", "2012-08-10T12:00:00", "2012-08-10T14:00:00"),
        )
    }
}
