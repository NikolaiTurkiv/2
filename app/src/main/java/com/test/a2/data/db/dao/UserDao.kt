package com.test.a2.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.a2.data.db.entities.UserEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface UserDao {

    @Query("SELECT * FROM user_info")
    fun getUsers(): Single<List<UserEntity>>

    @Query("SELECT * From user_info WHERE name = :name LIMIT 1")
    fun getUser(name: String): Single<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserEntity): Completable

    @Query("DELETE FROM user_info")
    fun removeAll(): Completable


    @Query("UPDATE user_info SET progress = 0")
    fun updateUsersProgress() : Completable

}