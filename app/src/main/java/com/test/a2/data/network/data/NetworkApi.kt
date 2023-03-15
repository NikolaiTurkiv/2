package com.test.network.data

import com.test.a2.data.network.data.response.TrainingResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkApi {

     @GET("{dayOfWeek}.json")
    fun getTraining(
        @Path("dayOfWeek") dayOfWeek: String
    ): Single<List<TrainingResponse>>

}
