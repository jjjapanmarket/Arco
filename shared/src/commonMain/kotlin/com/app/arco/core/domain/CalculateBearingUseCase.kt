package com.app.arco.core.domain

import com.app.arco.core.model.Bearing
import com.app.arco.core.model.Coordinate
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

/**
 * 現在地から目的地を見た方角を返す。
 *
 * 出すのは大圏航路の出発方位（initial bearing）。距離が伸びるほど進行中に値が変わるが、
 * このアプリは常に現在地から取り直すので、常に「いま向くべき向き」になる。
 *
 * ここが返すのは目的地の絶対方位でしかない。端末がどちらを向いているかは別のセンサーの話で、
 * 矢印の向き（絶対方位 − 端末の向き）は上の層で合成する。歩行中と停止中でどちらの方位ソースを
 * 信じるかの判断も別のルール（docs/architecture.md「方位の切り替えはドメインの判断」）。
 */
class CalculateBearingUseCase {
    operator fun invoke(
        from: Coordinate,
        to: Coordinate,
    ): Bearing {
        val fromLatitude = from.latitude.toRadians()
        val toLatitude = to.latitude.toRadians()
        val longitudeDelta = (to.longitude - from.longitude).toRadians()

        val y = sin(longitudeDelta) * cos(toLatitude)
        val x = cos(fromLatitude) * sin(toLatitude) - sin(fromLatitude) * cos(toLatitude) * cos(longitudeDelta)

        return Bearing.ofDegrees(atan2(y, x).toDegrees().toFloat())
    }
}
