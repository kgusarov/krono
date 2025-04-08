package org.kgusarov.krono.locales.ru.refiners

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings
import org.kgusarov.krono.common.refiners.AbstractMergeDateRangeRefiner

@SuppressFBWarnings("EI_EXPOSE_REP")
class RuMergeDateRangeRefiner : AbstractMergeDateRangeRefiner() {
    override fun patternBetween() = PATTERN

    companion object {
        @JvmStatic
        private val PATTERN = Regex("^\\s*(и до|и по|до|по|-)\\s*$", RegexOption.IGNORE_CASE)
    }
}
