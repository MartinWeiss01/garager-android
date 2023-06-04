package cz.martinweiss.garager.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.first

class DataStoreImpl(private val dataStore: DataStore<Preferences>) : IDataStoreController {
    override suspend fun getStringByKey(key: String): String? {
        val data = dataStore.data.first()
        return data[stringPreferencesKey(key)]
    }

    override suspend fun updateStringKey(key: String, value: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }

    override suspend fun getIntByKey(key: String): Int? {
        val data = dataStore.data.first()
        return data[intPreferencesKey(key)]
    }

    override suspend fun updateIntKey(key: String, value: Int) {
        dataStore.edit { preferences ->
            preferences[intPreferencesKey(key)] = value
        }
    }

    override suspend fun getBooleanByKey(key: String): Boolean? {
        val data = dataStore.data.first()
        return data[booleanPreferencesKey(key)]
    }

    override suspend fun updateBooleanKey(key: String, value: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }
}