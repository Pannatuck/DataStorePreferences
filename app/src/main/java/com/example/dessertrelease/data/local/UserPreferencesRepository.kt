package com.example.dessertrelease.data.local

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences> // property to access DataStore object (and type set as Preferences - work with simple data types (String, Int, Bool)
) {
    val isLinearLayout: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException){
                Log.e(TAG, "Error reading preferences", it)
                emit(emptyPreferences()) // can be used to create empty preferences in example like this
            } else {
                throw it
            }
        }
        .map { preferences -> // must be converted from Flow<Preferences> to Flow<Boolean>
            preferences[IS_LINEAR_LAYOUT] ?: true // also it doesn't have standard value at beginning, so you must make it yourself
        }

    private companion object {
        val IS_LINEAR_LAYOUT = booleanPreferencesKey("is_linear_layout") // key to access dataStore value
        const val TAG = "UserPreferencesRepo"
    }

    suspend fun saveLayoutPreference(isLinearLayout: Boolean){
        dataStore.edit {preferences -> // all CRUD operations to DataStore must be made through .edit method, other ways it'll throw error
            preferences[IS_LINEAR_LAYOUT] = isLinearLayout
        }
    }

}