package com.ldlywt.note.backup

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class BackupScheduler {
    companion object {
        private const val BACKUP_WORK_TAG = "backup_work"

        fun scheduleDailyBackup(context: Context) {
            // 创建 PeriodicWorkRequest，间隔时间为 7 天
            val workRequest = PeriodicWorkRequestBuilder<BackupWorker>(3, TimeUnit.DAYS)
                .addTag(BACKUP_WORK_TAG)
                .build()

            // 将任务添加到 WorkManager，并设定唯一的标签
            val workManager = WorkManager.getInstance(context)
            workManager.enqueueUniquePeriodicWork(
                BACKUP_WORK_TAG,
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
        }

        fun cancelDailyBackup(context: Context) {
            // 取消已经调度的任务
            val workManager = WorkManager.getInstance(context)
            workManager.cancelUniqueWork(BACKUP_WORK_TAG)
        }
    }
}
