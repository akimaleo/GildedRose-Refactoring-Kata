package com.gildedrose

import com.gildedrose.kt.item
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GildedRoseTest {

    @Test
    fun `test item mapper`() {
        val commonItem1 = Item("foo", 10, 30)
        with(item(commonItem1)) {
            assertEquals(commonItem1.name, name)
            assertEquals(commonItem1.sellIn, sellIn)
            assertEquals(commonItem1.quality, quality)
        }
    }

    @Test
    fun `should handle common successfully`() {
        val commonItem1 = Item("foo", 10, 30)


        val agedBrieItem = Item("Aged Brie", 10, 30)
        val backstageItem = Item("Backstage passes to a TAFKAL80ETC concert", 10, 30)
        val sulfurasItem = Item("Sulfuras, Hand of Ragnaros", 10, 30)

//        assertEquals(, name)

    }
}


