package com.gildedrose

open class Item(open var name: String, open var sellIn: Int, open var quality: Int) {
    override fun toString() = this.name + ", " + this.sellIn + ", " + this.quality
}