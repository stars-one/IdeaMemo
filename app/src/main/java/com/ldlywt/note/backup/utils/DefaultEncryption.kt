package com.ldlywt.note.backup.utils

import com.ldlywt.note.backup.api.Encryption
import javax.inject.Inject


class DefaultEncryption @Inject constructor() : Encryption {

    override fun encode(key: String?): String? {
        return Base64Util.encodeToString(key)
    }

    override fun decode(password: String?): String? {
        return Base64Util.decodeToString(password)
    }
}