package org.kgusarov.krono.locales.ru

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.kgusarov.krono.Krono
import org.kgusarov.krono.testSingleExpression
import java.util.stream.Stream

internal class RuTimeUnitsCasualRelativeTest {
    @ParameterizedTest
    @MethodSource("singleExpressionArgs")
    internal fun `single expression`(text: String, refDate: String, expectedDate: String) {
        testSingleExpression(Krono.ruCasual, text, refDate, expectedDate)
    }

    companion object {
        @JvmStatic
        fun singleExpressionArgs(): Stream<Arguments> = Stream.of(
            Arguments.of("следующие 2 недели", "2016-10-01T12:00:00", "2016-10-15T12:00:00"),
            Arguments.of("следующие 2 дня", "2016-10-01T12:00:00", "2016-10-03T12:00:00"),
            Arguments.of("следующие два года", "2016-10-01T12:00:00", "2018-10-01T12:00:00"),
            Arguments.of("следующие 2 недели 3 дня", "2016-10-01T12:00:00", "2016-10-18T12:00:00"),
            Arguments.of("через пару минут", "2016-10-01T12:00:00", "2016-10-01T12:02:00"),
            Arguments.of("через полчаса", "2016-10-01T12:00:00", "2016-10-01T12:30:00"),
            Arguments.of("через 2 часа", "2016-10-01T12:00:00", "2016-10-01T14:00:00"),
            Arguments.of("спустя 2 часа", "2016-10-01T12:00:00", "2016-10-01T14:00:00"),
            Arguments.of("через три месяца", "2016-10-01T12:00:00", "2017-01-01T12:00:00"),
            Arguments.of("через неделю", "2016-10-01T12:00:00", "2016-10-08T12:00:00"),
            Arguments.of("через месяц", "2016-10-01T12:00:00", "2016-11-01T12:00:00"),
            Arguments.of("через год", "2020-11-22T03:11:02.006", "2021-11-22T03:11:02.006"),
            Arguments.of("через год", "2020-11-22T12:11:32.006", "2021-11-22T12:11:32.006"),
            Arguments.of("прошлые 2 недели", "2016-10-01T12:00:00", "2016-09-17T12:00:00"),
            Arguments.of("прошлые 2 дня", "2016-10-01T12:00:00", "2016-09-29T12:00:00"),
            Arguments.of("+15 минут", "2012-08-10T12:14:00", "2012-08-10T12:29:00"),
            Arguments.of("+15мин", "2012-08-10T12:14:00", "2012-08-10T12:29:00"),
            Arguments.of("+1 день 2 часа", "2012-08-10T12:14:00", "2012-08-11T14:14:00"),
            Arguments.of("-3 года", "2015-08-10T12:14:00", "2012-08-10T12:14:00"),
        )
    }
}
