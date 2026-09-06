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

        /**
         * 度数を 0 以上 360 未満に畳んで [Bearing] にする。非有限値のときは null を返す。
         *
         * NaN と Infinity を通さないのは、`NaN % 360` が `NaN` のままになり、
         * この型が約束している範囲が破れるため。
         *
         * 例外ではなく null で表すのは、方位が取れない状況がこのアプリでは
         * 想定内の状態だから（AGENTS.md「踏みやすい地雷」のコンパスの磁気ノイズ、
         * docs/conventions.md「エラー」）。都市部で加速度と地磁気のベクトルが縮退すると
         * Android の `getOrientation()` は NaN を返す。そのまま通すと矢印が黙って
         * 北を指し続け、原因に辿り着けない。null なら「矢印を出さない」を選べる。
         */
        fun ofDegrees(degrees: Float): Bearing? {
            if (!degrees.isFinite()) return null
            // 剰余を 2 回とるのは、負の入力を正へ回すのと同時に -0.0 を 0.0 へ潰すため。
            // value class の等価判定は Float の equals に委ねられ、-0.0 と 0.0 は別物になる
            val normalized = (degrees % FULL_TURN_DEGREES + FULL_TURN_DEGREES) % FULL_TURN_DEGREES
            return Bearing(normalized)
        }
    }
}
