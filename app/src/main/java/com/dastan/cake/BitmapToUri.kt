package com.dastan.cake

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.core.content.FileProvider
import java.io.File


fun imageBitmapToBitmap(imageBitmap: ImageBitmap): Bitmap {
    return imageBitmap.asAndroidBitmap()
}

fun bitmapToFile(context: Context, bitmap: Bitmap): File {
    val file = File(context.cacheDir, "${System.currentTimeMillis()}.png")
    file.outputStream().use { out ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
    }
    return file
}
fun fileToUri(context: Context, file: File): Uri? {
    try {
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        Log.d("dasattn check", uri.toString())
        return uri
    } catch (e: Exception) {
        Log.e("dasattn check", "Error getting URI", e)
        return null
    }
}