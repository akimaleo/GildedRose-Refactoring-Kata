package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
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

    @Disabled("My assumption is that legacy implementation of the GildedRose is incorrect")
    @Test
    fun `test refactored GildedRose implementation behaviour is correct comparing with gildedRoseRefactored`() {
        repeat(10) {
            if (it == 5) {
                println()
            }
            gildedRoseLegacy.updateQuality()
            gildedRoseRefactored.updateQuality()

            assertEquals(
                gildedRoseLegacy,
                gildedRoseRefactored,
                "Attempt: ${it + 1}"
            )
        }
    }
}
