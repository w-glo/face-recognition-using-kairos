package com.wajahat.facerecognition

import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import io.ktor.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var file: File
    private lateinit var uri: Uri
    private lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        file = File(getExternalFilesDir(null), "picFromCamera")
        uri = FileProvider.getUriForFile(this, applicationContext.packageName + ".provider", file)
        findViewById<Button>(R.id.button).setOnClickListener {
            takePicture.launch(uri)
        }
        findViewById<Button>(R.id.upload).setOnClickListener {
            recognize()
        }
//        recognize()
    }

    private fun enroll() {
        CoroutineScope(Dispatchers.IO).launch {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
            val encoded = Base64.encodeToString(byteArray, Base64.DEFAULT)

            Timber.d("encoded: %s", encoded)

            val movieApi = KairosApi()
            val response = movieApi.enroll(
                encoded,
                "Levitete",
                "Wajahat"
            )
            Timber.d("response: %s", response.face_id)
        }
    }

    @KtorExperimentalAPI
    private fun recognize() {
        CoroutineScope(Dispatchers.IO).launch {
            val movieApi = KairosApi()
            Timber.d("recognize()...")


            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
            val encoded = Base64.encodeToString(byteArray, Base64.DEFAULT)

            val response = movieApi.recognize(
//                "https://media-exp1.licdn.com/dms/image/C5103AQGaVyisNF1UjQ/profile-displayphoto-shrink_200_200/0/1587210924414?e=1630540800&v=beta&t=dmKQq-xNBtphMTaq8Ua7IID3yI6Bk23QKNaMvUd8geE",
                encoded,
                "Levitete"
            )
            Timber.d("response: %s", response.images.toString())
//            withContext(Dispatchers.Main) {
//            }
        }
    }

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
            if (success) {
                // The image was saved into the given Uri -> do something with it
                bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                Log.d("MainActivity", "uri.toString(): $uri")
                findViewById<ImageView>(R.id.image).setImageBitmap(
                    bitmap
                )
            }
        }

    private fun handleOrientation(photoPath: String, bitmap: Bitmap): Bitmap {
        val ei = ExifInterface(photoPath)
        val orientation: Int = ei.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )

        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270)
            ExifInterface.ORIENTATION_NORMAL -> bitmap
            else -> bitmap
        }
    }

    private fun rotateImage(source: Bitmap, angle: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle.toFloat())
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }
}