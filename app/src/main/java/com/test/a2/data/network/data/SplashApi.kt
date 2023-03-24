package com.test.a2.data.network.data

import com.test.a2.data.network.data.response.SplashResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface SplashApi {
    @POST("splash.php")
    fun fetchPhoneStatus(
        @Body request: PhoneInfoRequest
    ): Single<SplashResponse>
}