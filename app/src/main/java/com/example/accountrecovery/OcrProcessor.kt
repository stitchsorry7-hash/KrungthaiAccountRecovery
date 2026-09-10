package com.example.accountrecovery

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class OcrProcessor(private val context: Context) {
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    fun process(bitmap: Bitmap): String = run(InputImage.fromBitmap(bitmap, 0))
    fun process(uri: Uri): String = run(InputImage.fromFilePath(context, uri))
    private fun run(image: InputImage): String {
        var out = ""; var err: Exception? = null; val latch = CountDownLatch(1)
        recognizer.process(image).addOnSuccessListener { out = it.text; latch.countDown() }
            .addOnFailureListener { err = it as? Exception ?: RuntimeException(it); latch.countDown() }
        if (!latch.await(60, TimeUnit.SECONDS)) throw RuntimeException("OCR timeout")
        err?.let { throw it }; return out
    }
}
