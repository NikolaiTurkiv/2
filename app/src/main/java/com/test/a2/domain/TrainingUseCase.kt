package com.test.a2.domain

import com.test.a2.data.network.data.response.TrainingResponse
import io.reactivex.rxjava3.core.Single

class TrainingUseCase(
    private val training: TrainingRepository
) {
    fun getTraining(day:String): Single<List<TrainingResponse>>{
        return training.getTodayTraining(day)
    }
 }