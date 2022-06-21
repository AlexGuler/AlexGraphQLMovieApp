package com.podium.technicalchallenge.di

import com.podium.technicalchallenge.BuildConfig
import com.podium.technicalchallenge.network.retrofit.GraphQLService
import com.podium.technicalchallenge.repos.Repo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * TODO: Add this to a secrets.properties file and use [BuildConfig] to grab it.
 */
private val API_URL = "https://podium-fe-challenge-2021.netlify.app/.netlify/functions/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGraphQLService(): GraphQLService = Retrofit
        .Builder()
        .client(OkHttpClient.Builder().build())
        .baseUrl(API_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GraphQLService::class.java)

    @Singleton
    @Provides
    fun provideRepo(service: GraphQLService): Repo = Repo(service)
}
