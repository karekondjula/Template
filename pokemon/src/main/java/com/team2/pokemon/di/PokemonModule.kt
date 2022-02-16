package com.team2.pokemon.di

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.team2.pokemon.database.PokemonDatabase
import com.team2.pokemon.database.PokemonsDao
import com.team2.pokemon.repository.PokemonRepository
import com.team2.pokemon.service.PokemonApi
import com.team2.pokemon.usecase.GetPokemonsUseCase
import com.team2.pokemon.usecase.GetPokemonsUseCaseImpl
import com.team2.pokemon.viewmodel.PokemonsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val baseUrl = "https://pokeapi.co/api/v2/"

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@InternalCoroutinesApi
val pokemonModule = module {

    viewModel { PokemonsViewModel(get()) }

    factory { GetPokemonsUseCaseImpl(get()) } bind GetPokemonsUseCase::class

    factory {
        PokemonRepository(get(), get(), get())
    }

    single {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(PokemonApi::class.java)
    } bind PokemonApi::class

    fun provideDatabase(application: Application): PokemonDatabase {
        return Room.databaseBuilder(application, PokemonDatabase::class.java, "pokemon-database")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun providePokemonsDao(dataBase: PokemonDatabase): PokemonsDao {
        return dataBase.pokemonsDao()
    }

    single { provideDatabase(androidApplication()) }
    single { providePokemonsDao(get()) }
}