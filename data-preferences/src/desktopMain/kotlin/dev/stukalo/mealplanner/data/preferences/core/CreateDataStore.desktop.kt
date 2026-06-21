package dev.stukalo.mealplanner.data.preferences.core

import androidx.datastore.core.DataStore
import androidx.datastore.core.FileStorage
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferencesFileSerializer
import java.io.File

fun createDataStore(): DataStore<Preferences> = createDataStore(
    storage = FileStorage(
        serializer = PreferencesFileSerializer,
        produceFile = { File(System.getProperty("java.io.tmpdir"), dataStoreFileName) }
    )
)
