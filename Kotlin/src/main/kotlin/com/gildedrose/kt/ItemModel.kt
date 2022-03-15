package com.gildedrose.kt

import com.gildedrose.*
import com.gildedrose.kt.EndOfLifeModificator.Companion.apply
import kotlin.run

sealed class EndOfLifeModificator {
    class QualityMultiplier(val num: Int) : EndOfLifeModificator() {
        operator fun invoke(item: ItemModel, originalNum: Int) {
            item.quality = validateQuality(item.quality + originalNum * num)
        }
    }

    class Jump(val num: Int) : EndOfLifeModificator() {
        operator fun invoke(item: ItemModel) {
            item.quality = num
        }
    }

    object Empty : EndOfLifeModificator() {
        operator fun invoke(item: ItemModel, originalNum: Int) {
            item.quality = validateQuality(item.quality + originalNum)
        }
    }

    companion object {
        fun EndOfLifeModificator.apply(item: ItemModel, originalNum: Int) {
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
            num?.let { endOfLifeModificator.apply(item, num) }
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
            findNum(item)?.let {
                item.quality = validateQuality(item.quality + it)
            } ?: print("Not in the range")
            item.sellIn--
        }

        override fun onAfterlife(item: ItemModel) {
            findNum(item)?.let { num ->
                endOfLifeModificator.apply(item, num)
            } ?: print("Not in the range")
            item.sellIn--
        }

        private fun findNum(item: ItemModel) = values
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

    operator fun invoke(item: ItemModel) = qualityStrategy(item)

}

class ItemModel(
    var name: String,
    var sellIn: Int,
    var quality: Int
) {
    var itemBehaviour: ItemBehaviour = getTypeOfName(name)

    fun tick() {
        itemBehaviour(this)
    }

    override fun toString() = "[" + this.name + ", " + this.sellIn + ", " + this.quality + "]"
}
