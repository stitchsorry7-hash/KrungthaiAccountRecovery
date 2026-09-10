package com.example.accountrecovery

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var status: TextView; private lateinit var results: LinearLayout
    private val detector = AccountNumberDetector(); private lateinit var ocr: OcrProcessor; private lateinit var secure: SecureStore
    private val pick = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri -> uri?.let { analyzeUri(it) } }
    private val permission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted -> if (granted) camera.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE)) else toast("ต้องอนุญาตกล้องเพื่อสแกน") }
    private val camera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { r -> if (r.resultCode == Activity.RESULT_OK) (r.data?.extras?.get("data") as? Bitmap)?.let { analyzeBitmap(it) } }

    override fun onCreate(b: Bundle?) { super.onCreate(b); setContentView(R.layout.activity_main); ocr = OcrProcessor(this); secure = SecureStore(this)
        status=findViewById(R.id.status); results=findViewById(R.id.results)
        findViewById<Button>(R.id.selectFile).setOnClickListener { pick.launch(arrayOf("image/*", "application/pdf")) }
        findViewById<Button>(R.id.camera).setOnClickListener { if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED) camera.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE)) else permission.launch(Manifest.permission.CAMERA) }
        findViewById<Button>(R.id.clear).setOnClickListener { results.removeAllViews(); status.text="เลือกหลักฐานเพื่อเริ่มค้นหา" }
        secure.readConfirmed()?.let { status.text="มีเลขบัญชีที่ยืนยันไว้: ${mask(it)}" }
    }
    private fun analyzeUri(uri: android.net.Uri) { status.text="กำลัง OCR..."; thread { try { val mime=contentResolver.getType(uri); val text=if (mime=="application/pdf" || uri.toString().lowercase().contains(".pdf")) PdfOcrProcessor(this, ocr).process(uri) else ocr.process(uri); show(text, uri.toString()) } catch(e:Exception){ runOnUiThread{status.text="อ่านไฟล์ไม่สำเร็จ: ${e.message}"} } } }
    private fun analyzeBitmap(bitmap: Bitmap) { status.text="กำลัง OCR..."; thread { try { show(ocr.process(bitmap), "Camera") } catch(e:Exception){ runOnUiThread{status.text="OCR ไม่สำเร็จ: ${e.message}"} } } }
    private fun show(text:String, source:String) { val list=detector.detect(text, source); runOnUiThread { results.removeAllViews(); status.text=if(list.isEmpty()) "ไม่พบ Candidate 10 หลัก — กรุณาลองภาพที่คมชัดขึ้น" else "พบ ${list.size} Candidate — ตรวจหลักฐานก่อนยืนยัน"; list.forEach { addCandidate(it) } } }
    private fun addCandidate(c:AccountCandidate) { val box=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL; setPadding(0,18,0,18)}; val title=TextView(this).apply{text="${c.number}   คะแนน ${c.score}/100"; textSize=20f}; val ev=TextView(this).apply{text="หลักฐาน: ${c.evidence}"}; val btn=Button(this).apply{text="ยืนยันเลขนี้"; setOnClickListener{ secure.saveConfirmed(c.number); status.text="ยืนยันแล้ว: ${mask(c.number)}"; toast("บันทึกแบบเข้ารหัสในเครื่องแล้ว")} }; box.addView(title);box.addView(ev);box.addView(btn);results.addView(box) }
    private fun mask(n:String)="••••••${n.takeLast(4)}"; private fun toast(s:String)=Toast.makeText(this,s,Toast.LENGTH_LONG).show()
}
