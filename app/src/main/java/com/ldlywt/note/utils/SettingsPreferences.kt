package com.ldlywt.note.utils

import androidx.annotation.StringRes
import androidx.core.content.edit
import com.ldlywt.note.App
import com.ldlywt.note.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object SettingsPreferences {
    enum class ThemeMode(@StringRes val resId: Int) {
        LIGHT(R.string.light_mode), DARK(R.string.dark_mode), SYSTEM(R.string.use_device_theme),
    }

    private val preferences = App.instance.preferences

    private val _themeMode = MutableStateFlow(preferences.getEnum(KEY_THEME_MODE, ThemeMode.SYSTEM))
    val themeMode = _themeMode.asStateFlow()

    private val _dynamicColor = MutableStateFlow(preferences.getBoolean(KEY_DYNAMIC_COLOR, false))
    val dynamicColor = _dynamicColor.asStateFlow()


    fun changeThemeMode(themeMode: ThemeMode) {
        _themeMode.value = themeMode
        preferences.edit { putEnum(KEY_THEME_MODE, themeMode) }
    }


    fun changeDynamicColor(dynamicTheme: Boolean) {
        _dynamicColor.value = dynamicTheme
        preferences.edit { putBoolean(KEY_DYNAMIC_COLOR, dynamicTheme) }
    }

    fun changeFirstLaunch(isFirst : Boolean){
        preferences.edit { putBoolean(KEY_FIRST_LAUNCH, isFirst) }
    }

    fun getFirstLaunch():Boolean{
        return preferences.getBoolean(KEY_FIRST_LAUNCH, true)
    }
}