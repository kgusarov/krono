package org.kgusarov.krono.locales.ru.parsers

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.kgusarov.krono.ParserResult
import org.kgusarov.krono.ParserResultFactory
import org.kgusarov.krono.ParsingComponents
import org.kgusarov.krono.ParsingContext
import org.kgusarov.krono.RegExpMatchArray
import org.kgusarov.krono.locales.ru.RuConstants
import org.kgusarov.krono.locales.ru.parseTimeUnits
import org.kgusarov.krono.utils.reverseDecimalTimeUnits

@SuppressFBWarnings("EI_EXPOSE_REP", "RCN_REDUNDANT_NULLCHECK_OF_NONNULL_VALUE")
class RuTimeUnitAgoFormatParser : AbstractRuParserWithLeftBoundaryChecking() {
    override fun innerPatternString(context: ParsingContext) = PATTERN

    override fun innerExtract(
        context: ParsingContext,
        match: RegExpMatchArray,
    ): ParserResult? {
        val timeUnits = parseTimeUnits(match[1]!!) ?: return null
        val outputTimeUnits = reverseDecimalTimeUnits(timeUnits)
        val components = ParsingComponents.createRelativeFromDecimalReference(context.reference, outputTimeUnits)

        return ParserResultFactory(components)
    }

    companion object {
        @JvmStatic
        private val PATTERN = "(${RuConstants.TIME_UNITS_PATTERN})\\s{0,5}назад(?=(?:\\W|$))"
    }
}
