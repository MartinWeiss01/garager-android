package cz.martinweiss.garager.datastore

import cz.martinweiss.garager.model.currencies

const val DATASTORE_MOT_DAYS = "warning_mot"
const val DEFAULT_MOT_WARNING_DAYS_VALUE = 30
const val DATASTORE_CURRENCY = "currency"
var DEFAULT_CURRENCY_VALUE = currencies[0].name

interface IDataStoreController {
    suspend fun getStringByKey(key: String): String?
    suspend fun updateStringKey(key: String, value: String)
    suspend fun getIntByKey(key: String): Int?
    suspend fun updateIntKey(key: String, value: Int)
    suspend fun getBooleanByKey(key: String): Boolean?
    suspend fun updateBooleanKey(key: String, value: Boolean)
}