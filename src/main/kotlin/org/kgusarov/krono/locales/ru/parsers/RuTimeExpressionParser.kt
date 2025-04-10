package org.kgusarov.krono.locales.ru.parsers

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.kgusarov.krono.ParsedComponents
import org.kgusarov.krono.ParsingContext
import org.kgusarov.krono.RegExpMatchArray
import org.kgusarov.krono.common.parsers.AbstractTimeExpressionParser
import org.kgusarov.krono.common.parsers.RelativeDateTimeParserSupport
import org.kgusarov.krono.locales.ru.RuConstants

@SuppressFBWarnings("EI_EXPOSE_REP")
class RuTimeExpressionParser(strictMode: Boolean) :
    AbstractTimeExpressionParser(strictMode),
    RelativeDateTimeParserSupport {
    override fun primaryPatternLeftBoundary() = PRIMARY_PATTERN_LEFT_BOUNDARY

    override fun primaryPrefix() = PRIMARY_PREFIX

    override fun primarySuffix() = PRIMARY_SUFFIX

    override fun followingPhase() = FOLLOWING_PHASE

    override fun extractPrimaryTimeComponents(
        context: ParsingContext,
        match: RegExpMatchArray,
    ): ParsedComponents? {
        return extractPrimaryTimeComponents(
            context,
            match,
            "вечера",
            "после полудня",
            "утра",
        ) { c, m ->
            super<AbstractTimeExpressionParser>.extractPrimaryTimeComponents(c, m)
        }
    }

    companion object {
        @JvmStatic
        private val PRIMARY_PATTERN_LEFT_BOUNDARY = "(^|\\s|T|(?:[^\\p{L}\\p{N}_]))"

        @JvmStatic
        private val FOLLOWING_PHASE = "\\s*(?:\\-|\\–|\\~|\\〜|до|и|по|\\?)\\s*"

        @JvmStatic
        private val PRIMARY_PREFIX = "(?:(?:в|с)\\s*)??"

        @JvmStatic
        private val PRIMARY_SUFFIX = "(?:\\s*(?:утра|вечера|после полудня))?(?!\\/)${RuConstants.RIGHT_BOUNDARY}"
    }
}
