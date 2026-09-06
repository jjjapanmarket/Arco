package com.app.arco.core.model

/**
 * 緯度経度。
 *
 * プラットフォームの `CLLocation` / `android.location.Location` はここまで上げてこない。
 * Data 層の DataSource がこの型に詰め替えてから Domain 層へ渡す
 * （docs/architecture.md「センサーの扱い」）。
 *
 * 値の妥当性はここで検査しない。検査を入れると純粋な値型が例外を投げる口を持つことになり、
 * 「Domain 層は例外を投げない」という前提と噛み合わなくなるため
 * （docs/conventions.md「エラー」）。
 */
data class Coordinate(
    val latitude: Double,
    val longitude: Double,
)
