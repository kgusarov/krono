package org.kgusarov.krono.locales.ru

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.kgusarov.krono.Krono
import org.kgusarov.krono.testSingleExpression
import java.util.stream.Stream

internal class RuRelativeTest {
    @ParameterizedTest
    @MethodSource("singleExpressionArgs")
    internal fun `single expression`(text: String, refDate: String, expectedDate: String) {
        testSingleExpression(Krono.ruCasual, text, refDate, expectedDate)
    }

    companion object {
        @JvmStatic
        fun singleExpressionArgs(): Stream<Arguments> = Stream.of(
            Arguments.of("на этой неделе", "2017-11-19T12:00:00", "2017-11-19T12:00:00"),
            Arguments.of("в этом месяце", "2017-11-19T12:00:00", "2017-11-01T12:00:00"),
            Arguments.of("в этом месяце", "2017-11-01T12:00:00", "2017-11-01T12:00:00"),
            Arguments.of("в этом году", "2017-11-19T12:00:00", "2017-01-01T12:00:00"),
            Arguments.of("на прошлой неделе", "2016-10-01T12:00:00", "2016-09-24T12:00:00"),
            Arguments.of("в прошлом месяце", "2016-11-01T12:00:00", "2016-10-01T12:00:00"),
            Arguments.of("в прошлом году", "2016-10-01T12:00:00", "2015-10-01T12:00:00"),
            Arguments.of("на следующей неделе", "2016-11-01T12:00:00", "2016-11-08T12:00:00"),
            Arguments.of("в следующем месяце", "2016-11-01T12:00:00", "2016-12-01T12:00:00"),
            Arguments.of("в следующем квартале", "2016-10-01T12:00:00", "2017-01-01T12:00:00"),
            Arguments.of("в следующем году", "2016-11-01T12:00:00", "2017-11-01T12:00:00"),
        )
    }
}