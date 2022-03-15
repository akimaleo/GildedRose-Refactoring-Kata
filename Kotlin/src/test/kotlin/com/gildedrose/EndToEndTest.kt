package com.gildedrose

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class EndToEndTest {
    lateinit var glideRoseLegacy: GildedRose
    lateinit var glideRoseRefactored: GildedRose

    @BeforeEach
    fun setUp() {
        glideRoseLegacy = GildedRoseLegacy(testItems.clone())
        glideRoseRefactored = GildedRoseRefactored(testItems.clone())
    }

    @Test
    fun `test`() {
        repeat(100) {
//            glideRoseLegacy.updateQuality()
            glideRoseRefactored.updateQuality()
            println(glideRoseRefactored.items[1])
//            assertTrue(
//                glideRoseLegacy.items.contentEquals(glideRoseRefactored.items),
//                "Attempt: $it\n" +
//                        "\tLegacy:\n${glideRoseLegacy}\n" +
//                        "\tRefactored:\n${glideRoseRefactored}"
//            )
        }
    }
}
