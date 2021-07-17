package com.samandroid.SelfieApp.activities

import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.samandroid.SelfieApp.R
import com.samandroid.SelfieApp.adapters.ImageListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2
import java.io.File

class MainActivity : AppCompatActivity() {
    var mList: ArrayList<StorageReference> = ArrayList()
    var mImageListAdapter: ImageListAdapter? = null
    val storage = FirebaseStorage.getInstance().reference
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        var email = "7thelement@gmail.com"
        var password = "Hahaha"

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(object: OnCompleteListener<AuthResult> {
                override fun onComplete(task: Task<AuthResult>) {
                    if(task.isSuccessful){
                        Log.d("abc", "successful login authentication")
                    }
                    else{
                        Toast.makeText(applicationContext, "Login Failed", Toast.LENGTH_SHORT).show()
                        Log.d("abc", "login authentication FAILED")
                    }
                }

            })

        init()
    }

    private fun init() {
        generateData()

        mImageListAdapter = ImageListAdapter(this, mList)
        recycler_view.adapter = mImageListAdapter
        recycler_view.layoutManager = LinearLayoutManager(this)

        button_add_task.setOnClickListener {
            startActivity(Intent(this, UploadActivity::class.java))
        }
    }

    fun generateData() {
        val storage = Firebase.storage
        val listRef = storage.reference.child("images")

// You'll need to import com.google.firebase.storage.ktx.component1 and
// com.google.firebase.storage.ktx.component2
        listRef.listAll()
            .addOnSuccessListener { (items, prefixes) ->
                prefixes.forEach { prefix ->
                    // All the prefixes under listRef.
                    // You may call listAll() recursively on them.
                }

                items.forEach { item ->
                    // All the items under listRef.
                    mList.add(item)
                }
                mImageListAdapter?.setData(mList)
            }
            .addOnFailureListener {
                // Uh-oh, an error occurred!
            }
    }

    override fun onRestart() {
        super.onRestart()
        mList = ArrayList()
        init()
    }
}