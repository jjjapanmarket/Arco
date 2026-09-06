package com.app.arco.core.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class BearingTest {
    @Test
    fun keepsDegreesInsideRange() {
        assertEquals(0f, degreesOf(0f), TOLERANCE)
        assertEquals(90f, degreesOf(90f), TOLERANCE)
        assertEquals(359.5f, degreesOf(359.5f), TOLERANCE)
    }

    @Test
    fun wrapsNegativeDegrees() {
        assertEquals(270f, degreesOf(-90f), TOLERANCE)
        assertEquals(1f, degreesOf(-359f), TOLERANCE)
    }

    @Test
    fun wrapsDegreesBeyondFullTurn() {
        assertEquals(0f, degreesOf(360f), TOLERANCE)
        assertEquals(90f, degreesOf(450f), TOLERANCE)
        assertEquals(10f, degreesOf(730f), TOLERANCE)
    }

    @Test
    fun treatsNegativeZeroAsZero() {
        // value class の等価判定は Float の equals に委ねられるため、
        // -0.0 が残っていると 0.0 と別物になる
        assertEquals(Bearing.ofDegrees(0f), Bearing.ofDegrees(-0f))
    }

    @Test
    fun rejectsNaN() {
        // NaN % 360 は NaN のままなので、正規化を通しても範囲に入らない
        assertNull(Bearing.ofDegrees(Float.NaN))
    }

    @Test
    fun rejectsInfinity() {
        assertNull(Bearing.ofDegrees(Float.POSITIVE_INFINITY))
        assertNull(Bearing.ofDegrees(Float.NEGATIVE_INFINITY))
    }

    private fun degreesOf(degrees: Float): Float = assertNotNull(Bearing.ofDegrees(degrees)).degrees

    private companion object {
        const val TOLERANCE = 0.01f
    }
}
