package org.kgusarov.krono.common.parsers

import org.kgusarov.krono.ParsingContext

abstract class AbstractParserWithLeftBoundaryChecking : AbstractParserWithWordBoundaryChecking() {
    override fun innerPattern(context: ParsingContext): Regex = Regex(innerPatternString(context), RegexOption.IGNORE_CASE)

    override fun innerPatternHasChange(
        context: ParsingContext,
        currentInnerPattern: Regex,
    ) = false

    abstract fun innerPatternString(context: ParsingContext): String
}
