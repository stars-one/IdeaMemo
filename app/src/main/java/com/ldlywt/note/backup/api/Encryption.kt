package com.ldlywt.note.backup.api

interface  Encryption {
    //加密
    fun encode(key: String?): String?

    //解密
    fun decode(password: String?): String?
}