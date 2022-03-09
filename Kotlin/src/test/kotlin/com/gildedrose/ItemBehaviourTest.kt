package com.gildedrose

import com.gildedrose.kt.ItemBehaviour
import com.gildedrose.kt.item
import org.junit.jupiter.api.Test

class ItemBehaviourTest {
    @Test
    fun `should handle ItemBehaviour#COMMON properly`(){
        var common = ItemBehaviour.COMMON
        val item = item("Name",2,10)
    }
}