package com.ldlywt.note

import android.app.Application
import com.ldlywt.note.backup.BackupScheduler
import com.ldlywt.note.utils.SharedPreferencesUtils
import dagger.hilt.android.HiltAndroidApp

fun getAppName(): String {
    return "IdeaMemo"
}

val preferences by lazy { SharedPreferencesUtils(App.instance) }

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (preferences.localAutoBackup) {
            BackupScheduler.scheduleDailyBackup(this)
        } else {
            BackupScheduler.cancelDailyBackup(this)
        }
    }

    companion object {
        lateinit var instance: App
            private set
    }
}