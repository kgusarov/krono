package org.kgusarov.krono.locales.ru.refiners

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.kgusarov.krono.common.refiners.AbstractMergeDateTimeRefiner

@Suppress("RegExpSingleCharAlternation")
@SuppressFBWarnings("EI_EXPOSE_REP")
class RuMergeDateTimeRefiner : AbstractMergeDateTimeRefiner() {
    override fun patternBetween() = PATTERN

    companion object {
        @JvmStatic
        private val PATTERN = Regex("^\\s*(T|в|,|-)?\\s*$")
    }
}
