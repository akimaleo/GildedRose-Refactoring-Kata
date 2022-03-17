package com.gildedrose.kt

import com.gildedrose.kt.EndOfLifeModificator.Companion.invoke

sealed class QualityStrategy(
    val endOfLifeModificator: EndOfLifeModificator,
    var min: Int = QUALITY_MIN,
    var max: Int = QUALITY_MAX,
) {

    operator fun invoke(item: ItemModel) {
        when {
            item.sellIn > 0 -> onTick(item)
            else -> onAfterlife(item)
        }
    }

    abstract fun onTick(item: ItemModel)
    abstract fun onAfterlife(item: ItemModel)


    class Static(
        val num: Int? = null,
        val decreaseSellIn: Boolean = true,
        endOfLifeModificator: EndOfLifeModificator,
        max: Int,
        min: Int
    ) : QualityStrategy(endOfLifeModificator, max, min) {
        override fun onTick(item: ItemModel) {
            with(item) {
                num?.let { quality = validateQuality(quality + it) }
                if (decreaseSellIn) sellIn--
            }
        }

        override fun onAfterlife(item: ItemModel) {
            num?.let { endOfLifeModificator.invoke(item, num) }
            if (decreaseSellIn) item.sellIn--
        }
    }

    class BackstagePasses(
        val values: Map<IntRange, Int>,
        endOfLifeModificator: EndOfLifeModificator,
        max: Int,
        min: Int
    ) : QualityStrategy(endOfLifeModificator, max, min) {
        override fun onTick(item: ItemModel) {
            valueForItem(item)?.let {
                item.quality = validateQuality(item.quality - it)
            } ?: print("Not in the range")
            item.sellIn--
        }

        override fun onAfterlife(item: ItemModel) {
            valueForItem(item)?.let { num ->
                endOfLifeModificator.invoke(item, num)
            } ?: print("Not in the range")
            item.sellIn--
        }

        fun valueForItem(item: ItemModel) = values
            .filter { it.key.contains(item.sellIn) }
            .map { it.value }
            .maxByOrNull { it }
    }
}
