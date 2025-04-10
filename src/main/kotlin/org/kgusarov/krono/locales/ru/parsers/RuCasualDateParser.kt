package org.kgusarov.krono.locales.ru.parsers

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.kgusarov.krono.ParsedComponents
import org.kgusarov.krono.ParserResult
import org.kgusarov.krono.ParserResultFactory
import org.kgusarov.krono.ParsingContext
import org.kgusarov.krono.RegExpMatchArray
import org.kgusarov.krono.common.theDayAfter
import org.kgusarov.krono.common.theDayBefore
import org.kgusarov.krono.common.today
import org.kgusarov.krono.common.tomorrow
import org.kgusarov.krono.common.yesterday

@SuppressFBWarnings("EI_EXPOSE_REP")
class RuCasualDateParser : AbstractRuParserWithLeftBoundaryChecking() {
    override fun innerPatternString(context: ParsingContext) = PATTERN

    override fun innerExtract(
        context: ParsingContext,
        match: RegExpMatchArray,
    ): ParserResult {
        val lowerText = match[1]!!.lowercase()

        val components: ParsedComponents =
            when (lowerText) {
                "сегодня" -> today(context.reference)
                "вчера" -> yesterday(context.reference)
                "завтра" -> tomorrow(context.reference)
                "послезавтра" -> theDayAfter(context.reference, 2)
                "послепослезавтра" -> theDayAfter(context.reference, 3)
                "позавчера" -> theDayBefore(context.reference, 2)
                "позапозавчера" -> theDayBefore(context.reference, 3)
                else -> context.createParsingComponents()
            }

        return ParserResultFactory(components)
    }

    companion object {
        @JvmStatic
        private val PATTERN = "(?:с|со)?\\s*(сегодня|вчера|завтра|послезавтра|послепослезавтра|позапозавчера|позавчера)"
    }
}
