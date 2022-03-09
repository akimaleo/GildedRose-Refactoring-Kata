package com.gildedrose.kt

import com.gildedrose.Item

// Item
fun item(item: Item) = ItemModel(item.name, item.sellIn, item.quality)
fun item(name: String, sellIn: Int, quality: Int) = ItemModel(name, sellIn,quality)

// Quality strategy
fun static(
    num: Int? = null,
    endOfLifeModificator: EndOfLifeModificator = eolEmpty(),
    min: Int = QUALITY_MIN,
    max: Int = QUALITY_MAX
) = QualityStrategy.Static(num, endOfLifeModificator, min, max)

fun backstagePasses(ranges: Map<IntRange, Int>, endOfLifeModificator: EndOfLifeModificator) =
    QualityStrategy.BackstagePasses(ranges, endOfLifeModificator, QUALITY_MAX, QUALITY_MIN)

// EOL modificators
fun eolMultiplier(num: Int) = EndOfLifeModificator.QualityMultiplier(num)
fun eolJump(num: Int) = EndOfLifeModificator.Jump(num)
fun eolEmpty() = EndOfLifeModificator.Empty

fun validateQuality(num: Int, min: Int = QUALITY_MIN, max: Int = QUALITY_MAX) =
    num.coerceIn(min, max)
