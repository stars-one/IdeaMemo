package com.ldlywt.note.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.ldlywt.note.ui.page.SortTime
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class SharedPreferencesUtils(context: Context) {

    private val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    var sortTime by SharedPreferenceDelegates.string(defaultValue = SortTime.UPDATE_TIME_DESC.name)
    var useSafe by SharedPreferenceDelegates.boolean(false)
    var localAutoBackup by SharedPreferenceDelegates.boolean(false)

    // content://com.android.externalstorage.documents/tree/primary%3ADocuments
    var localBackupUri by SharedPreferenceDelegates.string(null)

    var davLoginSuccess by SharedPreferenceDelegates.boolean(false)
    var davServerUrl by SharedPreferenceDelegates.string("https://dav.jianguoyun.com/dav/")
    var davUserName by SharedPreferenceDelegates.string()
    var davPassword by SharedPreferenceDelegates.string()

    fun clearDavConfig() {
        davLoginSuccess = false
        davServerUrl = null
        davUserName = null
        davPassword = null
    }

    private object SharedPreferenceDelegates {

        fun int(defaultValue: Int = 0) = object : ReadWriteProperty<SharedPreferencesUtils, Int> {

            override fun getValue(thisRef: SharedPreferencesUtils, property: KProperty<*>): Int {
                return thisRef.preferences.getInt(property.name, defaultValue)
            }

            override fun setValue(thisRef: SharedPreferencesUtils, property: KProperty<*>, value: Int) {
                thisRef.preferences.edit().putInt(property.name, value).apply()
            }
        }

        fun long(defaultValue: Long = 0L) = object : ReadWriteProperty<SharedPreferencesUtils, Long> {

            override fun getValue(thisRef: SharedPreferencesUtils, property: KProperty<*>): Long {
                return thisRef.preferences.getLong(property.name, defaultValue)
            }

            override fun setValue(thisRef: SharedPreferencesUtils, property: KProperty<*>, value: Long) {
                thisRef.preferences.edit().putLong(property.name, value).apply()
            }
        }

        fun boolean(defaultValue: Boolean = false) = object : ReadWriteProperty<SharedPreferencesUtils, Boolean> {
            override fun getValue(thisRef: SharedPreferencesUtils, property: KProperty<*>): Boolean {
                return thisRef.preferences.getBoolean(property.name, defaultValue)
            }

            override fun setValue(thisRef: SharedPreferencesUtils, property: KProperty<*>, value: Boolean) {
                thisRef.preferences.edit().putBoolean(property.name, value).apply()
            }
        }

        fun float(defaultValue: Float = 0.0f) = object : ReadWriteProperty<SharedPreferencesUtils, Float> {
            override fun getValue(thisRef: SharedPreferencesUtils, property: KProperty<*>): Float {
                return thisRef.preferences.getFloat(property.name, defaultValue)
            }

            override fun setValue(thisRef: SharedPreferencesUtils, property: KProperty<*>, value: Float) {
                thisRef.preferences.edit().putFloat(property.name, value).apply()
            }
        }

        fun string(defaultValue: String? = null) = object : ReadWriteProperty<SharedPreferencesUtils, String?> {
            override fun getValue(thisRef: SharedPreferencesUtils, property: KProperty<*>): String? {
                return thisRef.preferences.getString(property.name, defaultValue)
            }

            override fun setValue(thisRef: SharedPreferencesUtils, property: KProperty<*>, value: String?) {
                thisRef.preferences.edit().putString(property.name, value).apply()
            }
        }

        fun setString(defaultValue: Set<String>? = null) =
            object : ReadWriteProperty<SharedPreferencesUtils, Set<String>?> {
                override fun getValue(thisRef: SharedPreferencesUtils, property: KProperty<*>): Set<String>? {
                    return thisRef.preferences.getStringSet(property.name, defaultValue)
                }

                override fun setValue(thisRef: SharedPreferencesUtils, property: KProperty<*>, value: Set<String>?) {
                    thisRef.preferences.edit().putStringSet(property.name, value).apply()
                }
            }
    }
}