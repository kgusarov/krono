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

internal class NlCasualTest {
    @ParameterizedTest
    @MethodSource("singleExpressionArgs")
    internal fun `single expression`(text: String, refDate: String, expectedDate: String) {
        testSingleCase(Krono.nlCasual, text, refDate) {
            it.start.assertDate(expectedDate)
        }
    }

    @ParameterizedTest
    @MethodSource("combinedExpressionArgs")
    internal fun `combined expression`(text: String, refDate: String, expectedDate: String) {
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
        testUnexpectedResult(Krono.nlCasual, "notoday")
        testUnexpectedResult(Krono.nlCasual, "tdtmr")
        testUnexpectedResult(Krono.nlCasual, "xyesterday")
        testUnexpectedResult(Krono.nlCasual, "nowhere")
        testUnexpectedResult(Krono.nlCasual, "noway")
        testUnexpectedResult(Krono.nlCasual, "knowledge")
    }

    companion object {
        @JvmStatic
        fun combinedExpressionArgs(): Stream<Arguments> = Stream.of(
            Arguments.of("De deadline is vandaag om 17:00", "2012-08-10T12:00:00.000", "2012-08-10T17:00:00.000"),
            Arguments.of("gisterenochtend", "2012-08-10T14:00:00.000", "2012-08-09T06:00:00.000"),
            Arguments.of("gisterenmiddag", "2012-08-10T14:00:00.000", "2012-08-09T12:00:00.000"),
            Arguments.of("gisterenavond", "2012-08-10T14:00:00.000", "2012-08-09T20:00:00.000"),
            Arguments.of("vanochtend", "2012-08-10T14:00:00.000", "2012-08-10T06:00:00.000"),
            Arguments.of("vanmiddag", "2012-08-10T14:00:00.000", "2012-08-10T12:00:00.000"),
            Arguments.of("vanavond", "2012-08-10T14:00:00.000", "2012-08-10T20:00:00.000"),
            Arguments.of("morgenochtend", "2012-08-10T14:00:00.000", "2012-08-11T06:00:00.000"),
            Arguments.of("morgenmiddag", "2012-08-10T14:00:00.000", "2012-08-11T12:00:00.000"),
            Arguments.of("morgenavond", "2012-08-10T14:00:00.000", "2012-08-11T20:00:00.000"),
        )

        @JvmStatic
        fun rangeExpressionArgs(): Stream<Arguments> = Stream.of(
            Arguments.of(
                "Het evenement is vandaag - volgende vrijdag",
                "2012-08-04T12:00:00.000",
                "2012-08-04T12:00:00.000",
                "2012-08-10T12:00:00.000"
            ),
            Arguments.of(
                "Het evenement is vandaag - volgende vrijdag",
                "2012-08-10T12:00:00.000",
                "2012-08-10T12:00:00.000",
                "2012-08-17T12:00:00.000"
            ),
            Arguments.of(
                "jaarlijks verlof vanaf vandaag tot morgennamiddag",
                "2012-08-04T12:00:00.000",
                "2012-08-04T12:00:00.000",
                "2012-08-05T15:00:00.000"
            ),
            Arguments.of(
                "jaarlijks verlof vanaf deze ochtend tot morgen",
                "2012-08-04T12:00:00.000",
                "2012-08-04T06:00:00.000",
                "2012-08-05T12:00:00.000"
            ),
        )

        @JvmStatic
        fun singleExpressionArgs(): Stream<Arguments> = Stream.of(
            Arguments.of("De deadline is nu", "2012-08-10T08:09:10.011", "2012-08-10T08:09:10.011"),
            Arguments.of("De deadline is vandaag", "2012-08-10T14:12:00.000", "2012-08-10T14:12:00.000"),
            Arguments.of("De deadline is morgen", "2012-08-10T17:10:00.000", "2012-08-11T17:10:00.000"),
            Arguments.of("De deadline was gisteren", "2012-08-10T12:00:00.000", "2012-08-09T12:00:00.000"),
            Arguments.of("De deadline was deze ochtend", "2012-08-10T12:00:00.000", "2012-08-10T06:00:00.000"),
            Arguments.of("De deadline was deze namiddag", "2012-08-10T12:00:00.000", "2012-08-10T15:00:00.000"),
            Arguments.of("De deadline was deze avond", "2012-08-10T12:00:00.000", "2012-08-10T20:00:00.000"),
            Arguments.of("De deadline is vanavond", "2012-08-10T12:00:00.000", "2012-08-10T20:00:00.000"),
            Arguments.of("De deadline is om middernacht", "2012-08-10T01:00:00.000", "2012-08-11T00:00:00.000"),
            Arguments.of("vanavond", "2012-01-01T12:00:00.000", "2012-01-01T20:00:00.000"),
            Arguments.of("middag", "2012-01-01T12:00:00.000", "2012-01-01T12:00:00.000"),
            Arguments.of("vanavond 22:00", "2012-01-01T12:00:00.000", "2012-01-01T22:00:00.000"),
            Arguments.of("vanavond om 21:00", "2012-01-01T12:00:00.000", "2012-01-01T21:00:00.000"),
            Arguments.of("morgen voor 16:00", "2012-01-01T12:00:00.000", "2012-01-02T16:00:00.000"),
            Arguments.of("morgen na 16:00", "2012-01-01T12:00:00.000", "2012-01-02T16:00:00.000"),
            Arguments.of("donderdag", "2016-10-01T12:00:00.000", "2016-09-29T12:00:00.000"),
            Arguments.of("deze avond", "2016-10-01T12:00:00.000", "2016-10-01T20:00:00.000"),
            Arguments.of("gisterennamiddag", "2016-10-01T12:00:00.000", "2016-09-30T15:00:00.000"),
            Arguments.of("morgenochtend", "2016-10-01T08:00:00.000", "2016-10-02T06:00:00.000"),
            Arguments.of("deze namiddag om 15:00", "2016-10-01T08:00:00.000", "2016-10-01T15:00:00.000"),
        )
    }
}
