package org.kgusarov.krono.locales.en.parsers

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.kgusarov.krono.KronoTimeUnits
import org.kgusarov.krono.ParserResult
import org.kgusarov.krono.ParserResultFactory
import org.kgusarov.krono.ParsingComponents
import org.kgusarov.krono.ParsingContext
import org.kgusarov.krono.RegExpMatchArray
import org.kgusarov.krono.common.parsers.AbstractParserWithWordBoundaryChecking
import org.kgusarov.krono.common.parsers.RelativeDateTimeParserSupport
import org.kgusarov.krono.locales.en.EnConstants
import org.kgusarov.krono.utils.matchAnyPattern

@SuppressFBWarnings("EI_EXPOSE_REP")
class EnRelativeDateFormatParser : AbstractParserWithWordBoundaryChecking(), RelativeDateTimeParserSupport {
    override fun innerPattern(context: ParsingContext) = PATTERN

    override fun innerExtract(
        context: ParsingContext,
        match: RegExpMatchArray,
    ): ParserResult {
        val modifier = match[MODIFIER_WORD_GROUP]!!.lowercase()
        val unitWord = match[RELATIVE_WORD_GROUP]!!.lowercase()
        val timeunit = EnConstants.TIME_UNIT_DICTIONARY[unitWord]!!

        if (modifier == "next" || modifier.startsWith("after")) {
            val timeUnits: KronoTimeUnits = mutableMapOf(timeunit to 1)
            val components = ParsingComponents.createRelativeFromUnits(context.reference, timeUnits)
            return ParserResultFactory(components)
        }

        if (modifier == "last" || modifier == "past") {
            val timeUnits: KronoTimeUnits = mutableMapOf(timeunit to -1)
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
                "(this|last|past|next|after\\s*this)\\s*(${matchAnyPattern(EnConstants.TIME_UNIT_DICTIONARY)})(?=\\s*)" +
                    "(?=\\W|\$)",
                RegexOption.IGNORE_CASE,
            )

        @JvmStatic
        private val WEEK_PATTERN = Regex("week", RegexOption.IGNORE_CASE)

        @JvmStatic
        private val MONTH_PATTERN = Regex("month", RegexOption.IGNORE_CASE)

        @JvmStatic
        private val YEAR_PATTERN = Regex("year", RegexOption.IGNORE_CASE)

        private const val MODIFIER_WORD_GROUP = 1
        private const val RELATIVE_WORD_GROUP = 2
    }
}
