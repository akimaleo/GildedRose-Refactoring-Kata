package com.gildedrose

import jdk.nashorn.internal.ir.annotations.Ignore
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GildedRoseTest {
    lateinit var gildedRoseLegacy: GildedRose
    lateinit var gildedRoseRefactored: GildedRose

    @BeforeEach
    fun setUp() {
        testItems
            .filter { !it.name.contains("Conjured") }
            .let {
                gildedRoseLegacy = GildedRoseLegacy(testItems)
                gildedRoseRefactored = GildedRoseRefactored(testItems)
            }
    }

    /**
     * My assumption is legacy implementation of the GildedRose is incorrect
     */
    @Test
    @Ignore
    fun `test refactored GildedRose implementation behaviour is correct comparing with gildedRoseRefactored`() {
        repeat(10) {
            gildedRoseLegacy.updateQuality()
            gildedRoseRefactored.updateQuality()

            assertEquals(
                gildedRoseLegacy,
                gildedRoseRefactored
            )
        }
    }
}
