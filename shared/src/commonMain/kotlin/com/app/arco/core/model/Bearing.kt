package com.app.arco.core.model

import kotlin.jvm.JvmInline

/**
 * 方角。度で持つ（docs/conventions.md「ドメイン用語」）。
 *
 * 生の `Float` ではなくこの型にしているのは、0 以上 360 未満に正規化されていることを
 * 型で保証するため。矢印を描く側は範囲外を考えなくてよくなる。
 */
@JvmInline
value class Bearing private constructor(
    val degrees: Float,
) {
    companion object {
        private const val FULL_TURN_DEGREES = 360f

        /** 任意の度数を 0 以上 360 未満に畳んで [Bearing] にする。 */
        fun ofDegrees(degrees: Float): Bearing {
            // 剰余を 2 回とるのは、負の入力を正へ回すのと同時に -0.0 を 0.0 へ潰すため。
            // value class の等価判定は Float の equals に委ねられ、-0.0 と 0.0 は別物になる
            val normalized = (degrees % FULL_TURN_DEGREES + FULL_TURN_DEGREES) % FULL_TURN_DEGREES
            return Bearing(normalized)
        }
    }
}
