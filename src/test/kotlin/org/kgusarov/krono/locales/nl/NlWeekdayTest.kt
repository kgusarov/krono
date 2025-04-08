package org.kgusarov.krono.locales.nl

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.kgusarov.krono.Krono
import org.kgusarov.krono.ParsingOption
import org.kgusarov.krono.assertDate
import org.kgusarov.krono.testSingleCase
import org.kgusarov.krono.testSingleExpression
import java.util.stream.Stream

internal class NlWeekdayTest {
    @ParameterizedTest
    @MethodSource("singleExpressionArgs")
    internal fun `single expression`(text: String, refDate: String, expectedDate: String) {
        testSingleExpression(Krono.nlCasual, text, refDate, expectedDate)
    }

    @Test
    internal fun forwardOption() {
        testSingleCase(
            Krono.nlCasual,
            "maandag (forward dates only)",
            "2012-08-10T12:00:00",
            ParsingOption(forwardDate = true)
        ) {
            it.start.assertDate("2012-08-13T12:00:00")
        }

        testSingleCase(
            Krono.nlCasual,
            "deze vrijdag tot deze maandag",
            "2016-08-04T12:00:00",
            ParsingOption(forwardDate = true)
        ) {
            it.start.assertDate("2016-08-05T12:00:00")
            it.end!!.assertDate("2016-08-08T12:00:00")
        }
    }

    companion object {
        @JvmStatic
        fun singleExpressionArgs(): Stream<Arguments> = Stream.of(
            Arguments.of("maandag", "2012-08-09T12:00:00", "2012-08-06T12:00:00"),
            Arguments.of("donderdag", "2012-08-10T12:00:00", "2012-08-09T12:00:00"),
            Arguments.of("zondag", "2012-08-10T12:00:00", "2012-08-12T12:00:00"),
            Arguments.of("De deadline is vorige vrijdag...", "2012-08-09T12:00:00", "2012-08-03T12:00:00"),
            Arguments.of("De deadline is vorige vrijdag...", "2012-08-12T12:00:00", "2012-08-10T12:00:00"),
            Arguments.of(
                "Laten we een meeting hebben op volgende week vrijdag", "2015-04-16T12:00:00", "2015-04-24T12:00:00"
            ),
            Arguments.of(
                "Ik plan een vrije dag op volgende week dinsdag", "2015-04-18T12:00:00", "2015-04-21T12:00:00"
            ),
            Arguments.of("Laten we op dinsdag ochtend afspreken", "2015-04-18T12:00:00", "2015-04-21T06:00:00"),
            Arguments.of("zondag, 7 december 2014", "2012-08-10T12:00:00", "2014-12-07T12:00:00"),
            Arguments.of("zondag 7/12/2014", "2012-08-10T12:00:00", "2014-12-07T12:00:00"),
        )
    }
}
