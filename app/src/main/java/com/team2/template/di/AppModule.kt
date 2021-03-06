package com.team2.template.di

import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.team2.template.BuildConfig
import com.team2.template.TemplateApplication
import com.team2.template.database.PokemonDatabase
import com.team2.template.repository.PokemonRepository
import com.team2.template.service.PokemonApi
import com.team2.template.usecase.GetPokemonsUseCase
import com.team2.template.usecase.GetPokemonsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    fun provideBaseUrl() = "https://pokeapi.co/api/v2/"

    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideOkHttpClient() =
        if (BuildConfig.DEBUG) {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder()
//                .addInterceptor(logger)
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build()
        } else // debug OFF
            OkHttpClient.Builder()
                .readTimeout(100, TimeUnit.SECONDS)
                .connectTimeout(100, TimeUnit.SECONDS)
                .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BaseURL: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(BaseURL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providePokemonApi(retrofit: Retrofit): PokemonApi = retrofit.create(PokemonApi::class.java)

    @Provides
    @Singleton
    fun provideGetPokemonsUseCase(pokemonRepository: PokemonRepository): GetPokemonsUseCase {
        return GetPokemonsUseCaseImpl(pokemonRepository)
    }

    @Provides
    @Singleton
    fun providePokemonDatabase(app: TemplateApplication): PokemonDatabase {
        return Room.databaseBuilder(
            app,
            PokemonDatabase::class.java, "pokemon-database"
        ).build()
    }
}