package com.example.mobileprogrammingpracticals

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

// Define the DataStore extension in a common place
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")
