package com.test.a2.domain

import com.test.a2.data.db.entities.ActualUser
import com.test.a2.data.db.entities.UserEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class RegistrationUseCase(
    private val registration: UserRegistrationRepository,
    private val sharedP: PreferencesRepository
) {
    val users = registration.fullInfo()

    fun getUser(name: String): Single<UserEntity> {
        return registration.getUser(name)
    }

    fun insertUser(user: UserEntity): Completable {
       return registration.insertUser(user)
    }

    fun updateProgress(): Completable{
        return registration.clearProgress()
    }

    fun removeAll(): Completable {
        return registration.removeAll()
    }

    val actualName = sharedP.actualName
    val actualProgress = sharedP.progress

    fun saveName(name: String) {
        sharedP.saveActualName(name)
    }

    fun saveProgress(progress: Int) {
        sharedP.saveActualProgress(progress)
    }

}
