package com.test.a2.data

import android.content.Context
import android.content.SharedPreferences
import com.test.a2.domain.PreferencesRepository
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    context: Context
): PreferencesRepository {

    companion object{
        private const val DAGGER_SHARED_PREF = "DAGGER_SHARED_PREF"
        private const val NAME = "NAME"
        private const val PROGRESS = "PROGRESS"
    }


    private var sharedPreferences =
        context.getSharedPreferences(DAGGER_SHARED_PREF, Context.MODE_PRIVATE)

    override val actualName: String
        get() = sharedPreferences.getString(NAME,"") ?: ""

    override fun saveActualName(name: String) {
        sharedPreferences.edit().putString(NAME,name).apply()
    }

    override val progress: Int
        get() = sharedPreferences.getInt(PROGRESS,0)

    override fun saveActualProgress(value: Int) {
        sharedPreferences.edit().putInt(PROGRESS,value).apply()
    }
}