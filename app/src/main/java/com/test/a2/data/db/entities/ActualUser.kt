package com.test.a2.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "actual_user")
data class ActualUser(
    @PrimaryKey
    val name: String,
    val height: Double,
    val weight: Double,
    val progress: Int = 0
)