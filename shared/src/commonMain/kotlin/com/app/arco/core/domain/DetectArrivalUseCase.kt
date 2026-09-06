package com.app.arco.core.domain

/**
 * 残り距離から到着したかを判定する。
 *
 * 距離の計算とは別の UseCase に分けてある。「何メートルまで近づけば着いたことにするか」は
 * 計算ではなく仕様で、実機で歩いて調整することになる値だから。1 箇所に閉じておけば
 * 調整がここだけで済む。
 *
 * 閾値を引数で受けられるようにしているのは、既定値がまだ暫定のため。
 */
class DetectArrivalUseCase(
    private val thresholdMeters: Int = DEFAULT_THRESHOLD_METERS,
) {
    operator fun invoke(remainingMeters: Int): Boolean = remainingMeters <= thresholdMeters

    companion object {
        /**
         * 到着とみなす残り距離の既定値。
         *
         * 市街地の GPS 測位誤差が 10〜20m 出ることを見込んだ暫定値で、根拠は実測ではない。
         * 屋外で歩いて詰める（AGENTS.md「動作確認」）。
         */
        const val DEFAULT_THRESHOLD_METERS = 30
    }
}
