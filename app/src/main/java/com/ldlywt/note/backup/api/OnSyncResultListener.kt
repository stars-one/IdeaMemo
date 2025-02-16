package com.ldlywt.note.backup.api

interface OnSyncResultListener {
    fun onSuccess(result: String?)
    fun onError(errorMsg: String?)
}