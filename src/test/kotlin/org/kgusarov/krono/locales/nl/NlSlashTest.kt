package org.kgusarov.krono.locales.nl

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.kgusarov.krono.Krono
import org.kgusarov.krono.assertDate
import org.kgusarov.krono.testSingleCase
import org.kgusarov.krono.testUnexpectedResult
import java.util.stream.Stream

internal class NlSlashTest {
    @ParameterizedTest
    @MethodSource("singleExpressionArgs")
    internal fun `single expression`(text: String, refDate: String, expectedDate: String) {
        testSingleCase(Krono.nlCasual, text, refDate) {
            it.start.assertDate(expectedDate)
        }
    }

    @ParameterizedTest
    @MethodSource("rangeExpressionArgs")
    internal fun `range expression`(
        text: String,
        refDate: String,
        expectedStartDate: String,
        expectedEndDate: String,
    ) {
        testSingleCase(Krono.nlCasual, text, refDate) {
            it.start.assertDate(expectedStartDate)
            it.end!!.assertDate(expectedEndDate)
        }
    }

    @Test
    internal fun `random negative text`() {
        testUnexpectedResult(Krono.nlCasual, "8/32/2014")
        testUnexpectedResult(Krono.nlCasual, "8/32")
        testUnexpectedResult(Krono.nlCasual, "2/29/2014")
        testUnexpectedResult(Krono.nlCasual, "2014/22/29")
        testUnexpectedResult(Krono.nlCasual, "2014/13/22")
        testUnexpectedResult(Krono.nlCasual, "80-32-89-89")
    }

    companion object {
        @JvmStatic
        fun singleExpressionArgs(): Stream<Arguments> = Stream.of(
            Arguments.of("    04/2016   ", "2012-08-10T12:00:00", "2016-04-01T12:00:00"),
            Arguments.of("Het evenement gaat door (04/2016)", "2012-08-10T12:00:00", "2016-04-01T12:00:00"),
            Arguments.of("Gepubliceerd: 06/2004", "2012-08-10T12:00:00", "2004-06-01T12:00:00"),
            Arguments.of("8/10/2012", "2012-08-10T12:00:00", "2012-10-08T12:00:00"),
            Arguments.of(": 8/1/2012", "2012-08-10T12:00:00", "2012-01-08T12:00:00"),
            Arguments.of("8/10", "2012-08-10T12:00:00", "2012-10-08T12:00:00"),
            Arguments.of("De deadline is 8/10/2012", "2012-08-10T12:00:00", "2012-10-08T12:00:00"),
            Arguments.of("De deadline is dinsdag 11/3/2015", "2012-08-10T12:00:00", "2015-03-11T12:00:00"),
            Arguments.of("28/2/2014", "2012-08-10T12:00:00", "2014-02-28T12:00:00"),
            Arguments.of("30-12-16", "2012-08-10T12:00:00", "2016-12-30T12:00:00"),
            Arguments.of("vrijdag 30-12-16", "2012-08-10T12:00:00", "2016-12-30T12:00:00"),
            Arguments.of("8/10/2012", "2012-08-10T12:00:00", "2012-10-08T12:00:00"),
            Arguments.of("8 oktober 2012", "2012-08-10T12:00:00", "2012-10-08T12:00:00"),
            Arguments.of("2015-05-25", "2012-08-10T12:00:00", "2015-05-25T12:00:00"),
            Arguments.of("2015/05/25", "2012-08-10T12:00:00", "2015-05-25T12:00:00"),
            Arguments.of("2015.05.25", "2012-08-10T12:00:00", "2015-05-25T12:00:00"),
            Arguments.of("25-05-2015", "2012-08-10T12:00:00", "2015-05-25T12:00:00"),
            Arguments.of("25/05/2015", "2012-08-10T12:00:00", "2015-05-25T12:00:00"),
            Arguments.of("25.05.2015", "2012-08-10T12:00:00", "2015-05-25T12:00:00"),
        )

        @JvmStatic
        fun rangeExpressionArgs(): Stream<Arguments> = Stream.of(
            Arguments.of(
                "10/8/2012 - 15/8/2012",
                "2012-08-10T12:00:00",
                "2012-08-10T12:00:00",
                "2012-08-15T12:00:00",
            ),
        )
    }
}
