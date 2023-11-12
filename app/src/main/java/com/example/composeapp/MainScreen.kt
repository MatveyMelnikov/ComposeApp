package com.example.composeapp

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(context: Context) {
    var text = "https://osdaily.ru/wp-content/uploads/2016/11/probnaya_str_02-645x403.jpg"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            label = { Text("Image Link") }
        )
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                loadAndSaveImage(context, text)
            }
        ) {
            Text(
                text = "Load and save",
                color = Color.White
            )
        }
        Spacer(modifier = Modifier
            .fillMaxHeight(0.1f))
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                val constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
                val data = Data.Builder()
                data.putString("link", text)

                val imageWorkerRequest = OneTimeWorkRequestBuilder<LoadAndSaveImageWorker>()
                    .setConstraints(constraints)
                    .setInitialDelay(10, TimeUnit.SECONDS)
                    .setInputData(data.build())
                    .build()
                WorkManager.getInstance(context).enqueue(imageWorkerRequest)
            }
        ) {
            Text(
                text = "Load and save in worker",
                color = Color.White
            )
        }
    }
}

//@OptIn(DelicateCoroutinesApi::class)
//suspend fun loadImage(link: String) : Bitmap?
//{
//    val image = CompletableDeferred<Bitmap?>()
//
//    GlobalScope.launch(Dispatchers.IO) {
//        val url = URL(link)
//        val connection: HttpURLConnection?
//        try {
//            connection = url.openConnection() as HttpURLConnection
//            connection.connect()
//            val inputStream: InputStream = connection.inputStream
//            val bufferedInputStream = BufferedInputStream(inputStream)
//
//            image.complete(BitmapFactory.decodeStream(bufferedInputStream))
//        } catch (e: IOException) {
//            e.printStackTrace()
//            image.complete(null)
//        }
//    }
//
//    return image.await()
//}
//
//fun savePhoto(context: Context, filename: String, bitmap: Bitmap)
//{
//    var fos: OutputStream? = null
//
//    context.contentResolver?.also { resolver ->
//        val contentValues = ContentValues().apply {
//            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
//            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
//            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
//        }
//
//        val imageUri: Uri? = resolver.insert(
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//            contentValues
//        )
//
//        fos = imageUri?.let { resolver.openOutputStream(it) }
//    }
//
//    fos?.use {
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, it) // Write to stream
//    }
//}

@OptIn(DelicateCoroutinesApi::class)
fun loadAndSaveImage(context: Context, link: String)
{
    GlobalScope.launch {
        //val bitmap = loadImage(link) ?: return@launch
        val bitmap = ImageOperations.loadImage(link) ?: return@launch
        val filename = "${System.currentTimeMillis()}.jpg"
        ImageOperations.savePhoto(context, filename, bitmap)
    }
}