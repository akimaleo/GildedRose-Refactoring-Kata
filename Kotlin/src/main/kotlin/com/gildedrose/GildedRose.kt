package com.gildedrose

import java.util.Collections.max

class GildedRose(var items: Array<Item>) {

    fun updateQuality() {
        items.forEach {
            if (it.name != "Aged Brie" && it.name != "Backstage passes to a TAFKAL80ETC concert") {
                if (it.quality > 0) {
                    if (it.name != "Sulfuras, Hand of Ragnaros") {
                        it.quality = it.quality - 1
                    }
                }
            } else {
                if (it.quality < 50) {
                    it.quality = it.quality + 1

                    if (it.name == "Backstage passes to a TAFKAL80ETC concert") {
                        if (it.sellIn < 11) {
                            if (it.quality < 50) {
                                it.quality = it.quality + 1
                            }
                        }

                        if (it.sellIn < 6) {
                            if (it.quality < 50) {
                                it.quality = it.quality + 1
                            }
                        }
                    }
                }
            }

            if (it.name != "Sulfuras, Hand of Ragnaros") {
                it.sellIn = it.sellIn - 1
            }

            if (it.sellIn < 0) {
                if (it.name != "Aged Brie") {
                    if (it.name != "Backstage passes to a TAFKAL80ETC concert") {
                        if (it.quality > 0) {
                            if (it.name != "Sulfuras, Hand of Ragnaros") {
                                it.quality = it.quality - 1
                            }
                        }
                    } else {
                        it.quality = it.quality - it.quality
                    }
                } else {
                    if (it.quality < 50) {
                        it.quality = it.quality + 1
                    }
                }
            }
        }
    }

}

