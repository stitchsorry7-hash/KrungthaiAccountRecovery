package com.example.accountrecovery

import android.content.Context
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor

class PdfOcrProcessor(private val context: Context, private val ocr: OcrProcessor) {
    fun process(uri: Uri): String {
        val pfd: ParcelFileDescriptor = context.contentResolver.openFileDescriptor(uri, "r")
            ?: throw IllegalArgumentException("เปิด PDF ไม่ได้")
        pfd.use { descriptor ->
            PdfRenderer(descriptor).use { renderer ->
                val out = StringBuilder()
                for (i in 0 until renderer.pageCount) {
                    renderer.openPage(i).use { page ->
                        val bitmap = android.graphics.Bitmap.createBitmap(page.width * 2, page.height * 2, android.graphics.Bitmap.Config.ARGB_8888)
                        bitmap.eraseColor(android.graphics.Color.WHITE)
                        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                        out.append(ocr.process(bitmap)).append('\n')
                        bitmap.recycle()
                    }
                }
                return out.toString()
            }
        }
    }
}
