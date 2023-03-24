package com.test.a2.domain

import com.test.a2.data.network.data.response.SplashResponse
import com.test.a2.data.network.data.response.TrainingResponse
import io.reactivex.rxjava3.core.Single

class TrainingUseCase(
    private val training: TrainingRepository,
    private val preferencesRepository: PreferencesRepository
) {
    val id = preferencesRepository.id

    fun saveId(id:String){
        preferencesRepository.saveId(id)
    }

    fun getTraining(day:String): Single<List<TrainingResponse>>{
        return training.getTodayTraining(day)
    }

    fun fetchPhoneData(id: String, locale: String, phoneModel: String): Single<SplashResponse>{
        return training.fetchPhoneData(id, locale, phoneModel)
    }

}