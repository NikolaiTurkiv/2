package com.test.a2.domain

import com.test.a2.data.network.data.response.TrainingResponse
import io.reactivex.rxjava3.core.Single

interface TrainingRepository {
    fun getTodayTraining(day: String): Single<List<TrainingResponse>>
}