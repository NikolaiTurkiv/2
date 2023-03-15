package com.test.a2.data

import com.test.a2.data.network.data.response.TrainingResponse
import com.test.a2.domain.TrainingRepository
import com.test.network.data.NetworkApi
import io.reactivex.rxjava3.core.Single

class TrainingRepositoryImpl(
    private val api: NetworkApi
): TrainingRepository {
    override fun getTodayTraining(day:String): Single<List<TrainingResponse>> {
        return api.getTraining(day)
    }
}