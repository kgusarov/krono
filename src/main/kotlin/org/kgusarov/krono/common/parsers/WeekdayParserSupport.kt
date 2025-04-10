package org.kgusarov.krono.common.parsers

import org.kgusarov.krono.RegExpMatchArray

interface WeekdayParserSupport {
    fun getModifierWord(
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
