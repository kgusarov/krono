package org.kgusarov.krono.locales.nl.parsers

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.kgusarov.krono.ParserResult
import org.kgusarov.krono.ParserResultFactory
import org.kgusarov.krono.ParsingContext
import org.kgusarov.krono.RegExpMatchArray
import org.kgusarov.krono.common.calculation.createParsingComponentsAtWeekday
import org.kgusarov.krono.common.parsers.AbstractWeekdayParser
import org.kgusarov.krono.locales.nl.NlConstants
import org.kgusarov.krono.utils.matchAnyPattern

@SuppressFBWarnings("EI_EXPOSE_REP")
class NlWeekdayParser : AbstractWeekdayParser() {
    override fun innerPattern(context: ParsingContext) = PATTERN

    override fun innerExtract(
        context: ParsingContext,
        match: RegExpMatchArray,
    ): ParserResult {
        val dayOfWeek = match[WEEKDAY_GROUP]!!.lowercase()
        val weekday = NlConstants.WEEKDAY_DICTIONARY[dayOfWeek]!!
        val modifierWord = getModifierWord(match, PREFIX_GROUP, POSTFIX_GROUP)
        val modifier: String? =
            when (modifierWord) {
                "vorige" -> "last"
                "volgende" -> "next"
                "deze" -> "this"
                else -> null
            }

        return ParserResultFactory(createParsingComponentsAtWeekday(context.reference, weekday, modifier))
    }

    @Suppress("RegExpSingleCharAlternation", "RegExpRedundantEscape")
    companion object {
        @JvmStatic
        private val PATTERN =
            Regex(
                "(?:(?:\\,|\\(|\\（)\\s*)?" +
                    "(?:op\\s*?)?" +
                    "(?:(deze|vorige|volgende)\\s*(?:week\\s*)?)?" +
                    "(${matchAnyPattern(NlConstants.WEEKDAY_DICTIONARY)})" +
                    "(?=\\W|$)",
                RegexOption.IGNORE_CASE,
            )

        private const val PREFIX_GROUP = 1
        private const val WEEKDAY_GROUP = 2
        private const val POSTFIX_GROUP = 3
    }
}
