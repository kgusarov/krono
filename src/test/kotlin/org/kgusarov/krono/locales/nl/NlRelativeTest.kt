package org.kgusarov.krono.locales.nl

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.kgusarov.krono.Krono
import org.kgusarov.krono.KronoComponents
import org.kgusarov.krono.ReferenceWithTimezone
import org.kgusarov.krono.assertDate
import org.kgusarov.krono.assertOffsetDate
import org.kgusarov.krono.testSingleCase
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.stream.Stream

internal class NlRelativeTest {
    @ParameterizedTest
    @MethodSource("singleExpressionArgs")
    internal fun `single expression`(text: String, refDate: String, expectedDate: String) {
        testSingleCase(Krono.nlCasual, text, refDate) {
            it.start.assertDate(expectedDate)
        }
    }

    @Test
    internal fun `JST ref - now`() {
        testSingleCase(Krono.nlCasual, "nu", JST_REF) {
            assertThat(it.text).isEqualTo("nu")

            with(it.start) {
                imply(KronoComponents.Offset, 3600)

                assertOffsetDate("2020-11-29T13:24:13+09:00")
                assertOffsetDate("2020-11-29T05:24:13+01:00")
            }
        }
    }

    @Test
    internal fun `JST ref - morgen om 17 uur`() {
        testSingleCase(Krono.nlCasual, "morgen om 17 uur", JST_REF) {
            assertThat(it.text).isEqualTo("morgen om 17 uur")

            with(it.start) {
                imply(KronoComponents.Offset, 3600)

                assertOffsetDate("2020-12-01T01:00:00+09:00")
                assertOffsetDate("2020-11-30T17:00:00+01:00")
            }
        }
    }

    @Test
    internal fun `JST ref - binnen 10 minuten`() {
        testSingleCase(Krono.nlCasual, "binnen 10 minuten", JST_REF) {
            assertThat(it.text).isEqualTo("binnen 10 minuten")

            with(it.start) {
                imply(KronoComponents.Offset, 3600)

                assertOffsetDate("2020-11-29T13:34:13+09:00")
                assertOffsetDate("2020-11-29T05:34:13+01:00")
            }
        }
    }

    companion object {
        private val JST_REF =
            run {
                val zdt = ZonedDateTime.of(LocalDateTime.parse("2020-11-29T13:24:13"), ZoneId.of("Japan"))
                ReferenceWithTimezone(zdt.toLocalDateTime(), zdt.zone)
            }

        @JvmStatic
        fun singleExpressionArgs(): Stream<Arguments> = Stream.of(
            Arguments.of("deze week", "2017-11-19T12:00:00", "2017-11-19T12:00:00"),
            Arguments.of("deze maand", "2017-11-19T12:00:00", "2017-11-01T12:00:00"),
            Arguments.of("dit jaar", "2017-11-19T12:00:00", "2017-01-01T12:00:00"),
            Arguments.of("afgelopen week", "2016-10-01T12:00:00", "2016-09-24T12:00:00"),
            Arguments.of("afgelopen maand", "2016-10-01T12:00:00", "2016-09-01T12:00:00"),
            Arguments.of("afgelopen jaar", "2016-10-01T12:00:00", "2015-10-01T12:00:00"),
            Arguments.of("vorige week", "2016-10-01T12:00:00", "2016-09-24T12:00:00"),
            Arguments.of("komend uur", "2016-10-01T12:00:00", "2016-10-01T13:00:00"),
            Arguments.of("volgende week", "2016-10-01T12:00:00", "2016-10-08T12:00:00"),
            Arguments.of("volgende dag", "2016-10-01T12:00:00", "2016-10-02T12:00:00"),
            Arguments.of("volgende maand", "2016-10-01T12:00:00", "2016-11-01T12:00:00"),
            Arguments.of("aankomende maand", "2016-10-01T12:00:00", "2016-11-01T12:00:00"),
            Arguments.of("volgend jaar", "2016-10-01T12:00:00", "2017-10-01T12:00:00"),
        )
    }
}
