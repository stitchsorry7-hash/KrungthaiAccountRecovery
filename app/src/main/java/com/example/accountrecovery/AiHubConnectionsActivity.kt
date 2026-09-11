package com.example.accountrecovery

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AiHubConnectionsActivity : AppCompatActivity() {
    private lateinit var status: TextView

    private val providers = listOf(
        "📁 Files" to "เลือกไฟล์/โฟลเดอร์ด้วย Android Storage Access Framework",
        "☁️ TeraBox" to "รอการเชื่อมต่อ Official API/SDK ที่ผู้ใช้อนุญาต",
        "🐙 GitHub" to "รอการ Authorize repository ที่ผู้ใช้เลือก",
        "🤖 Poe" to "รอ Sign in with Poe (OAuth + PKCE)",
        "💬 ChatGPT" to "รอการตั้งค่า Official API/OAuth",
        "✨ Gemini" to "รอการตั้งค่า Google Gemini API/OAuth"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 24, 24, 24)
        }
        val title = TextView(this).apply {
            text = "🔗 AI Hub / Connections"
            textSize = 28f
        }
        status = TextView(this).apply {
            text = "เลือกบริการเพื่อเชื่อมต่อแบบผู้ใช้อนุญาต"
            textSize = 16f
            setPadding(0, 12, 0, 16)
        }
        root.addView(title)
        root.addView(status)

        providers.forEach { (name, description) ->
            val button = Button(this).apply {
                text = "$name\n$description"
                setOnClickListener { connect(name) }
            }
            root.addView(button)
        }
        setContentView(root)
    }

    private fun connect(name: String) {
        if (name.contains("Files")) {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            }
            startActivityForResult(intent, REQUEST_TREE)
            return
        }
        status.text = "$name: ยังไม่เชื่อมต่อ — ต้องทำ OAuth/API authorization ของบริการนี้ก่อน"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_TREE && resultCode == RESULT_OK) {
            data?.data?.let { uri: Uri ->
                val flags = data.flags and (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                try { contentResolver.takePersistableUriPermission(uri, flags) } catch (_: SecurityException) { }
                status.text = "📁 Files: เชื่อมต่อแล้ว — โฟลเดอร์ที่ผู้ใช้เลือกได้รับสิทธิ์"
            }
        }
    }

    companion object { private const val REQUEST_TREE = 7001 }
}
