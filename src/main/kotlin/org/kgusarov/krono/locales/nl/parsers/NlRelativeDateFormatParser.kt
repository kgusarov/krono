package org.kgusarov.krono.locales.nl.parsers

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.kgusarov.krono.KronoTimeUnits
import org.kgusarov.krono.ParserResult
import org.kgusarov.krono.ParserResultFactory
import org.kgusarov.krono.ParsingComponents
import org.kgusarov.krono.ParsingContext
import org.kgusarov.krono.RegExpMatchArray
import org.kgusarov.krono.common.parsers.AbstractParserWithWordBoundaryChecking
import org.kgusarov.krono.common.parsers.RelativeDateTimeParserSupport
import org.kgusarov.krono.locales.nl.NlConstants
import org.kgusarov.krono.utils.matchAnyPattern

@SuppressFBWarnings("EI_EXPOSE_REP")
class NlRelativeDateFormatParser : AbstractParserWithWordBoundaryChecking(), RelativeDateTimeParserSupport {
    override fun innerPattern(context: ParsingContext) = PATTERN

    override fun innerExtract(
        context: ParsingContext,
        match: RegExpMatchArray,
    ): ParserResult {
        val modifier = match[MODIFIER_WORD_GROUP]!!.lowercase()
        val unitWord = match[RELATIVE_WORD_GROUP]!!.lowercase()
        val unit = NlConstants.TIME_UNIT_DICTIONARY[unitWord]!!

        if (modifier == "volgend" || modifier == "komend" || modifier == "aankomend") {
            val timeUnits: KronoTimeUnits = mutableMapOf(unit to 1)
            val components = ParsingComponents.createRelativeFromUnits(context.reference, timeUnits)
            return ParserResultFactory(components)
        }

        if (modifier == "afgelopen" || modifier == "vorig") {
            val timeUnits: KronoTimeUnits = mutableMapOf(unit to -1)
            val components = ParsingComponents.createRelativeFromUnits(context.reference, timeUnits)
            return ParserResultFactory(components)
        }

        val components =
            adjust(
                context,
                WEEK_PATTERN,
                MONTH_PATTERN,
                YEAR_PATTERN,
                unitWord,
            )

        return ParserResultFactory(components)
    }

    companion object {
        @JvmStatic
        private val PATTERN =
            Regex(
                "(dit|deze|(?:aan)?komend|volgend|afgelopen|vorig)e?\\s*" +
                    "(${matchAnyPattern(NlConstants.TIME_UNIT_DICTIONARY)})(?=\\s*)" +
                    "(?=\\W|$)",
            )

        @JvmStatic
        private val WEEK_PATTERN = Regex("week", RegexOption.IGNORE_CASE)

        @JvmStatic
        private val MONTH_PATTERN = Regex("maand", RegexOption.IGNORE_CASE)

        @JvmStatic
        private val YEAR_PATTERN = Regex("jaar", RegexOption.IGNORE_CASE)

        private const val MODIFIER_WORD_GROUP = 1
        private const val RELATIVE_WORD_GROUP = 2
    }
}
