package com.example.composeapp

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

object ImageOperations {
    @JvmStatic
    @OptIn(DelicateCoroutinesApi::class)
    suspend fun loadImage(link: String): Bitmap? {
        val image = CompletableDeferred<Bitmap?>()

        GlobalScope.launch(Dispatchers.IO) {
            val url = URL(link)
            val connection: HttpURLConnection?
            try {
                connection = url.openConnection() as HttpURLConnection
                connection.connect()
                val inputStream: InputStream = connection.inputStream
                val bufferedInputStream = BufferedInputStream(inputStream)

                image.complete(BitmapFactory.decodeStream(bufferedInputStream))
            } catch (e: IOException) {
                e.printStackTrace()
                image.complete(null)
            }
        }

        return image.await()
    }

    @JvmStatic
    fun savePhoto(context: Context, filename: String, bitmap: Bitmap) {
        var fos: OutputStream? = null

        context.contentResolver?.also { resolver ->
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }

            val imageUri: Uri? = resolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )

            fos = imageUri?.let { resolver.openOutputStream(it) }
        }

        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, it) // Write to stream
        }
    }
}
