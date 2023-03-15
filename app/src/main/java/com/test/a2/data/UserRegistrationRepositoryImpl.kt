package com.test.a2.data

import com.test.a2.data.db.dao.ActualUserDao
import com.test.a2.domain.UserRegistrationRepository
import com.test.a2.data.db.dao.UserDao
import com.test.a2.data.db.entities.ActualUser
import com.test.a2.data.db.entities.UserEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class UserRegistrationRepositoryImpl(
    private val bd: UserDao,
 ) : UserRegistrationRepository {
    override fun fullInfo(): Single<List<UserEntity>> {
        return bd.getUsers()
    }

    override fun insertUser(user: UserEntity): Completable {
       return bd.insert(user)
    }

    override fun getUser(name: String): Single<UserEntity> {
        return bd.getUser(name)
    }

    override fun removeAll(): Completable {
       return bd.removeAll()
    }

    override fun clearProgress(): Completable {
        return bd.updateUsersProgress()
    }

}