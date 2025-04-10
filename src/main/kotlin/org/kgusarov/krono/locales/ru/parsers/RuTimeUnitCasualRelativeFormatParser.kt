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

@SuppressFBWarnings("EI_EXPOSE_REP")
class RuTimeUnitCasualRelativeFormatParser : AbstractRuParserWithLeftBoundaryChecking() {
    override fun innerPatternString(context: ParsingContext) = PATTERN

    override fun innerExtract(
        context: ParsingContext,
        match: RegExpMatchArray,
    ): ParserResult {
        val prefix = match[1]!!.lowercase()
        var timeUnits = parseTimeUnits(match[2]!!)
        if (prefix == "последние" || prefix == "прошлые" || prefix == "-") {
            timeUnits = reverseDecimalTimeUnits(timeUnits)
        }

        val components = ParsingComponents.createRelativeFromDecimalReference(context.reference, timeUnits)
        return ParserResultFactory(components)
    }

    companion object {
        @JvmStatic
        private val PATTERN =
            "(эти|последние|прошлые|следующие|после|спустя|через|\\+|-)\\s*" +
                "(${RuConstants.TIME_UNITS_PATTERN})"
    }
}
