package com.gildedrose

import com.gildedrose.kt.ItemBehaviour
import com.gildedrose.kt.QualityStrategy
import com.gildedrose.kt.item
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ItemBehaviourTest {

    @Test
    fun `should handle ItemBehaviour#COMMON behaviour successfully`() {
        val item = item("foo", 3, 5) {
            itemBehaviour = ItemBehaviour.COMMON
        }
        item.apply {
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
    fun `should handle ItemBehaviour#CONJURED behaviour successfully`() {
        val item = item("foo", 3, 5) {
            itemBehaviour = ItemBehaviour.CONJURED
        }
        item.apply {
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


    @Test
    fun `should handle ItemBehaviour#AGED_BRIE behaviour successfully`() {
        val item = item("foo", 3, 5) {
            itemBehaviour = ItemBehaviour.AGED_BRIE
        }

        item.apply {
            //Run till sell in become 0
            (1..3).map {
                tick()
                assertEquals(3 - it, sellIn, toString())
                assertEquals(5 + it, quality, toString())
            }

            // Test EOL
            (1..2).map {
                tick()
                assertEquals(0 - it, sellIn, toString())
                assertEquals((8 + (it * 2)), quality, toString())
            }
        }
    }


    @Test
    fun `should handle ItemBehaviour#SULFURAS behaviour successfully with positive sellIn`() {
        val item = item("foo", 3, 5) {
            itemBehaviour = ItemBehaviour.SULFURAS
        }

        repeat(10) {
            item.tick()
        }
        assertEquals(5, item.quality)
        assertEquals(3, item.sellIn)
    }

    @Test
    fun `should handle ItemBehaviour#SULFURAS behaviour successfully with negative sellIn`() {
        val item = item("foo", -1, 5) {
            itemBehaviour = ItemBehaviour.SULFURAS
        }

        repeat(10) {
            item.tick()
        }
        assertEquals(5, item.quality)
    }


    @Test
    fun `should handle ItemBehaviour#BACKSTAGE_PASSES behaviour successfully`() {
        val item = item("foo", 12, 50) {
            itemBehaviour = ItemBehaviour.BACKSTAGE_PASSES
        }
        val strategy =
            (ItemBehaviour.BACKSTAGE_PASSES.qualityStrategy as QualityStrategy.BackstagePasses)
        repeat(13) {
            val qualityChangeValue = strategy.valueForItem(item) ?: 0
            val expectedEquality = item.quality + qualityChangeValue
            item.tick()
            assertEquals(expectedEquality, item.quality)
            assertEquals(12 - it - 1, item.sellIn)
        }
    }
}
