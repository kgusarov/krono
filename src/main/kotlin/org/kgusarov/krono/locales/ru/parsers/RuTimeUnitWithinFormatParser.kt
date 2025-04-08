package org.kgusarov.krono.locales.ru.parsers

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.kgusarov.krono.ParserResult
import org.kgusarov.krono.ParserResultFactory
import org.kgusarov.krono.ParsingComponents
import org.kgusarov.krono.ParsingContext
import org.kgusarov.krono.RegExpMatchArray
import org.kgusarov.krono.common.parsers.AbstractParserWithWordBoundaryChecking
import org.kgusarov.krono.locales.ru.RuConstants
import org.kgusarov.krono.locales.ru.parseTimeUnits

@SuppressFBWarnings("EI_EXPOSE_REP")
class RuTimeUnitWithinFormatParser : AbstractParserWithWordBoundaryChecking() {
    override fun patternLeftBoundary() = RuConstants.LEFT_BOUNDARY

    override fun innerPattern(context: ParsingContext) =
        when (context.option.forwardDate) {
            true -> Regex(PATTERN, RegexOption.IGNORE_CASE)
            false -> Regex("(?:(?:в течение|в течении)\\s*)?$PATTERN", RegexOption.IGNORE_CASE)
        }

    override fun innerExtract(
        context: ParsingContext,
        match: RegExpMatchArray,
    ): ParserResult {
        val timeUnits = parseTimeUnits(match[1]!!)
        val components = ParsingComponents.createRelativeFromDecimalReference(context.reference, timeUnits)
        return ParserResultFactory(components)
    }

    companion object {
        @JvmStatic
        private val PATTERN =
            "(?:(?:около|примерно)\\s*(?:~\\s*)?)?(${RuConstants.TIME_UNITS_PATTERN})${RuConstants.RIGHT_BOUNDARY}"
    }
}
