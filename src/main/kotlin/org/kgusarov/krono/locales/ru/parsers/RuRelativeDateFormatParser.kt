package org.kgusarov.krono.locales.ru.parsers

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.kgusarov.krono.KronoTimeUnits
import org.kgusarov.krono.ParserResult
import org.kgusarov.krono.ParserResultFactory
import org.kgusarov.krono.ParsingComponents
import org.kgusarov.krono.ParsingContext
import org.kgusarov.krono.RegExpMatchArray
import org.kgusarov.krono.common.parsers.RelativeDateTimeParserSupport
import org.kgusarov.krono.locales.ru.RuConstants
import org.kgusarov.krono.utils.matchAnyPattern

@SuppressFBWarnings("EI_EXPOSE_REP")
class RuRelativeDateFormatParser : AbstractRuParserWithLeftRightBoundaryChecking(), RelativeDateTimeParserSupport {
    override fun innerPatternString(context: ParsingContext) = PATTERN

    override fun innerExtract(
        context: ParsingContext,
        match: RegExpMatchArray,
    ): ParserResult {
        val modifier = match[MODIFIER_WORD_GROUP]!!.lowercase()
        val unitWord = match[RELATIVE_WORD_GROUP]!!.lowercase()
        val timeunit = RuConstants.TIME_UNIT_DICTIONARY[unitWord]!!

        when (modifier) {
            "на следующей", "в следующем" -> {
                val timeUnits: KronoTimeUnits = mutableMapOf(timeunit to 1)
                val components = ParsingComponents.createRelativeFromUnits(context.reference, timeUnits)
                return ParserResultFactory(components)
            }

            "в прошлом", "на прошлой" -> {
                val timeUnits: KronoTimeUnits = mutableMapOf(timeunit to -1)
                val components = ParsingComponents.createRelativeFromUnits(context.reference, timeUnits)
                return ParserResultFactory(components)
            }
        }

        return ParserResultFactory(adjust(context, timeunit))
    }

    companion object {
        @JvmStatic
        private val PATTERN =
            "(в прошлом|на прошлой|на следующей|в следующем|на этой|в этом)\\s*" +
                "(${matchAnyPattern(RuConstants.TIME_UNIT_DICTIONARY)})"

        private const val MODIFIER_WORD_GROUP = 1
        private const val RELATIVE_WORD_GROUP = 2
    }
}
