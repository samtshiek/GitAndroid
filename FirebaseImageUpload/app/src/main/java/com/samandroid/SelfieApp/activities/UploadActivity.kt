package com.samandroid.SelfieApp.activities

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.samandroid.SelfieApp.R
import kotlinx.android.synthetic.main.activity_upload.*
import java.io.File

class UploadActivity : AppCompatActivity(), View.OnClickListener {
    val FILE_NAME = "camera_photo"
    val REQUEST_CAMERA_CODE = 100
    val REQUEST_GALLERY_CODE = 101
    val PERMISSION_CODE = 1001

    private var mUri: Uri? = null
    lateinit var filePhoto: File
    lateinit var uriGallery: Uri
    var photoOrGallery = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        init()
    }

    private fun init() {
        button_camera.setOnClickListener(this)
        button_upload.setOnClickListener(this)
        button_galery.setOnClickListener(this)

    }

    private fun checkSinglePermission() {
        var permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // we don't have the permission for camera and hence we have to request
            requestCameraPermission()
        } else {
            // we have the permission
            openCamera()
        }
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_CAMERA_CODE
        )
    }

    private fun openGallery(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, PERMISSION_CODE)
            } else{
                chooseImageGallery();
            }
        }else{
            chooseImageGallery();
        }
    }

    private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    chooseImageGallery()
                }else{
                    Toast.makeText(this,"Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getPhotoFile(fileName: String): File {
        val directoryStorage = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", directoryStorage)
    }

    fun openCamera() {
        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        filePhoto = getPhotoFile(FILE_NAME)
        val providerFile =FileProvider.getUriForFile(this,"com.samandroid.SelfieApp", filePhoto)
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerFile)

        startActivityForResult(takePhotoIntent, REQUEST_CAMERA_CODE)

    }

    override fun onClick(v: View?) {
        when(v) {
            button_camera -> {
                checkSinglePermission()
            }
            button_galery -> openGallery()
            button_upload -> {
                val storage = FirebaseStorage.getInstance()
                val storageRef = storage.reference
                var uploadTask: UploadTask

                if(photoOrGallery.equals("photo")) {
                    var file = Uri.fromFile(filePhoto)
                    val photoRef = storageRef.child("images/${file.lastPathSegment}")
                    uploadTask = photoRef.putFile(file)
                }
                else {
                    var file = uriGallery
                    val photoRef = storageRef.child("images/${file.lastPathSegment}")
                    uploadTask = photoRef.putFile(file)
                }

// Register observers to listen for when the download is done or if it fails
                uploadTask.addOnFailureListener {
                    Log.d("abc", "Upload failed my friend")
                    // Handle unsuccessful uploads
                }.addOnSuccessListener { taskSnapshot ->
                    Log.d("abc", "Upload sucessful!!")
                    // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                    // ...
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CAMERA_CODE && resultCode == Activity.RESULT_OK){
            photoOrGallery = "photo"
            val takenPhoto = BitmapFactory.decodeFile(filePhoto.absolutePath)
            image_view_preview.setImageBitmap(takenPhoto)
        }
        else if(requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK){
            photoOrGallery = "gallery"
            image_view_preview.setImageURI(data?.data)
            uriGallery = data?.data!!

        }
    }

}