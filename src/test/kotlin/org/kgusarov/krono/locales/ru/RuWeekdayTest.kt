package org.kgusarov.krono.locales.ru

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

internal class RuWeekdayTest {
    @ParameterizedTest
    @MethodSource("singleExpressionArgs")
    internal fun `single expression`(text: String, refDate: String, expectedDate: String) {
        testSingleExpression(Krono.ruCasual, text, refDate, expectedDate)
    }

    @Test
    internal fun `forward date`() {
        testSingleCase(
            Krono.ruCasual,
            "В понедельник?",
            "2012-08-09T12:00:00",
            option = ParsingOption(forwardDate = true),
        ) {
            it.start.assertDate("2012-08-13T12:00:00",)
        }
    }

    companion object {
        @JvmStatic
        fun singleExpressionArgs(): Stream<Arguments> = Stream.of(
            Arguments.of("понедельник", "2012-08-10T12:00:00", "2012-08-13T12:00:00"),
            Arguments.of("Дедлайн в пятницу...", "2012-08-10T12:00:00", "2012-08-10T12:00:00"),
            Arguments.of("Дедлайн в прошлый четверг!", "2012-08-09T12:00:00", "2012-08-02T12:00:00"),
            Arguments.of("Дедлайн в следующий вторник", "2012-08-10T12:00:00", "2012-08-14T12:00:00"),
            Arguments.of("Позвони в среду утром", "2015-04-18T12:00:00", "2015-04-15T06:00:00"),
            Arguments.of("воскресенье, 7 декабря 2014", "2012-08-10T12:00:00", "2014-12-07T12:00:00")
        )
    }
}
