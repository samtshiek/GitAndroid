package com.samandroid.volleypostproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.samandroid.volleypostproject.Models.LoginResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.button_login
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.button_category
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.app_bar_orange.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.nav_header.view.*
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener,
    NavigationView.OnNavigationItemSelectedListener{
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var toolbar = my_toolbar2
        toolbar.title = "Welcome to JJ's Grocery App!"

        /*setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)*/

        init()
    }

    private fun init() {

        val sharedPreference = getSharedPreferences("my_user", Context.MODE_PRIVATE)

        if(!sharedPreference.getString("email", null).isNullOrBlank()) {
            startActivity(Intent(this, HomeActivity()::class.java))
        }

        button_login.setOnClickListener(this)
        button_register.setOnClickListener(this)
        button_category.setOnClickListener(this)


        /*drawerLayout = drawer_layout
        navView = nav_view
        var headerView = navView.getHeaderView(0)

        var sharedPreference = getSharedPreferences("my_user", Context.MODE_PRIVATE)

        val firstName = sharedPreference.getString("firstName", null)
        val email = sharedPreference.getString("email", null)

        if (!firstName.isNullOrBlank()) {
            headerView.text_view_header_name.text = firstName
            headerView.text_view_nav_header_email.text = email
        }
        else {
            headerView.text_view_header_name.text = "Active User"
            headerView.text_view_nav_header_email.text = "activeuser@gmail.com"
        }


        var toggle = ActionBarDrawerToggle(
            this, drawerLayout, my_toolbar2, 0, 0
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)*/

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            R.id.menu_cart -> startActivity(Intent(this, CartActivity::class.java))
            R.id.menu_setting -> Toast.makeText(applicationContext, "Settings", Toast.LENGTH_SHORT).show()
            R.id.menu_person -> Toast.makeText(applicationContext, "Person", Toast.LENGTH_SHORT).show()
        }
        return  true

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_account -> startActivity(Intent(this, LoginActivity::class.java))
            R.id.item_category -> startActivity(Intent(this, CategoryActivity::class.java))
            R.id.item_logout -> {
                var sharedPreference = getSharedPreferences("my_user", Context.MODE_PRIVATE)
                var edit = sharedPreference.edit()
                edit.clear()
                edit.commit()
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return  true
    }


    override fun onClick(v: View?) {
        when(v) {
            button_category -> startActivity(Intent(this, CategoryActivity()::class.java))
            button_register -> startActivity(Intent(this, RegisterActivity()::class.java))
            button_login -> {

                val sharedPreference = getSharedPreferences("my_user", Context.MODE_PRIVATE)

                if(sharedPreference.getString("email", null).isNullOrBlank()) {
                    startActivity((Intent(this, LoginActivity()::class.java)))
                }
                else {

                    startActivity(Intent(this, HomeActivity()::class.java))
                }

                }
        }
    }

    override fun onRestart() {
        super.onRestart()
        init()
    }


}