package com.test.core_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.a2.data.db.dao.ActualUserDao
import com.test.a2.data.db.dao.UserDao
import com.test.a2.data.db.entities.ActualUser
import com.test.a2.data.db.entities.UserEntity

@Database(entities = [UserEntity::class,ActualUser::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userInfoDao(): UserDao
    abstract fun actualUserDao(): ActualUserDao
}