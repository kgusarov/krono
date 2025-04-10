package org.kgusarov.krono.common.parsers

import org.kgusarov.krono.ParsingContext

abstract class AbstractParserWithLeftRightBoundaryChecking : AbstractParserWithLeftBoundaryChecking() {
    override fun innerPattern(context: ParsingContext): Regex =
        Regex("${innerPatternString(context)}${patternRightBoundary()}", RegexOption.IGNORE_CASE)

    abstract fun patternRightBoundary(): String
}
