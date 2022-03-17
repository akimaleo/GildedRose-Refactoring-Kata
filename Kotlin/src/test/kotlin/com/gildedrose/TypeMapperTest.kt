package com.gildedrose

import com.gildedrose.base.randomString
import com.gildedrose.kt.ItemBehaviour
import com.gildedrose.kt.getTypeOfName
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource


class TypeMapperTest {
    @ParameterizedTest
    @MethodSource("names")
    fun `should map item type successfully`(input: String, expected: ItemBehaviour) {
        assertEquals(getTypeOfName(input), expected)
    }

    companion object {
        @JvmStatic
        fun names() = listOf(
            Arguments.of("Aged Brie", ItemBehaviour.AGED_BRIE),
            Arguments.of(
                "Backstage passes to a TAFKAL80ETC concert",
                ItemBehaviour.BACKSTAGE_PASSES
            ),
            Arguments.of("Sulfuras, Hand of Ragnaros", ItemBehaviour.SULFURAS),
            Arguments.of(randomString(), ItemBehaviour.COMMON)
        )
    }
}
