package org.kgusarov.krono.locales.nl

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.kgusarov.krono.Krono
import org.kgusarov.krono.KronoMeridiem
import org.kgusarov.krono.testSingleCase

internal class NlTimeExpressionTest {
    @Test
    internal fun `parsing text offset`() {
        testSingleCase(Krono.nlCasual, "  11:00 ", "2016-10-01T08:00:00") {
            assertThat(it.index).isEqualTo(2)
            assertThat(it.text).isEqualTo("11:00")
        }

        testSingleCase(Krono.nlCasual, "2020 om  11:00 ", "2016-10-01T08:00:00") {
            assertThat(it.index).isEqualTo(5)
            assertThat(it.text).isEqualTo("om  11:00")
        }
    }

    @Test
    internal fun `time expression`() {
        testSingleCase(Krono.nlCasual, "20:32:13", "2016-10-01T08:00:00") {
            assertThat(it.index).isEqualTo(0)
            assertThat(it.text).isEqualTo("20:32:13")
            assertThat(it.start.hour()).isEqualTo(20)
            assertThat(it.start.minute()).isEqualTo(32)
            assertThat(it.start.second()).isEqualTo(13)
            assertThat(it.start.meridiem()).isEqualTo(KronoMeridiem.PM)
        }
    }

    @Test
    internal fun `time range expression`() {
        testSingleCase(Krono.nlCasual, "10:00:00 - 21:45:00", "2016-10-01T08:00:00") {
            assertThat(it.text).isEqualTo("10:00:00 - 21:45:00")

            with(it.start) {
                assertThat(hour()).isEqualTo(10)
                assertThat(minute()).isEqualTo(0)
                assertThat(second()).isEqualTo(0)
                assertThat(meridiem()).isEqualTo(KronoMeridiem.AM)
            }

            with(it.end!!) {
                assertThat(hour()).isEqualTo(21)
                assertThat(minute()).isEqualTo(45)
                assertThat(second()).isEqualTo(0)
                assertThat(meridiem()).isEqualTo(KronoMeridiem.PM)
            }
        }
    }

    @Test
    internal fun `casual time number expression`() {
        testSingleCase(Krono.nlCasual, "23:00 's avonds", "2016-10-01T08:00:00") {
            assertThat(it.text).isEqualTo("23:00 's avonds")

            with(it.start) {
                assertThat(year()).isEqualTo(2016)
                assertThat(month()).isEqualTo(10)
                assertThat(day()).isEqualTo(1)
                assertThat(hour()).isEqualTo(23)
            }
        }

        testSingleCase(Krono.nlCasual, "23:00 vanavond", "2016-10-01T08:00:00") {
            assertThat(it.text).isEqualTo("23:00 vanavond")

            with(it.start) {
                assertThat(year()).isEqualTo(2016)
                assertThat(month()).isEqualTo(10)
                assertThat(day()).isEqualTo(1)
                assertThat(hour()).isEqualTo(23)
            }
        }

        testSingleCase(Krono.nlCasual, "6:00 's ochtends", "2016-10-01T08:00:00") {
            assertThat(it.text).isEqualTo("6:00 's ochtends")

            with(it.start) {
                assertThat(year()).isEqualTo(2016)
                assertThat(month()).isEqualTo(10)
                assertThat(day()).isEqualTo(1)
                assertThat(hour()).isEqualTo(6)
                assertThat(minute()).isEqualTo(0)
                assertThat(meridiem()).isEqualTo(KronoMeridiem.AM)
            }
        }

        testSingleCase(Krono.nlCasual, "6:00 in de namiddag", "2016-10-01T08:00:00") {
            assertThat(it.text).isEqualTo("6:00 in de namiddag")

            with(it.start) {
                assertThat(year()).isEqualTo(2016)
                assertThat(month()).isEqualTo(10)
                assertThat(day()).isEqualTo(1)
                assertThat(hour()).isEqualTo(18)
                assertThat(minute()).isEqualTo(0)
                assertThat(meridiem()).isEqualTo(KronoMeridiem.PM)
            }
        }
    }
}
