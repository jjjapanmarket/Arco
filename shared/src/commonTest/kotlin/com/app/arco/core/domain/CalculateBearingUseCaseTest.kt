package com.app.arco.core.domain

import com.app.arco.core.model.Coordinate
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateBearingUseCaseTest {
    private val calculateBearing = CalculateBearingUseCase()
    private val origin = Coordinate(latitude = 0.0, longitude = 0.0)

    @Test
    fun pointsNorth() {
        assertEquals(0f, calculateBearing(origin, Coordinate(1.0, 0.0)).degrees, TOLERANCE)
    }

    @Test
    fun pointsEast() {
        assertEquals(90f, calculateBearing(origin, Coordinate(0.0, 1.0)).degrees, TOLERANCE)
    }

    @Test
    fun pointsSouth() {
        assertEquals(180f, calculateBearing(origin, Coordinate(-1.0, 0.0)).degrees, TOLERANCE)
    }

    @Test
    fun pointsWestAsPositiveDegrees() {
        // atan2 は -90 度を返すが、Bearing が 0〜360 に畳むので 270 になる
        assertEquals(270f, calculateBearing(origin, Coordinate(0.0, -1.0)).degrees, TOLERANCE)
    }

    @Test
    fun pointsNorthEastOnDiagonal() {
        // 赤道上の短い斜めなら 45 度に十分近づく
        val bearing = calculateBearing(origin, Coordinate(0.001, 0.001))
        assertEquals(45f, bearing.degrees, 0.5f)
    }

    private companion object {
        const val TOLERANCE = 0.01f
    }
}
