package org.kgusarov.krono.locales.ru.parsers

import org.kgusarov.krono.common.parsers.AbstractParserWithLeftBoundaryChecking
import org.kgusarov.krono.common.parsers.AbstractParserWithLeftRightBoundaryChecking
import org.kgusarov.krono.locales.ru.RuConstants

abstract class AbstractRuParserWithLeftBoundaryChecking : AbstractParserWithLeftBoundaryChecking() {
    override fun patternLeftBoundary() = RuConstants.LEFT_BOUNDARY
}

abstract class AbstractRuParserWithLeftRightBoundaryChecking : AbstractParserWithLeftRightBoundaryChecking() {
    override fun patternRightBoundary() = RuConstants.RIGHT_BOUNDARY
}
