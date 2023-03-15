package com.test.a2.di

import android.content.SharedPreferences
import com.test.a2.domain.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainObject {

    @Provides
    @Singleton
    fun provideTrainingUseCase(trainingRepository: TrainingRepository) =
        TrainingUseCase(trainingRepository)

    @Provides
    @Singleton
    fun provideRegistrationUseCase(registrationRepository: UserRegistrationRepository,sp:PreferencesRepository) =
        RegistrationUseCase(registrationRepository,sp)
}