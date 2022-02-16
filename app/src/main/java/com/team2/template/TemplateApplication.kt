package com.team2.template

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.team2.core.di.coreModule
import com.team2.pokemon.di.pokemonModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@InternalCoroutinesApi
class TemplateApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TemplateApplication)
            modules(coreModule, pokemonModule)
        }
    }
}