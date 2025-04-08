package org.kgusarov.krono.locales.nl

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.kgusarov.krono.Krono
import org.kgusarov.krono.testSingleExpression
import java.util.stream.Stream

internal class NlTimeUnitsLaterTest {
    @ParameterizedTest
    @MethodSource("singleExpressionArgs")
    internal fun `single expression`(text: String, refDate: String, expectedDate: String) {
        testSingleExpression(Krono.nlCasual, text, refDate, expectedDate)
    }

    @ParameterizedTest
    @MethodSource("strictSingleExpressionArgs")
    internal fun `strict single expression`(text: String, refDate: String, expectedDate: String) {
        testSingleExpression(Krono.nlStrict, text, refDate, expectedDate)
    }

    companion object {
        @JvmStatic
        fun singleExpressionArgs(): Stream<Arguments> = Stream.of(
            Arguments.of("2 dagen later", "2012-08-10T12:00:00", "2012-08-12T12:00:00"),
            Arguments.of("5 minuten later", "2012-08-10T10:00:00", "2012-08-10T10:05:00"),
            Arguments.of("3 weken later", "2012-08-10T10:00:00", "2012-08-31T10:00:00"),
            Arguments.of("5 dagen vanaf nu we hebben iets gedaan", "2012-08-10T12:00:00", "2012-08-15T12:00:00"),
            Arguments.of("10 dagen vanaf nu we hebben iets gedaan", "2012-08-10T12:00:00", "2012-08-20T12:00:00"),
            Arguments.of("15 minuten vanaf nu", "2012-08-10T12:14:00", "2012-08-10T12:29:00"),
            Arguments.of("15 minuten eerder", "2012-08-10T12:14:00", "2012-08-10T11:59:00"),
            Arguments.of("15 minuten uit", "2012-08-10T12:14:00", "2012-08-10T12:29:00"),
            Arguments.of("12 uur vanaf nu", "2012-08-10T12:14:00", "2012-08-11T00:14:00"),
            Arguments.of("half uur vanaf nu", "2012-08-10T12:14:00", "2012-08-10T12:44:00"),
            Arguments.of("Over 12 uur heb ik iets gedaan", "2012-08-10T12:14:00", "2012-08-11T00:14:00"),
            Arguments.of("Over 12 seconden heb ik iets gedaan", "2012-08-10T12:14:00", "2012-08-10T12:14:12"),
            Arguments.of("over drie seconden heb ik iets gedaan", "2012-08-10T12:14:00", "2012-08-10T12:14:03"),
            Arguments.of("Over 5 dagen hebben we iets gedaan", "2012-08-10T12:00:00", "2012-08-15T12:00:00"),
            Arguments.of("Over een dag hebben we iets gedaan", "2012-08-10T12:00:00", "2012-08-11T12:00:00"),
            Arguments.of("een minuutje uit", "2012-08-10T12:14:00", "2012-08-10T12:15:00"),
            Arguments.of("in 1 uur", "2012-08-10T12:14:00", "2012-08-10T13:14:00"),
            Arguments.of("over 1,5 uur", "2012-08-10T12:40:00", "2012-08-10T14:10:00"),
        )

        @JvmStatic
        fun strictSingleExpressionArgs(): Stream<Arguments> = Stream.of(
            Arguments.of("15 minuten vanaf nu", "2012-08-10T12:14:00", "2012-08-10T12:29:00"),
            Arguments.of("25 minuten later", "2012-08-10T12:40:00", "2012-08-10T13:05:00"),
        )
    }
}
