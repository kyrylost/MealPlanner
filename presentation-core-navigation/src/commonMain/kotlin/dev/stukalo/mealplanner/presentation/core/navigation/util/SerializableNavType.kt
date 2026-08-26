package dev.stukalo.mealplanner.presentation.core.navigation.util

import androidx.navigation.NavType
import androidx.savedstate.SavedState
import androidx.savedstate.read
import androidx.savedstate.write
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

/**
 * A custom [NavType] for [kotlinx.serialization.Serializable] objects.
 * This allows passing complex objects as navigation arguments.
 *
 * @param T The type of the object.
 * @param serializer The serializer for type [T].
 * @param isNullable Whether the type is nullable.
 */
class SerializableNavType<T : Any>(private val serializer: KSerializer<T>, isNullable: Boolean = false) :
    NavType<T?>(isNullable) {
    override fun get(bundle: SavedState, key: String): T? = bundle.read { getString(key) }?.let {
        if (it == "null") null else Json.decodeFromString(serializer, it)
    }

    override fun parseValue(value: String): T? = if (value == "null") null else Json.decodeFromString(serializer, value)

    override fun put(bundle: SavedState, key: String, value: T?) {
        bundle.write {
            if (value == null) {
                putString(key, "null")
            } else {
                putString(key, Json.encodeToString(serializer, value))
            }
        }
    }

    override fun serializeAsValue(value: T?): String =
        if (value == null) "null" else Json.encodeToString(serializer, value)
}
