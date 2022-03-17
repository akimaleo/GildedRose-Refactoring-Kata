package com.gildedrose

class Item(var name: String, var sellIn: Int, var quality: Int) {
    override fun toString() = this.name + ", " + this.sellIn + ", " + this.quality
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Item) return false

        if (name != other.name) return false
        if (sellIn != other.sellIn) return false
        if (quality != other.quality) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + sellIn
        result = 31 * result + quality
        return result
    }

}