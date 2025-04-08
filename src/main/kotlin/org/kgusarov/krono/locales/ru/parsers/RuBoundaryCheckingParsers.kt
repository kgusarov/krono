package org.kgusarov.krono.locales.ru.parsers

import org.kgusarov.krono.ParsingContext
import org.kgusarov.krono.common.parsers.AbstractParserWithWordBoundaryChecking
import org.kgusarov.krono.locales.ru.RuConstants

abstract class AbstractRuParserWithLeftBoundaryChecking : AbstractParserWithWordBoundaryChecking() {
    override fun patternLeftBoundary() = RuConstants.LEFT_BOUNDARY

    override fun innerPattern(context: ParsingContext): Regex = Regex(innerPatternString(context), RegexOption.IGNORE_CASE)

    override fun innerPatternHasChange(
        context: ParsingContext,
        currentInnerPattern: Regex,
    ) = false

    abstract fun innerPatternString(context: ParsingContext): String
}

abstract class AbstractRuParserWithLeftRightBoundaryChecking : AbstractRuParserWithLeftBoundaryChecking() {
    override fun innerPattern(context: ParsingContext): Regex =
        Regex("${innerPatternString(context)}${RuConstants.RIGHT_BOUNDARY}", RegexOption.IGNORE_CASE)
}
