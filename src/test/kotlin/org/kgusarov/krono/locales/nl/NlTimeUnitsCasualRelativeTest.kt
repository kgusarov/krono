package org.kgusarov.krono.locales.nl

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.kgusarov.krono.Krono
import org.kgusarov.krono.assertDate
import org.kgusarov.krono.testSingleCase
import java.util.stream.Stream

internal class NlTimeUnitsCasualRelativeTest {
    @Test
    internal fun `positive time units`() {
        testSingleCase(Krono.nlCasual, "komende 2 weken", "2016-10-01T12:00:00") {
            assertThat(it.text).isEqualTo("komende 2 weken")
            with(it.start) {
                assertThat(year()).isEqualTo(2016)
                assertThat(month()).isEqualTo(10)
                assertThat(day()).isEqualTo(15)
            }
        }

        testSingleCase(Krono.nlCasual, "komende 2 dagen", "2016-10-01T12:00:00") {
            assertThat(it.text).isEqualTo("komende 2 dagen")
            with(it.start) {
                assertThat(year()).isEqualTo(2016)
                assertThat(month()).isEqualTo(10)
                assertThat(day()).isEqualTo(3)
                assertThat(hour()).isEqualTo(12)
            }
        }

        testSingleCase(Krono.nlCasual, "komende 2 jaar", "2016-10-01T12:00:00") {
            assertThat(it.text).isEqualTo("komende 2 jaar")
            with(it.start) {
                assertThat(year()).isEqualTo(2018)
                assertThat(month()).isEqualTo(10)
                assertThat(day()).isEqualTo(1)
                assertThat(hour()).isEqualTo(12)
            }
        }

        testSingleCase(Krono.nlCasual, "komende 2 weken 3 dagen", "2016-10-01T12:00:00") {
            assertThat(it.text).isEqualTo("komende 2 weken 3 dagen")
            with(it.start) {
                assertThat(year()).isEqualTo(2016)
                assertThat(month()).isEqualTo(10)
                assertThat(day()).isEqualTo(18)
                assertThat(hour()).isEqualTo(12)
            }
        }
    }

    @ParameterizedTest
    @MethodSource("negativeTimeUnitsArgs")
    internal fun `negative time units`(text: String, refDate: String, expectedDate: String) {
        testSingleCase(Krono.nlCasual, text, refDate) {
            it.start.assertDate(expectedDate)
        }
    }

    @ParameterizedTest
    @MethodSource("plusSignArgs")
    internal fun `plus sign`(text: String, refDate: String, expectedDate: String) {
        testSingleCase(Krono.nlCasual, text, refDate) {
            it.start.assertDate(expectedDate)
        }
    }

    @ParameterizedTest
    @MethodSource("minusSignArgs")
    internal fun `minus sign`(text: String, refDate: String, expectedDate: String) {
        testSingleCase(Krono.nlCasual, text, refDate) {
            it.start.assertDate(expectedDate)
        }
    }

    companion object {
        @JvmStatic
        fun negativeTimeUnitsArgs() : Stream<Arguments> = Stream.of(
            Arguments.of("afgelopen 2 weken", "2016-10-01T12:00:00", "2016-09-17T12:00:00"),
            Arguments.of("afgelopen twee weken", "2016-10-01T12:00:00", "2016-09-17T12:00:00"),
            Arguments.of("afgelopen 2 dagen", "2016-10-01T12:00:00", "2016-09-29T12:00:00"),
            Arguments.of("+2 maanden 5 dagen", "2016-10-01T12:00:00", "2016-12-06T12:00:00"),
        )

        @JvmStatic
        fun plusSignArgs() : Stream<Arguments> = Stream.of(
            Arguments.of("+15 minuten", "2012-08-10T12:14:00", "2012-08-10T12:29:00"),
            Arguments.of("+15min", "2012-08-10T12:14:00", "2012-08-10T12:29:00"),
            Arguments.of("+1 dag 2 uur", "2012-08-10T12:14:00", "2012-08-11T14:14:00"),
        )

        @JvmStatic
        fun minusSignArgs(): Stream<Arguments> = Stream.of(
            Arguments.of("-3jr", "2015-08-10T12:14:00", "2012-08-10T12:14:00"),
            Arguments.of("-2u5min", "2016-10-01T12:00:00", "2016-10-01T09:55:00"),
        )
    }
}
