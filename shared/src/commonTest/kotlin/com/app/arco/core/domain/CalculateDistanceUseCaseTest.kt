package com.app.arco.core.domain

import com.app.arco.core.model.Coordinate
import kotlin.math.abs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CalculateDistanceUseCaseTest {
    private val calculateDistance = CalculateDistanceUseCase()

    @Test
    fun returnsZeroForSamePoint() {
        val tokyo = Coordinate(latitude = 35.681236, longitude = 139.767125)
        assertEquals(0, calculateDistance(tokyo, tokyo))
    }

    @Test
    fun measuresOneDegreeOfLongitudeOnEquator() {
        // 赤道上の経度 1 度は平均半径の円周 / 360 に一致する
        val distance = calculateDistance(Coordinate(0.0, 0.0), Coordinate(0.0, 1.0))
        assertNear(expected = 111_195, actual = distance, tolerance = 2)
    }

    @Test
    fun measuresOneDegreeOfLatitude() {
        // 球面近似では緯度 1 度も経度 1 度（赤道上）と同じ長さになる
        val distance = calculateDistance(Coordinate(0.0, 0.0), Coordinate(1.0, 0.0))
        assertNear(expected = 111_195, actual = distance, tolerance = 2)
    }

    @Test
    fun shrinksLongitudeSpanAwayFromEquator() {
        // 緯度 35 度では経度方向の距離が cos(35°) ≒ 0.819 倍になる
        val distance = calculateDistance(Coordinate(35.0, 139.0), Coordinate(35.0, 139.001))
        assertNear(expected = 91, actual = distance, tolerance = 2)
    }

    @Test
    fun isSymmetric() {
        val from = Coordinate(latitude = 35.681236, longitude = 139.767125)
        val to = Coordinate(latitude = 35.628471, longitude = 139.738760)
        assertEquals(calculateDistance(from, to), calculateDistance(to, from))
    }

    @Test
    fun handlesAntipodalPointsWithoutNaN() {
        // haversine が 1 をわずかに超えて asin の定義域を外れうる位置。
        // 地球一周の半分に収まっていれば NaN は出ていない
        val distance = calculateDistance(Coordinate(0.0, 0.0), Coordinate(0.0, 180.0))
        assertNear(expected = 20_015_115, actual = distance, tolerance = 100)
    }

    private fun assertNear(
        expected: Int,
        actual: Int,
        tolerance: Int,
    ) {
        assertTrue(
            abs(actual - expected) <= tolerance,
            "expected $expected ± $tolerance but was $actual",
        )
    }
}
