package com.gildedrose

import com.gildedrose.kt.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.lang.Integer.max
import java.lang.Integer.min

internal class QualityStrategyBehaviourTest {

    @Test
    fun `should handle static quality diminution strategy successfully`() {
        val staticQualityStrategy = static(-1, eolMultiplier(1))
        val item = item("foo", 3, 10)
        with(item) {
            (1..4).forEach {
                staticQualityStrategy(this)
                assertEquals(3 - it, sellIn, "$it")
                assertEquals(10 - it, quality, "$it")
            }
        }
    }

    @Test
    fun `should handle static quality amplification strategy successfully`() {
        val initQuality = 10
        val staticQualityStrategy = static(1, eolMultiplier(1))
        val item = item("foo", 3, initQuality)
        with(item) {
            (1..4).forEach {
                staticQualityStrategy(this)
                assertEquals(3 - it, sellIn, "$it")
                assertEquals(initQuality + it, quality, "$it")
            }
        }
    }

    @Test
    fun `should handle maximum quality bound successfully`() {
        val initQuality = 48
        val staticQualityStrategy = static(1, eolMultiplier(1))
        val item = item("foo", 3, initQuality)
        with(item) {
            (1..4).forEach {
                staticQualityStrategy(this)
                assertEquals(3 - it, sellIn, "$it")
                assertEquals(min((initQuality + it), QUALITY_MAX), quality, "$it")
            }
        }
    }

    @Test
    fun `should handle minimum quality bound successfully`() {
        val initQuality = 1
        val staticQualityStrategy = static(-1, eolMultiplier(1))
        val item = item("foo", 3, initQuality)
        repeat(2) {
            staticQualityStrategy(item)
        }
        with(item) {
            assertEquals(0, quality)
        }
    }

    @Test
    fun `should handle EOL multiplier modificator for properly`() {
        val commonBehaviour = static(-1, eolMultiplier(2))
        val item = item("foo", 3, 10)
        repeat(5) {
            commonBehaviour(item)
        }
        with(item) {
            assertEquals(-2, sellIn)
            assertEquals(3, quality)
        }
    }

    @Test
    fun `should handle EOL eolJump modificator properly`() {
        val commonBehaviour = static(-1, eolJump(10))
        val item = item("foo", 1, 10)
        repeat(2) {
            commonBehaviour(item)
        }
        with(item) {
            assertEquals(-1, sellIn)
            assertEquals(10, quality)
        }
    }

    @Test
    fun `should handle EOL eolEmpty modificator properly`() {
        val commonBehaviour = static(-1, eolEmpty())
        val item = item("foo", 0, 10)
        commonBehaviour(item)
        with(item) {
            assertEquals(-1, sellIn)
            assertEquals(9, quality)
        }
    }

    @Test
    fun `should keep quality in provided bounds `() {
        assertEquals(10, validateQuality(10))
        assertEquals(QUALITY_MIN, validateQuality(-10))
        assertEquals(QUALITY_MAX, validateQuality(60))
    }
}
