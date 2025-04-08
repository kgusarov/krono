package org.kgusarov.krono.locales.nl

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.kgusarov.krono.Krono
import org.kgusarov.krono.ParsingOption
import org.kgusarov.krono.assertDate
import org.kgusarov.krono.testSingleCase
import java.util.stream.Stream

internal class NlMonthNameLittleEndianTest {
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
    internal fun `forward option`() {
        testSingleCase(Krono.nlCasual, "22-23 februari om 19:00", "2016-03-15T12:00:00") {
            it.start.assertDate("2016-02-22T19:00:00")
            it.end!!.assertDate("2016-02-23T19:00:00")
        }

        testSingleCase(
            Krono.nlCasual,
            "22-23 februari om 19:00",
            "2016-03-15T12:00:00",
            ParsingOption(forwardDate = true)
        ) {
            it.start.assertDate("2017-02-22T19:00:00")
            it.end!!.assertDate("2017-02-23T19:00:00")
        }
    }

    companion object {
        @JvmStatic
        fun singleExpressionArgs(): Stream<Arguments> = Stream.of(
            Arguments.of("10 augustus 2012", "2012-08-10T12:00:00", "2012-08-10T12:00:00"),
            Arguments.of("3 februari 82", "2012-08-10T12:00:00", "1982-02-03T12:00:00"),
            Arguments.of("10 augustus 234 voor Christus", "2012-08-10T12:00:00", "-0234-08-10T12:00:00"),
            Arguments.of("10 augustus 88 na Christus", "2012-08-10T12:00:00", "0088-08-10T12:00:00"),
            Arguments.of("Zon 15 Sept", "2013-08-10T12:00:00", "2013-09-15T12:00:00"),
            Arguments.of("ZON 15 SEPT", "2013-08-10T12:00:00", "2013-09-15T12:00:00"),
            Arguments.of("De deadline is 10 augustus", "2012-08-10T12:00:00", "2012-08-10T12:00:00"),
            Arguments.of("De deadline is dinsdag, 10 januari", "2012-08-10T12:00:00", "2013-01-10T12:00:00"),
            Arguments.of("De deadline is di, 10 januari", "2012-08-10T12:00:00", "2013-01-10T12:00:00"),
            Arguments.of("31ste maart 2016", "2012-08-10T12:00:00", "2016-03-31T12:00:00"),
            Arguments.of("23ste februari 2016", "2012-08-10T12:00:00", "2016-02-23T12:00:00"),
            Arguments.of("12de juli om 19:00", "2012-08-10T12:00:00", "2012-07-12T19:00:00"),
            Arguments.of("5 mei 12:00", "2012-08-10T12:00:00", "2012-05-05T12:00:00"),
            Arguments.of("7 mei 11:00", "2012-08-10T12:00:00", "2012-05-07T11:00:00"),
            Arguments.of("vierentwintigste mei", "2012-08-10T12:00:00", "2012-05-24T12:00:00"),
            Arguments.of("achtste tot elfde mei 2010", "2012-08-10T12:00:00", "2010-05-08T12:00:00"),
            Arguments.of("24ste oktober, 9:00", "2017-08-10T12:00:00", "2017-10-24T09:00:00"),
            Arguments.of("24ste oktober, 21:00", "2017-08-10T12:00:00", "2017-10-24T21:00:00"),
            Arguments.of("24 oktober, 21:00", "2017-08-10T12:00:00", "2017-10-24T21:00:00"),
            Arguments.of("03 aug 96", "2012-08-10T12:00:00", "1996-08-03T12:00:00"),
            Arguments.of("3 aug 96", "2012-08-10T12:00:00", "1996-08-03T12:00:00"),
            Arguments.of("9 aug 96", "2012-08-10T12:00:00", "1996-08-09T12:00:00"),
        )

        @JvmStatic
        fun rangeExpressionArgs(): Stream<Arguments> = Stream.of(
            Arguments.of(
                "10 - 22 augustus 2012", "2012-08-10T12:00:00", "2012-08-10T12:00:00", "2012-08-22T12:00:00"
            ),
            Arguments.of(
                "10 tot 22 augustus 2012", "2012-08-10T12:00:00", "2012-08-10T12:00:00", "2012-08-22T12:00:00"
            ),
            Arguments.of(
                "10 augustus - 12 september", "2012-08-10T12:00:00", "2012-08-10T12:00:00", "2012-09-12T12:00:00"
            ),
            Arguments.of(
                "10 augustus - 12 september 2013", "2012-08-10T12:00:00", "2013-08-10T12:00:00", "2013-09-12T12:00:00"
            ),
        )
    }
}
