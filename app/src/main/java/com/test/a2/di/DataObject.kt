package com.test.a2.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.test.a2.data.PreferencesRepositoryImpl
import com.test.a2.data.TrainingRepositoryImpl
import com.test.a2.data.UserRegistrationRepositoryImpl
import com.test.a2.data.db.dao.ActualUserDao
import com.test.a2.domain.PreferencesRepository
import com.test.a2.domain.TrainingRepository
import com.test.a2.domain.UserRegistrationRepository
import com.test.core_db.AppDatabase
import com.test.a2.data.db.dao.UserDao
import com.test.a2.data.network.data.SplashApi
import com.test.network.data.NetworkApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataObject {
    private const val DB_NAME = "app_database"
    private const val BASE_URL = "http://84.38.181.162/ios/"
    private const val SPLASH_URL = "http://94.130.75.196/"
    private const val SHARED_PREF = "SHARED_PREF"


    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(appDatabase: AppDatabase): UserDao = appDatabase.userInfoDao()

    @Provides
    @Singleton
    fun provideActualUser(appDatabase: AppDatabase): ActualUserDao = appDatabase.actualUserDao()


    @Provides
    @Singleton
    fun retrofitService(): NetworkApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        return retrofit.create(NetworkApi::class.java)
    }

    @Provides
    @Singleton
    fun splashService(): SplashApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(SPLASH_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        return retrofit.create(SplashApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTrainingRepository(api: NetworkApi, splashApi: SplashApi): TrainingRepository =
        TrainingRepositoryImpl(api, splashApi)

    @Provides
    @Singleton
    fun provideUserRegistration(
        bd: UserDao,
    ): UserRegistrationRepository =
        UserRegistrationRepositoryImpl(bd)

    @Provides
    fun providePreferencesRepository(@ApplicationContext context: Context): PreferencesRepository =
        PreferencesRepositoryImpl(context)

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    }

}