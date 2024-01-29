package com.anezin.smash.infrastructure.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.anezin.smash.core.interfaces.MyIdRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SharedPrefsIdRepository(
    private val dataStore: DataStore<Preferences>
): MyIdRepository {

    override fun getMyId(): Flow<String> {
        val key = stringPreferencesKey("myId")
        return dataStore.data
            .map { preferences ->
                preferences[key] ?: ""
            }
    }

    override suspend fun saveMyId(myId: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey("myId")] = myId
        }
    }
}