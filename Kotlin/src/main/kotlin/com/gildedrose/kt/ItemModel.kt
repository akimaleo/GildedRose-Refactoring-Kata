package com.gildedrose.kt

import com.gildedrose.*
import com.gildedrose.kt.EndOfLifeModificator.Companion.apply
import kotlin.run

sealed class EndOfLifeModificator {
    class QualityMultiplier(val num: Int) : EndOfLifeModificator() {
        operator fun invoke(item: Item, originalNum: Int) {
            item.quality = validateQuality(item.quality + originalNum * num)
        }
    }

    class Jump(val num: Int) : EndOfLifeModificator() {
        operator fun invoke(item: Item) {
            item.quality = num
        }
    }

    object Empty : EndOfLifeModificator() {
        operator fun invoke(item: Item, originalNum: Int) {
            item.quality = validateQuality(item.quality + originalNum)
        }
    }

    companion object {
        fun EndOfLifeModificator.apply(item: Item, originalNum: Int) {
            when (this) {
                is QualityMultiplier -> {
                    invoke(item, originalNum)
                }
                is Jump -> {
                    invoke(item)
                }
                is Empty -> invoke(item, originalNum)
            }
        }
    }
}

sealed class QualityStrategy(
    val endOfLifeModificator: EndOfLifeModificator,
    var min: Int = QUALITY_MIN,
    var max: Int = QUALITY_MAX,
) {

    operator fun invoke(item: Item) {
        when {
            item.sellIn > 0 -> onTick(item)
            else -> onAfterlife(item)
        }
        item.sellIn--
    }

    abstract fun onTick(item: Item)

    abstract fun onAfterlife(item: Item)

    class Static(
        val num: Int? = null,
        endOfLifeModificator: EndOfLifeModificator,
        max: Int,
        min: Int
    ) : QualityStrategy(endOfLifeModificator, max, min) {
        override fun onTick(item: Item) {
            num?.let { item.quality = validateQuality(item.quality + it) }
        }

        override fun onAfterlife(item: Item) {
            num?.let { endOfLifeModificator.apply(item, num) }
        }
    }

    class BackstagePasses(
        val values: Map<IntRange, Int>,
        endOfLifeModificator: EndOfLifeModificator,
        max: Int,
        min: Int
    ) : QualityStrategy(endOfLifeModificator, max, min) {
        override fun onTick(item: Item) {
            findNum(item)?.let {
                item.quality += it
            } ?: print("Not in the range")
        }

        override fun onAfterlife(item: Item) {
            findNum(item)?.let { num ->
                endOfLifeModificator.apply(item, num)
            } ?: print("Not in the range")
        }

        private fun findNum(item: Item) = values
            .filter { it.key.contains(item.sellIn) }
            .map { it.value }
            .maxByOrNull { it }
    }
}


enum class ItemBehaviour(val qualityStrategy: QualityStrategy) {
    COMMON(static(-1, eolMultiplier(2))),
    CONJURED(static(-2, eolMultiplier(4))),
    AGED_BRIE(static(1, eolMultiplier(2))),
    SULFURAS(static()),
    BACKSTAGE_PASSES(
        backstagePasses(
            mapOf(
                (10..Int.MAX_VALUE) to 1,
                (5..10) to 2,
                (1..5) to 3
            ),
            eolJump(0)
        )
    );

    operator fun invoke(item: Item) = qualityStrategy(item)

}

class ItemModel(
    override var name: String,
    override var sellIn: Int,
    override var quality: Int
) : Item(name, sellIn, quality) {
    var itemBehaviour: ItemBehaviour = getTypeOfName(name)

    fun tick() {
        itemBehaviour(this)
    }
}