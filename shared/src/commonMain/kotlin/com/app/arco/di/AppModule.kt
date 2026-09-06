package com.app.arco.di

import com.app.arco.app.AppTabBridge
import com.app.arco.core.common.AppNavigator
import com.app.arco.core.domain.CalculateBearingUseCase
import com.app.arco.core.domain.CalculateDistanceUseCase
import com.app.arco.core.domain.DetectArrivalUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * アプリと同じ寿命を持つものの登録。
 *
 * すべて `single` で登録する。`factory` にすると購読する側ごとに別インスタンスが生まれ、
 * 「状態の所有者は1つ」という前提が崩れる（docs/architecture.md「状態をどこに置くか」）。
 * UseCase は状態を持たないので `factory` でも壊れないが、生成し直す理由もないので揃えておく。
 *
 * モジュールを分割したら、定義はそれぞれの Gradle モジュール側の Koin module へ移し、
 * :shared はそれを束ねるだけにする。いまはモジュールが1つなので Koin module も1つでいい。
 */
val appModule: Module =
    module {
        single { AppNavigator() }
        single { AppTabBridge(navigator = get()) }

        single { CalculateDistanceUseCase() }
        single { CalculateBearingUseCase() }
        single { DetectArrivalUseCase() }
    }
