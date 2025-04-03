package org.kgusarov.krono.common.parsers

import org.kgusarov.krono.RegExpMatchArray

abstract class AbstractWeekdayParser : AbstractParserWithWordBoundaryChecking() {
    protected fun getModifierWord(
        match: RegExpMatchArray,
        prefixGroup: Int,
        postfixGroup: Int,
    ): String {
        val prefix = match[prefixGroup]
        val postfix = match[postfixGroup]
        return (
            if (!prefix.isNullOrEmpty()) {
                prefix
            } else if (!postfix.isNullOrEmpty()) {
                postfix
            } else {
                ""
            }
        ).lowercase()
    }
}
