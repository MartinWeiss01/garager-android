package cz.martinweiss.garager.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.first

class DataStoreImpl(private val dataStore: DataStore<Preferences>) : IDataStoreController {
    override suspend fun getValueByKey(key: String, type: VALUE_TYPE): Any? {
        val data = dataStore.data.first()
        return when (type) {
            VALUE_TYPE.STRING -> data[stringPreferencesKey(key)]
            VALUE_TYPE.INT -> data[intPreferencesKey(key)]
            VALUE_TYPE.BOOLEAN -> data[booleanPreferencesKey(key)]
        }
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