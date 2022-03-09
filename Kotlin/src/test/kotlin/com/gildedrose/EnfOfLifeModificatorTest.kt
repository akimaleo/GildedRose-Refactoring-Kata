package com.gildedrose

import com.gildedrose.kt.eolJump
import com.gildedrose.kt.eolMultiplier
import com.gildedrose.kt.static
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class EnfOfLifeModificatorTest {

    @Test
    fun `should handle EOL multiplier modificator properly`() {
        val item = Item("foo", 0, 10)
        eolMultiplier(2).invoke(item, -1)
        Assertions.assertEquals(8, item.quality)
    }

    @Test
    fun `should jump to specific value when EOL eolJump modificator was applied`() {
        val item = Item("foo", 0, 0)
        eolJump(10).invoke(item)
        Assertions.assertEquals(10, item.quality)
    }


    @Test
    fun `should handle value inn common way when EOL Empty modificator was applied`() {
        val commonBehaviour = static(-1, eolJump(10))
        val item = Item("foo", 0, 10)
        repeat(2) {
            commonBehaviour(item)
        }
        Assertions.assertEquals(10, item.quality)
    }


}