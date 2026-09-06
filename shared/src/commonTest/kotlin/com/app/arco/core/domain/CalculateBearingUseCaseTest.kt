package com.app.arco.core.domain

import com.app.arco.core.model.Coordinate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class CalculateBearingUseCaseTest {
    private val calculateBearing = CalculateBearingUseCase()
    private val origin = Coordinate(latitude = 0.0, longitude = 0.0)

    @Test
    fun pointsNorth() {
        assertEquals(0f, degreesTo(Coordinate(1.0, 0.0)), TOLERANCE)
    }

    @Test
    fun pointsEast() {
        assertEquals(90f, degreesTo(Coordinate(0.0, 1.0)), TOLERANCE)
    }

    @Test
    fun pointsSouth() {
        assertEquals(180f, degreesTo(Coordinate(-1.0, 0.0)), TOLERANCE)
    }

    @Test
    fun pointsWestAsPositiveDegrees() {
        // atan2 は -90 度を返すが、Bearing が 0〜360 に畳むので 270 になる
        assertEquals(270f, degreesTo(Coordinate(0.0, -1.0)), TOLERANCE)
    }

    @Test
    fun pointsNorthEastOnDiagonal() {
        // 赤道上の短い斜めなら 45 度に十分近づく
        assertEquals(45f, degreesTo(Coordinate(0.001, 0.001)), 0.5f)
    }

    @Test
    fun returnsNullForNonFiniteCoordinate() {
        // 座標が壊れていれば方角も定まらない。矢印を出さない判断を呼ぶ側に渡す
        assertNull(calculateBearing(origin, Coordinate(Double.NaN, 0.0)))
        assertNull(calculateBearing(Coordinate(0.0, Double.POSITIVE_INFINITY), origin))
    }

    private fun degreesTo(to: Coordinate): Float = assertNotNull(calculateBearing(origin, to)).degrees

    private companion object {
        const val TOLERANCE = 0.01f
    }
}
