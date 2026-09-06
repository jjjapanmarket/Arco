package com.app.arco.core.domain

import com.app.arco.core.model.Coordinate
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt

/** 地球を球とみなしたときの平均半径（IUGG の平均半径 R1）。 */
private const val EARTH_RADIUS_METERS = 6_371_008.8

/**
 * 2 地点間の距離をメートルで返す。
 *
 * haversine 式で球面距離を出す。楕円体（Vincenty 等）を使わないのは、
 * このアプリが扱うのは徒歩圏の数 km で、その範囲なら球面近似の誤差が
 * GPS の測位誤差より小さく、精度を上げても体験に出ないため。
 *
 * 失敗しうる処理ではないので `Result` では包まない。`Result` を返すのは
 * 失敗が戻り値として意味を持つ場合だけ（docs/conventions.md「エラー」）。
 */
class CalculateDistanceUseCase {
    operator fun invoke(
        from: Coordinate,
        to: Coordinate,
    ): Int {
        val fromLatitude = from.latitude.toRadians()
        val toLatitude = to.latitude.toRadians()
        val latitudeDelta = toLatitude - fromLatitude
        val longitudeDelta = (to.longitude - from.longitude).toRadians()

        val haversine =
            sin(latitudeDelta / 2).let { it * it } +
                cos(fromLatitude) * cos(toLatitude) * sin(longitudeDelta / 2).let { it * it }

        // 丸め誤差で haversine が 1 をわずかに超えると sqrt の結果が asin の定義域を外れる。
        // 対蹠点付近でしか起きないが、NaN が残り距離に化けるより潰しておくほうが安い
        val centralAngle = 2 * asin(min(1.0, sqrt(haversine)))

        return (EARTH_RADIUS_METERS * centralAngle).roundToInt()
    }
}
