package com.test.a2.domain

interface PreferencesRepository {
    val actualName: String
    fun saveActualName(name:String)

    val progress: Int
    fun saveActualProgress(value:Int)
}