# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/work/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools-proguard.html

# Add any project specific keep rules here:

# Kotlin Serialization
-keepattributes *Annotation*, EnclosingMethod, Signature
-keep,allowobfuscation,allowshrinking class kotlinx.serialization.json.** { *; }
-keepclassmembers class * {
    @kotlinx.serialization.SerialName <fields>;
}

# Ktor
-keep class io.ktor.** { *; }
-dontwarn java.lang.management.ManagementFactory
-dontwarn java.lang.management.RuntimeMXBean

# Koin
-keep class io.insertkoin.** { *; }

# Room
-keep class * extends androidx.room.RoomDatabase
-keep class * extends androidx.room.Entity
-keep class * extends androidx.room.Dao

# Compose
-keepclassmembers class androidx.compose.runtime.Recomposer {
    private void readObject(java.io.ObjectInputStream);
    private void writeObject(java.io.ObjectOutputStream);
}
-keep class androidx.compose.runtime.ComposerImpl { *; }
-keep class androidx.compose.runtime.CompositionImpl { *; }

# Keep data models
-keep class dev.stukalo.mealplanner.domain.model.** { *; }
-keep class dev.stukalo.mealplanner.data.network.** { *; }
-keep class dev.stukalo.mealplanner.data.database.** { *; }
