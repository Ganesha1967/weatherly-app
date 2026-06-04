package com.weatherly.weather.di

import android.content.Context
import com.weatherly.weather.data.preferences.AppState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides @Singleton
    fun provideAppState(
        @ApplicationContext context: Context,
    ): AppState = AppState(context)
}
