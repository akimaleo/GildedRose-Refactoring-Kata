package com.gildedrose.kt

import com.gildedrose.Item

// Item
fun item(item: Item) = ItemModel(item.name, item.sellIn, item.quality)
fun item(name: String, sellIn: Int, quality: Int) = ItemModel(name, sellIn, quality)
fun item(name: String, sellIn: Int, quality: Int, modify: ItemModel.() -> (Unit)) =
    ItemModel(name, sellIn, quality).apply { modify(this) }

// Quality strategy
fun static(
    num: Int? = null,
    endOfLifeModificator: EndOfLifeModificator = eolEmpty(),
    decreaseSellIn: Boolean = true,
    min: Int = QUALITY_MIN,
    max: Int = QUALITY_MAX
) = QualityStrategy.Static(num, decreaseSellIn, endOfLifeModificator, min, max)

fun backstagePasses(
    ranges: Map<IntRange, Int>,
    endOfLifeModificator: EndOfLifeModificator,
    min: Int = QUALITY_MIN,
    max: Int = QUALITY_MAX
) = QualityStrategy.BackstagePasses(ranges, endOfLifeModificator, min, max)

// EOL modificators
fun eolMultiplier(num: Int) = EndOfLifeModificator.QualityMultiplier(num)
fun eolJump(num: Int) = EndOfLifeModificator.Jump(num)
fun eolEmpty() = EndOfLifeModificator.Empty

fun validateQuality(num: Int, min: Int = QUALITY_MIN, max: Int = QUALITY_MAX) =
    num.coerceIn(min, max)
