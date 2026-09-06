package com.app.arco.core.domain

import kotlin.math.PI

private const val STRAIGHT_ANGLE_DEGREES = 180.0

/** 度をラジアンへ。角度は度で持ち、ラジアンは計算の内側だけで使う（docs/conventions.md）。 */
internal fun Double.toRadians(): Double = this * PI / STRAIGHT_ANGLE_DEGREES

/** ラジアンを度へ。 */
internal fun Double.toDegrees(): Double = this * STRAIGHT_ANGLE_DEGREES / PI
