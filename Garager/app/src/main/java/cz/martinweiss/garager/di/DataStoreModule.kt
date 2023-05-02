package cz.martinweiss.garager.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import cz.martinweiss.garager.datastore.DataStoreImpl
import cz.martinweiss.garager.datastore.IDataStoreController
import org.koin.dsl.module

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "garager")
val dataStoreModule = module {
    single {
        val context: Context = get()
        context.dataStore
    }

    fun provideDataStore(dataStore: DataStore<Preferences>): IDataStoreController {
        return DataStoreImpl(dataStore)
    }

    single { provideDataStore(get()) }
}