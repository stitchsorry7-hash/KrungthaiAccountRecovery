package com.example.accountrecovery

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AiHubConnectionsActivity : AppCompatActivity() {
    private lateinit var status: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 24, 24, 24)
        }
        root.addView(TextView(this).apply {
            text = "🔗 AI Hub / Connections"
            textSize = 28f
        })
        status = TextView(this).apply {
            text = "เลือกบริการเพื่อเชื่อมต่อแบบผู้ใช้อนุญาต — ไม่มีการคัดลอกข้อมูลลับอัตโนมัติ"
            textSize = 16f
            setPadding(0, 12, 0, 16)
        }
        root.addView(status)

        AiHubConnectionEndpoints.all.forEach { endpoint ->
            root.addView(Button(this).apply {
                text = "${endpoint.label}\n${endpoint.description}"
                setOnClickListener { connect(endpoint) }
            })
        }
        setContentView(root)
    }

    private fun connect(endpoint: AiHubConnectionEndpoint) {
        if (endpoint.id == "files") {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            }
            startActivityForResult(intent, REQUEST_TREE)
            return
        }
        val url = endpoint.authorizationUrl
        if (url != null) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            status.text = "${endpoint.label}: เปิดหน้าการอนุญาต/ตั้งค่าแล้ว — กลับมาเชื่อมต่อหลังตั้งค่าอย่างเป็นทางการ"
        } else {
            status.text = "${endpoint.label}: ใช้งานผ่าน Android/เบราว์เซอร์ โดยแอปจะไม่อ่านข้อมูลส่วนตัวแบบลับ ๆ"
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_TREE && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                val flags = data.flags and (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                try {
                    contentResolver.takePersistableUriPermission(uri, flags)
                    status.text = "📁 Files: เชื่อมต่อแล้ว — โฟลเดอร์ที่ผู้ใช้เลือกได้รับสิทธิ์ถาวรตาม Android SAF"
                } catch (_: SecurityException) {
                    status.text = "📁 Files: เลือกโฟลเดอร์แล้ว แต่ไม่สามารถบันทึกสิทธิ์ถาวรได้"
                }
            }
        }
    }

    companion object { private const val REQUEST_TREE = 7001 }
}
