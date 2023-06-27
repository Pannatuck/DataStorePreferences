package com.example.dessertrelease

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.dessertrelease.data.local.UserPreferencesRepository


/* Manual DI for initializing DataStore.
*  To access our DI we must add android:name property to manifest also, so app know where to get values from.
*  Doing it this way will initialize values before initializing MainActivity, so user preferences will be activated at app launch */

// name of preferences that you accessing. As I understand you can have different preferences for different tasks
private const val LAYOUT_PREFERENCE_NAME = "layout_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore( // adding dataStore property to Context to access it anywhere we need
    name = LAYOUT_PREFERENCE_NAME
)

class DesertReleaseApplication : Application(){
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        // initialization of our dataStore repository at the moment of View creation
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }
}

