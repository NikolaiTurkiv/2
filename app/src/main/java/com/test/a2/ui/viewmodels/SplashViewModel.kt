package com.test.a2.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.a2.data.network.data.response.SplashResponse
import com.test.a2.domain.TrainingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SplashViewModel  @Inject constructor(
    private val trainingUseCase: TrainingUseCase
) : ViewModel(){
    val id = trainingUseCase.id

    private val _response = MutableLiveData<SplashResponse>()
    val response: LiveData<SplashResponse>
        get() = _response

    fun saveId(id:String){
        trainingUseCase.saveId(id)
    }

    fun fetchPhoneStatus(
        phoneName: String,
        locale: String,
        id: String,
    ) {
        trainingUseCase.fetchPhoneData(phoneName, locale, id)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                _response.postValue(it)
            }, {
                Log.d("fetchPhoneStatus", it.message.toString())
            })
    }
}