package org.kgusarov.krono.locales.nl.parsers

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.kgusarov.krono.ParserResult
import org.kgusarov.krono.ParserResultFactory
import org.kgusarov.krono.ParsingComponents
import org.kgusarov.krono.ParsingContext
import org.kgusarov.krono.RegExpMatchArray
import org.kgusarov.krono.common.parsers.AbstractParserWithWordBoundaryChecking
import org.kgusarov.krono.locales.nl.NlConstants
import org.kgusarov.krono.locales.nl.parseTimeUnits
import org.kgusarov.krono.utils.reverseDecimalTimeUnits

@SuppressFBWarnings("EI_EXPOSE_REP")
class NlTimeUnitCasualRelativeFormatParser : AbstractParserWithWordBoundaryChecking() {
    override fun innerPattern(context: ParsingContext) = PATTERN

    override fun innerExtract(
        context: ParsingContext,
        match: RegExpMatchArray,
    ): ParserResult {
        val prefix = match[PREFIX_WORD_GROUP]!!.lowercase()
        var timeUnits = parseTimeUnits(match[TIME_UNIT_WORD_GROUP]!!)
        when (prefix) {
            "vorig", "afgelopen", "-" -> timeUnits = reverseDecimalTimeUnits(timeUnits)
        }

        val components = ParsingComponents.createRelativeFromDecimalReference(context.reference, timeUnits)
        return ParserResultFactory(components)
    }

    companion object {
        @JvmStatic
        private val PATTERN =
            Regex(
                "(dit|deze|vorig|afgelopen|(?:aan)?komend|over|\\+|-)e?\\s*(${NlConstants.TIME_UNITS_PATTERN})(?=\\W|$)",
                RegexOption.IGNORE_CASE,
            )

        private const val PREFIX_WORD_GROUP = 1
        private const val TIME_UNIT_WORD_GROUP = 2
    }
}
