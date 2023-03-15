package com.test.a2.ui.viewmodels

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.a2.data.db.entities.UserEntity
import com.test.a2.data.network.data.response.TrainingResponse
import com.test.a2.domain.RegistrationUseCase
import com.test.a2.domain.TrainingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val trainingUseCase: TrainingUseCase,
    private val registrationUseCase: RegistrationUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private var listOfUsers = listOf<UserEntity>()

    val actualProgress = sharedPreferences.getInt(PROGRESS, 0)
    val actualName = sharedPreferences.getString(NAME, "")
    val oldTime = sharedPreferences.getLong(OLD_TIME,0L)

    private fun saveProgress(value: Int) {
        sharedPreferences.edit().putInt(PROGRESS, value).apply()
    }

    private fun saveName(value: String) {
        sharedPreferences.edit().putString(NAME, value).apply()
    }

    fun saveOldTime(time: Long){
        sharedPreferences.edit().putLong(OLD_TIME,time).apply()
    }


    private val _isUserChecked = MutableLiveData<Boolean>()
    val isUserChecked: LiveData<Boolean>
        get() = _isUserChecked

    private val _isDataCleared = MutableLiveData<Boolean>()
    val isDataCleared: LiveData<Boolean>
        get() = _isDataCleared

    private val _trainingListLD = MutableLiveData<List<TrainingResponse>>()
    val trainingListLD: LiveData<List<TrainingResponse>>
        get() = _trainingListLD

    private val _progressLD = MutableLiveData<Int>()
    val progressLD: LiveData<Int>
        get() = _progressLD

    private val _userLD = MutableLiveData<UserEntity>()
    val userLD: LiveData<UserEntity>
        get() = _userLD

    fun getTraining(day: String) {
        trainingUseCase.getTraining(day)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                _trainingListLD.postValue(it)
                Log.d("TRAINING", it.toString())
            }, {
                Log.d("TRAINING", it.toString())
            })
    }

    fun getUsers() {
        registrationUseCase.users
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                listOfUsers = it
                Log.d("Users", it.toString())
            }, {
                Log.d("Users", it.toString())
            })
    }

    fun getUser(name: String) {
        Log.d("getUser__", name)

        registrationUseCase.getUser(name)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe({
                _userLD.postValue(it)
                _progressLD.postValue(it.progress)
            }, {
                Log.d("getUser", it.toString())
            })
    }


    private fun calculateProgress(distance: Double, sitUp: Double, user: UserEntity?): Int {
        var progress = 0.0

        progress = if (user?.weight != null) {
            ((distance * 1000 + sitUp * 10) * user.weight) / 10
        } else {
            0.0
        }

        return progress.toInt()
    }

    fun updateUser(distance: Double, sitUp: Double, user: UserEntity?) {

        if (user != null) {
            val progress = user.progress + calculateProgress(distance, sitUp, user)

            saveProgress(progress)
            saveName(user.name)

            registrationUseCase.insertUser(
                UserEntity(
                    name = user.name,
                    height = user.height,
                    weight = user.weight,
                    progress = progress
                )
            ).observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    getUser(user.name)
                    _progressLD.postValue(progress)
                }, {
                    Log.d("checkUser", it.toString())
                })
        }
    }

    fun checkUser(name: String, height: Double, weight: Double) {

        val users = listOfUsers.filter { it.name == name }.map { it.name }


        if (users.isEmpty()) {
            saveName(name)
            saveProgress(0)
            registrationUseCase.insertUser(
                UserEntity(
                    name = name,
                    height = height,
                    weight = weight,
                    progress = 0
                )
            ).observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    _isUserChecked.postValue(true)
                }, {
                    _isUserChecked.postValue(false)
                    Log.d("checkUser", it.toString())
                })

        } else {
            saveName(name)
            registrationUseCase.getUser(name)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    _userLD.postValue(it)
                    saveProgress(it.progress)
                    _isUserChecked.postValue(true)
                }, {
                    _isUserChecked.postValue(false)
                    Log.d("checkUser", it.toString())
                })
        }
    }

    fun clearData() {
        clearSP()
        registrationUseCase.removeAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _isDataCleared.postValue(true)
            }, {
                Log.d("clearData", it.toString())
            })
    }

    fun clearSP() {
        saveName("")
        saveProgress(0)
    }

    fun checkDate(){

    }

    fun clearProgress(){
        clearSP()
        registrationUseCase.updateProgress()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d("clearProgress","SUCCESS")
            },{
                Log.d("clearProgress",it.message.toString())
            })
    }


    companion object {
        private const val NAME = "NAME"
        private const val PROGRESS = "PROGRESS"
        private const val OLD_TIME = "OLD_TIME"
    }
}