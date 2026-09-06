package com.app.arco.core.model

import kotlin.test.Test
import kotlin.test.assertEquals

class BearingTest {
    @Test
    fun keepsDegreesInsideRange() {
        assertEquals(0f, Bearing.ofDegrees(0f).degrees, TOLERANCE)
        assertEquals(90f, Bearing.ofDegrees(90f).degrees, TOLERANCE)
        assertEquals(359.5f, Bearing.ofDegrees(359.5f).degrees, TOLERANCE)
    }

    @Test
    fun wrapsNegativeDegrees() {
        assertEquals(270f, Bearing.ofDegrees(-90f).degrees, TOLERANCE)
        assertEquals(1f, Bearing.ofDegrees(-359f).degrees, TOLERANCE)
    }

    @Test
    fun wrapsDegreesBeyondFullTurn() {
        assertEquals(0f, Bearing.ofDegrees(360f).degrees, TOLERANCE)
        assertEquals(90f, Bearing.ofDegrees(450f).degrees, TOLERANCE)
        assertEquals(10f, Bearing.ofDegrees(730f).degrees, TOLERANCE)
    }

    @Test
    fun treatsNegativeZeroAsZero() {
        // value class の等価判定は Float の equals に委ねられるため、
        // -0.0 が残っていると 0.0 と別物になる
        assertEquals(Bearing.ofDegrees(0f), Bearing.ofDegrees(-0f))
    }

    private companion object {
        const val TOLERANCE = 0.01f
    }
}
