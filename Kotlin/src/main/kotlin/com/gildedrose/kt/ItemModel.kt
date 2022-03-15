package com.gildedrose.kt

enum class ItemBehaviour(val qualityStrategy: QualityStrategy) {
    COMMON(static(-1, eolMultiplier(2))),
    CONJURED(static(-2, eolMultiplier(4))),
    AGED_BRIE(static(1, eolMultiplier(2))),
    SULFURAS(static(decreaseSellIn = false)),
    BACKSTAGE_PASSES(
        backstagePasses(
            mapOf(
                (10..Int.MAX_VALUE) to -1,
                (5..10) to -2,
                (1..5) to -3
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
