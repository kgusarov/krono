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
class NlTimeUnitAgoFormatParser(private val strictMode: Boolean) : AbstractParserWithWordBoundaryChecking() {
    override fun innerPattern(context: ParsingContext) =
        if (strictMode) {
            STRICT_PATTERN
        } else {
            PATTERN
        }

    override fun innerExtract(
        context: ParsingContext,
        match: RegExpMatchArray,
    ): ParserResult {
        val timeUnits = parseTimeUnits(match[1]!!)
        val outputTimeUnits = reverseDecimalTimeUnits(timeUnits)
        val components = ParsingComponents.createRelativeFromDecimalReference(context.reference, outputTimeUnits)
        return ParserResultFactory(components)
    }

    @Suppress("RegExpUnnecessaryNonCapturingGroup")
    companion object {
        @JvmStatic
        private val PATTERN =
            Regex(
                "(${NlConstants.TIME_UNITS_PATTERN})(?:geleden|voor|eerder)(?=(?:\\W|$))",
                RegexOption.IGNORE_CASE,
            )

        @JvmStatic
        private val STRICT_PATTERN =
            Regex(
                "(${NlConstants.TIME_UNITS_PATTERN})geleden(?=(?:\\W|$))",
                RegexOption.IGNORE_CASE,
            )
    }
}
