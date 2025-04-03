package org.kgusarov.krono.locales.nl.parsers

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.kgusarov.krono.ParserResult
import org.kgusarov.krono.ParserResultFactory
import org.kgusarov.krono.ParsingContext
import org.kgusarov.krono.RegExpMatchArray
import org.kgusarov.krono.common.now
import org.kgusarov.krono.common.parsers.AbstractParserWithWordBoundaryChecking
import org.kgusarov.krono.common.today
import org.kgusarov.krono.common.tomorrow
import org.kgusarov.krono.common.yesterday

@SuppressFBWarnings("EI_EXPOSE_REP")
class NlCasualDateParser : AbstractParserWithWordBoundaryChecking() {
    override fun innerPattern(context: ParsingContext) = PATTERN

    override fun innerExtract(
        context: ParsingContext,
        match: RegExpMatchArray,
    ): ParserResult {
        val lowerText = match[0]!!.lowercase()
        val components =
            when (lowerText) {
                "nu" -> now(context.reference)
                "vandaag" -> today(context.reference)
                "morgen", "morgend" -> tomorrow(context.reference)
                "gisteren" -> yesterday(context.reference)
                else -> context.createParsingComponents()
            }

        return ParserResultFactory(components)
    }

    companion object {
        @JvmStatic
        private val PATTERN =
            Regex(
                "(nu|vandaag|morgen|morgend|gisteren)(?=\\W|$)",
                RegexOption.IGNORE_CASE,
            )
    }
}
