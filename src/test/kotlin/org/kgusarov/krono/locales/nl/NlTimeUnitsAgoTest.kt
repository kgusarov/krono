package org.kgusarov.krono.locales.nl

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.kgusarov.krono.Krono
import org.kgusarov.krono.testSingleExpression
import java.util.stream.Stream

internal class NlTimeUnitsAgoTest {
    @ParameterizedTest
    @MethodSource("singleExpressionArgs")
    internal fun `single expression`(text: String, refDate: String, expectedDate: String) {
        testSingleExpression(Krono.nlCasual, text, refDate, expectedDate)
    }

    companion object {
        @JvmStatic
        fun singleExpressionArgs(): Stream<Arguments> = Stream.of(
            Arguments.of("5 dagen geleden, hebben we wat gedaan", "2012-08-10T12:00:00", "2012-08-05T12:00:00"),
            Arguments.of("10 dagen geleden, hebben we wat gedaan", "2012-08-10T12:00:00", "2012-07-31T12:00:00"),
            Arguments.of("15 minuten geleden", "2012-08-10T12:14:00", "2012-08-10T11:59:00"),
            Arguments.of("15 minuten eerder", "2012-08-10T12:14:00", "2012-08-10T11:59:00"),
            Arguments.of("15 minuten voor", "2012-08-10T12:14:00", "2012-08-10T11:59:00"),
            Arguments.of("12 uur geleden", "2012-08-10T12:14:00", "2012-08-10T00:14:00"),
            Arguments.of("1u geleden", "2012-08-10T12:14:00", "2012-08-10T11:14:00"),
            Arguments.of("half uur geleden", "2012-08-10T12:14:00", "2012-08-10T11:44:00"),
            Arguments.of("12 uur geleden deed ik iets", "2012-08-10T12:14:00", "2012-08-10T00:14:00"),
            Arguments.of("12 seconden geleden deed ik iets", "2012-08-10T12:14:00", "2012-08-10T12:13:48"),
            Arguments.of("drie seconden geleden deed ik iets", "2012-08-10T12:14:00", "2012-08-10T12:13:57"),
            Arguments.of("5 dagen geleden, hebben we iets gedaan", "2012-08-10T12:00:00", "2012-08-05T12:00:00"),
            Arguments.of("Een dag geleden, hebben we wat gedaan", "2012-08-10T12:00:00", "2012-08-09T12:00:00"),
            Arguments.of("een minuut geleden", "2012-08-10T12:14:00", "2012-08-10T12:13:00"),
            Arguments.of("5 maanden geleden, hebben we iets gedaan", "2012-08-10T12:00:00", "2012-03-10T12:00:00"),
            Arguments.of("5 jaar geleden, hebben we iets gedaan", "2012-08-10T12:00:00", "2007-08-10T12:00:00"),
            Arguments.of("een week geleden, hebben we iets gedaan", "2012-08-10T12:00:00", "2012-08-03T12:00:00"),
            Arguments.of("paar dagen geleden, hebben we iets gedaan", "2012-08-10T12:00:00", "2012-08-08T12:00:00"),
            Arguments.of("15 uur 29 minuten geleden", "2012-08-10T22:30:00", "2012-08-10T07:01:00"),
            Arguments.of("1 dag 21 uur geleden ", "2012-08-10T22:30:00", "2012-08-09T01:30:00"),
            Arguments.of("3 min 49 sec geleden ", "2012-08-10T22:30:00", "2012-08-10T22:26:11"),
        )
    }
}
