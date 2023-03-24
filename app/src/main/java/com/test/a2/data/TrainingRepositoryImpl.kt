package com.test.a2.data

import com.test.a2.data.network.data.PhoneInfoRequest
import com.test.a2.data.network.data.SplashApi
import com.test.a2.data.network.data.response.SplashResponse
import com.test.a2.data.network.data.response.TrainingResponse
import com.test.a2.domain.TrainingRepository
import com.test.network.data.NetworkApi
import io.reactivex.rxjava3.core.Single

class TrainingRepositoryImpl(
    private val api: NetworkApi,
    private val splashApi: SplashApi
): TrainingRepository {
    override fun getTodayTraining(day:String): Single<List<TrainingResponse>> {
        return api.getTraining(day)
    }

    override fun fetchPhoneData(
        id: String,
        locale: String,
        phoneModel: String
    ): Single<SplashResponse> {
        return splashApi.fetchPhoneStatus(PhoneInfoRequest(
            phoneModel,locale,id
        ))
    }
}