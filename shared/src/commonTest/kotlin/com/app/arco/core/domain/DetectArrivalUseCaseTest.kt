package com.app.arco.core.domain

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DetectArrivalUseCaseTest {
    private val detectArrival = DetectArrivalUseCase()

    @Test
    fun arrivesInsideThreshold() {
        assertTrue(detectArrival(0))
        assertTrue(detectArrival(DetectArrivalUseCase.DEFAULT_THRESHOLD_METERS - 1))
    }

    @Test
    fun arrivesExactlyOnThreshold() {
        assertTrue(detectArrival(DetectArrivalUseCase.DEFAULT_THRESHOLD_METERS))
    }

    @Test
    fun doesNotArriveOutsideThreshold() {
        assertFalse(detectArrival(DetectArrivalUseCase.DEFAULT_THRESHOLD_METERS + 1))
        assertFalse(detectArrival(1_000))
    }

    @Test
    fun honoursInjectedThreshold() {
        val strict = DetectArrivalUseCase(thresholdMeters = 5)
        assertTrue(strict(5))
        assertFalse(strict(6))
    }
}
