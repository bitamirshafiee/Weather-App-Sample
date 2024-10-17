package com.weather.di

import com.weather.BuildConfig
import com.weather.data.repository.WeatherRepository
import com.weather.data.repository.WeatherRepositoryImpl
import com.weather.data.service.ServiceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideBodyLogging(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttp(bodyLoggingInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor(bodyLoggingInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder().baseUrl(BuildConfig.baseUrl)
        .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()

    @Provides
    @Singleton
    fun provideServiceProvider(retrofit: Retrofit): ServiceProvider = ServiceProvider(retrofit)

    @Provides
    @Singleton
    fun provideWeatherRepository(serviceProvider: ServiceProvider): WeatherRepository =
        WeatherRepositoryImpl(serviceProvider)
}