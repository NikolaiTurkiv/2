package com.test.a2.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.a2.data.db.entities.ActualUser
import com.test.a2.data.db.entities.UserEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface ActualUserDao {

    @Query("SELECT * FROM actual_user")
    fun getUsers(): Single<List<ActualUser>>

    @Query("SELECT * FROM actual_user LIMIT 1")
    fun getUser(): Single<ActualUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: ActualUser): Completable

    @Query("DELETE FROM actual_user")
    fun removeAll(): Completable

}