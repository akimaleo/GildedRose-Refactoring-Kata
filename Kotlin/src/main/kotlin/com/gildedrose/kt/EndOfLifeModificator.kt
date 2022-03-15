package com.gildedrose.kt

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
        fun EndOfLifeModificator.invoke(item: ItemModel, originalNum: Int) {
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