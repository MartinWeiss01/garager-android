package cz.martinweiss.garager.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.first

class DataStoreImpl(private val dataStore: DataStore<Preferences>) : IDataStoreController {
    override suspend fun getValueByKey(key: String): String? {
        val data = dataStore.data.first()
        return data[stringPreferencesKey(key)]
    }

    override suspend fun updateKey(key: String, value: Any) {
        dataStore.edit { preferences ->
            when (value) {
                is String -> preferences[stringPreferencesKey(key)] = value
                is Int -> preferences[intPreferencesKey(key)] = value
                is Boolean -> preferences[booleanPreferencesKey(key)] = value
                else -> throw IllegalArgumentException("Unsupported value type")
            }
        }
    }
}