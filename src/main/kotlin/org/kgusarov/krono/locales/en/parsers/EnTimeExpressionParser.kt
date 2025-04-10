package org.kgusarov.krono.locales.en.parsers

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.kgusarov.krono.ParsedComponents
import org.kgusarov.krono.ParsedResult
import org.kgusarov.krono.ParsingContext
import org.kgusarov.krono.RegExpMatchArray
import org.kgusarov.krono.common.parsers.AbstractTimeExpressionParser
import org.kgusarov.krono.common.parsers.RelativeDateTimeParserSupport

@SuppressFBWarnings("EI_EXPOSE_REP")
class EnTimeExpressionParser(strictMode: Boolean) :
    AbstractTimeExpressionParser(strictMode),
    RelativeDateTimeParserSupport {
    override fun primaryPrefix() = PRIMARY_PREFIX_PATTERN

    override fun followingPhase() = FOLLOWING_PHASE_PATTERN

    override fun primarySuffix() = PRIMARY_SUFFIX_PATTERN

    override fun extractPrimaryTimeComponents(
        context: ParsingContext,
        match: RegExpMatchArray,
    ): ParsedComponents? {
        val components =
            extractPrimaryTimeComponents(
                context,
                match,
                "night",
                "afternoon",
                "morning",
            ) { c, m ->
                super<AbstractTimeExpressionParser>.extractPrimaryTimeComponents(c, m)
            } ?: return null

        return components.addTag("parser/ENTimeExpressionParser")
    }

    override fun extractFollowingTimeComponents(
        context: ParsingContext,
        match: RegExpMatchArray,
        result: ParsedResult,
    ): ParsedComponents? {
        val followingComponents = super.extractFollowingTimeComponents(context, match, result)
        return when {
            followingComponents != null -> {
                followingComponents.addTags("parser/ENTimeExpressionParser")
            }

            else -> null
        }
    }

    companion object {
        @JvmStatic
        private val FOLLOWING_PHASE_PATTERN = "\\s*(?:\\-|\\–|\\~|\\〜|to|until|through|till|\\?)\\s*"

        @JvmStatic
        private val PRIMARY_PREFIX_PATTERN = "(?:(?:at|from)\\s*)??"

        @JvmStatic
        private val PRIMARY_SUFFIX_PATTERN =
            "(?:\\s*(?:o\\W*clock|at\\s*night|in\\s*the\\s*(?:morning|afternoon)))?(?!/)(?=\\W|$)"
    }
}
