package com.ldlywt.note.backup

import android.content.Context
import android.util.Log
import com.ldlywt.note.R
import com.ldlywt.note.backup.api.OnSyncResultListener
import com.ldlywt.note.backup.model.DavData
import com.ldlywt.note.preferences
import com.ldlywt.note.utils.toast
import com.thegrizzlylabs.sardineandroid.DavResource
import com.thegrizzlylabs.sardineandroid.impl.OkHttpSardine
import com.thegrizzlylabs.sardineandroid.impl.SardineException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

class SyncManager(
    private val context: Context,
) {

    suspend fun uploadFile(fileName: String?, fileDir: String, localFile: File?): String = withContext(Dispatchers.IO) {
        val sardine = OkHttpSardine()
        sardine.setCredentials(preferences.davUserName, preferences.davPassword, true)
        try {
            if (!sardine.exists(preferences.davServerUrl + fileDir)) {
                //若不存在需要创建目录
                sardine.createDirectory(preferences.davServerUrl + fileDir)
            }
            val url = preferences.davServerUrl + fileDir + "/" + fileName
            if (sardine.exists(url)) {
                sardine.delete(url)
            }
            sardine.put(url, localFile, "application/x-www-form-urlencoded")
            "Success：$fileDir/$fileName"
        } catch (e: IOException) {
            e.printStackTrace()
            e.message.toString()
        }
    }

    suspend fun checkConnection(url: String, account: String, pwd: String): Pair<Boolean, String> = withContext(Dispatchers.IO) {
        val sardine = OkHttpSardine()
        sardine.setCredentials(account, pwd, true)
        return@withContext try {
            sardine.exists(url)
            preferences.davServerUrl = url
            preferences.davUserName = account
            preferences.davPassword = pwd
            preferences.davLoginSuccess = true
            Pair(true, context.getString(R.string.webdav_config_success))
        } catch (e: SardineException) {
            e.printStackTrace()
            preferences.clearDavConfig()
            Pair(false, e.message.toString())
        }
    }

    fun uploadString(fileName: String?, fileLoc: String?, content: String?, listener: OnSyncResultListener?) {
        val sardine = OkHttpSardine()
        val T = Thread {
            sardine.setCredentials(preferences.davUserName, preferences.davPassword, true)
            try {
                if (!sardine.exists(preferences.davServerUrl + fileLoc + "/")) {
                    //若不存在需要创建目录
                    sardine.createDirectory(preferences.davServerUrl + fileLoc + "/")
                }
                val data = content!!.toByteArray()
                sardine.put(preferences.davServerUrl + fileLoc + "/" + fileName, data)
                listener!!.onSuccess("$fileLoc/$fileName,上传成功")
            } catch (e: IOException) {
                e.printStackTrace()
                listener!!.onError("出错了$e")
            }
        }
        T.start()
    }

    fun downloadFileByPath(webPath: String, localDir: String): String? {
        return runCatching {
            val sardine = OkHttpSardine()
            sardine.setCredentials(preferences.davUserName, preferences.davPassword, true)
            val fileName = webPath.substringAfterLast("/")
            val localPath = File(localDir, fileName).path
            Log.i("wutao", "downloadFileByPath: " + preferences.davServerUrl + webPath)
            sardine.get(preferences.davServerUrl + webPath).use { inputStream ->
                FileOutputStream(localPath).use { outputStream ->
                    val buffer = ByteArray(1024)
                    var bytesRead: Int
                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                        outputStream.write(buffer, 0, bytesRead)
                        outputStream.flush()
                    }
                }
            }
            localPath
        }.getOrElse {
            toast(it.message.toString())
            it.printStackTrace()
            null
        }
    }


    fun downloadString(fileName: String?, fileLoc: String?, listener: OnSyncResultListener?) {
        val T = Thread {
            val sardine = OkHttpSardine()
            sardine.setCredentials(preferences.davUserName, preferences.davPassword, true)
            val inputStream: InputStream
            try {
                inputStream = sardine[preferences.davServerUrl + fileLoc + "/" + fileName]
                //设置输入缓冲区
                val reader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8)) // 实例化输入流，并获取网页代
                var s: String? // 依次循环，至到读的值为空
                val sb = StringBuilder()
                while ((reader.readLine().also { s = it }) != null) {
                    sb.append(s)
                }
                reader.close()
                inputStream.close()
                val str = sb.toString()
                listener!!.onSuccess(str)
            } catch (e: IOException) {
                e.printStackTrace()
                listener!!.onError("出错了,$e")
            }
        }
        T.start()
    }

    fun listAllFile(dir: String?): List<DavData?> {
        val sardine = OkHttpSardine()
        sardine.setCredentials(preferences.davUserName, preferences.davPassword, true)
        return try {
            val resources = sardine.list(preferences.davServerUrl + dir) //如果是目录一定别忘记在后面加上一个斜杠
            val davData: MutableList<DavData> = ArrayList()
            for (i: DavResource in resources) {
                davData.add(DavData(i))
            }
            davData
        } catch (e: Exception) {
            e.printStackTrace()
            toast(e.message.toString())
            emptyList()
        }
    }

    fun deleteFile(fileDir: String?, listener: OnSyncResultListener?) {
        val sardine = OkHttpSardine()
        val T = Thread {
            sardine.setCredentials(preferences.davUserName, preferences.davPassword, true)
            try {
                sardine.delete(preferences.davServerUrl + fileDir)
                listener!!.onSuccess("删除成功！")
            } catch (e: IOException) {
                e.printStackTrace()
                listener!!.onError("出错了,$e")
            }
        }
        T.start()
    }
}