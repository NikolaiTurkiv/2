package com.test.a2.domain

import com.test.a2.data.db.entities.UserEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface UserRegistrationRepository {
    fun fullInfo(): Single<List<UserEntity>>
    fun insertUser(user: UserEntity): Completable
    fun getUser(name:String): Single<UserEntity>
    fun removeAll(): Completable

    fun clearProgress(): Completable
}