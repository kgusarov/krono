package org.kgusarov.krono.locales.nl

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.kgusarov.krono.Krono
import org.kgusarov.krono.testSingleExpression
import java.util.stream.Stream

internal class NlTimeUnitsWithinTest {
    @ParameterizedTest
    @MethodSource("singleExpressionArgs")
    internal fun `single expression`(text: String, refDate: String, expectedDate: String) {
        testSingleExpression(Krono.nlCasual, text, refDate, expectedDate)
    }

    companion object {
        @JvmStatic
        fun singleExpressionArgs(): Stream<Arguments> = Stream.of(
            Arguments.of("we have to make something binnen 5 dagen.", "2012-08-10T12:00:00", "2012-08-15T12:00:00"),
            Arguments.of("we have to make something binnen vijf dagen.", "2012-08-10T12:00:00", "2012-08-15T12:00:00"),
            Arguments.of("we have to make something binnen de 10 dagen", "2012-08-10T12:00:00", "2012-08-20T12:00:00"),
            Arguments.of("binnen 5 minuten", "2012-08-10T12:14:00", "2012-08-10T12:19:00"),
            Arguments.of("wait voor 5 minuten", "2012-08-10T12:14:00", "2012-08-10T12:19:00"),
            Arguments.of("binnen 1 uur", "2012-08-10T12:14:00", "2012-08-10T13:14:00"),
            Arguments.of("Binnen 5 minuten ben ik thuis", "2012-08-10T12:14:00", "2012-08-10T12:19:00"),
            Arguments.of(
                "Binnen de 5 minuten moet een auto zich verzetten", "2012-08-10T12:14:00", "2012-08-10T12:19:00"
            ),
            Arguments.of("Binnen 5 seconden moet een auto zich verzetten", "2012-08-10T12:14:00", "2012-08-10T12:14:05"),
            Arguments.of("Binnen de 2 weken", "2012-08-10T12:14:00", "2012-08-24T12:14:00"),
            Arguments.of("Binnen een maand", "2012-08-10T12:14:00", "2012-09-10T12:14:00"),
            Arguments.of("Binnen een jaar", "2012-08-10T12:14:00", "2013-08-10T12:14:00"),
            Arguments.of("Binnen 5 minuten A car need to move", "2012-08-10T12:14:00", "2012-08-10T12:19:00"),
            Arguments.of("Binnen 5 min a car need to move", "2012-08-10T12:14:00", "2012-08-10T12:19:00"),
            Arguments.of("binnen een week", "2016-10-01T12:00:00", "2016-10-08T12:00:00"),
            Arguments.of("Binnen de 30 dagen", "2012-08-10T12:14:00", "2012-09-09T12:14:00"),
            Arguments.of("Binnen 24 uur", "2012-08-10T12:14:00", "2012-08-11T12:14:00"),
            Arguments.of("binnen een dag", "2012-08-10T12:14:00", "2012-08-11T12:14:00"),
            Arguments.of("binnen 2 minuten", "2016-10-01T14:52:00", "2016-10-01T14:54:00"),
            Arguments.of("binnen 2 uur", "2016-10-01T14:52:00", "2016-10-01T16:52:00"),
            Arguments.of("binnen de 12 maand", "2016-10-01T14:52:00", "2017-10-01T14:52:00"),
            Arguments.of("binnen de 3 dagen", "2016-10-01T14:52:00", "2016-10-04T14:52:00"),
        )
    }
}
