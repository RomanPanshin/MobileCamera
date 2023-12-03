package com.example.third_5.ui.theme.ui.theme.ui.theme

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.third_5.R
import kotlinx.coroutines.coroutineScope
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CameraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        val button = findViewById<Button>(R.id.photoButton)
        button.setOnClickListener {
            if (checkCameraPermission()) {
                openCamera()
            } else {
                requestCameraPermission()
            }
        }
    }

     fun savePhoto() {
//        coroutineScope {
            try {
                val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                val date = sdf.format(Date())

                val path = filesDir
                val letDirectory = File(path, "Photos")
                letDirectory.mkdirs()
                val photoFile = File(letDirectory, "$date.txt")
//                FileOutputStream(photoFile).use {
//                    it.write(data)
//                }
                val file = File(path, "date.txt")
                var text = "$date;"
                file.appendText(text)
            } catch (e: Exception) {
                e.printStackTrace()
            }
//        }
    }

    private fun saveTimestamp() {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val file = File(getExternalFilesDir(null), "photos/date.txt")
        if (!file.parentFile.exists()) file.parentFile.mkdirs()
        FileOutputStream(file, true).bufferedWriter().use { writer ->
            writer.write("$timestamp\n")
        }
        Log.d("CameraActivity", "Timestamp saved: $timestamp")
    }


    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CAMERA_PERMISSION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    savePhoto();
                    openCamera()
                } else {
                    // Handle the case where the user denies the permission.
                }
            }
        }
    }

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 101
        private const val REQUEST_IMAGE_CAPTURE = 1
    }
}
