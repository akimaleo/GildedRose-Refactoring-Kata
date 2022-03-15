package com.gildedrose

import com.gildedrose.kt.ItemBehaviour
import com.gildedrose.kt.item
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GildedRoseTest {

//    @Test
//    fun `test item mapper`() {
//        val commonItem1 = Item("foo", 10, 30)
//        with(item(commonItem1)) {
//            assertEquals(commonItem1.name, name)
//            assertEquals(commonItem1.sellIn, sellIn)
//            assertEquals(commonItem1.quality, quality)
//        }
//    }

    @Test
    fun `should handle COMMON behaviour successfully`() {
        val commonItem = item("foo", 3, 5) {
            itemBehaviour = ItemBehaviour.COMMON
        }
        commonItem.apply {
            //Run till sell in become 0
            (1..3).map {
                tick()
                assertEquals(3 - it, sellIn, toString())
                assertEquals(5 - it, quality, toString())
            }

            // Test EOL
            (1..2).map {
                tick()
                assertEquals(0 - it, sellIn, toString())
                assertEquals((2 - (it + 1)).coerceAtLeast(0), quality, toString())
            }
        }
    }

    @Test
    fun `should handle CONJURED behaviour successfully`() {
        val commonItem = item("foo", 3, 5) {
            itemBehaviour = ItemBehaviour.CONJURED
        }
        commonItem.apply {
            //Run till sell in become 0
            (1..3).map {
                tick()
                assertEquals(3 - it, sellIn, toString())
                assertEquals((5 - (it * 2)).coerceAtLeast(0), quality, toString())
            }

            // Test EOL
            (1..2).map {
                tick()
                assertEquals(0 - it, sellIn, toString())
                assertEquals((2 - 2 * it).coerceAtLeast(0), quality, toString())
            }
        }
    }
}


