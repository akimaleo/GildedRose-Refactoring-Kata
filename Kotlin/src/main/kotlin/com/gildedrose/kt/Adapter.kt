package com.gildedrose.kt

import com.gildedrose.Item

fun getTypeOfName(name: String) = when (name) {
    in setOf("Aged Brie") -> ItemBehaviour.AGED_BRIE
    in setOf(
        "Backstage passes",
        "Backstage passes to a TAFKAL80ETC concert"
    ) -> ItemBehaviour.BACKSTAGE_PASSES
    in setOf("Sulfuras", "Sulfuras, Hand of Ragnaros") -> ItemBehaviour.SULFURAS
    in setOf("Conjured") -> ItemBehaviour.CONJURED
    else -> ItemBehaviour.COMMON
}


fun Array<Item>.toRefactored(): Array<ItemModel> {
    return Array(size) { item(get(it)) }
}

fun Array<ItemModel>.toLegacy() = Array(size) { index ->
    get(index).let {
        Item(it.name, it.sellIn, it.quality)
    }
}
