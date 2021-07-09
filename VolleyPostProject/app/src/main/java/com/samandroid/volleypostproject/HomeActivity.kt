package com.samandroid.volleypostproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_login.view.*

import kotlinx.android.synthetic.main.app_bar_orange.*
import kotlinx.android.synthetic.main.home_layout_content.*
import kotlinx.android.synthetic.main.nav_header.view.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var toolbar = my_toolbar2
        toolbar.title = "Home"

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        init()
    }

    private fun init() {
        val sharePreference = getSharedPreferences("my_user", Context.MODE_PRIVATE)
        val firstName = sharePreference.getString("firstName", null)
        val token = sharePreference.getString("token", null)
        val email = sharePreference.getString("email", null)
        val mobile = sharePreference.getString("mobile", null)
        val password = sharePreference.getString("password", null)

        text_view_name.text = "Welcome, $firstName"
        text_view_token.text = "TOKEN : $token "
        text_view_email.text = "Email : $email"
        text_view_mobile.text = "Mobile : $mobile"
        text_view_password.text = "Password : $password"

        button_category.setOnClickListener {
            startActivity(Intent(this, CategoryActivity()::class.java))
        }

        button_logout.setOnClickListener {
            var sharedPreference = getSharedPreferences("my_user", Context.MODE_PRIVATE)
            var edit = sharedPreference.edit()
            edit.clear()
            edit.commit()
            finish()
            startActivity(Intent(this, MainActivity()::class.java))
        }

        //for menu
        drawerLayout = drawer_layout
        navView = nav_view
        var headerView = navView.getHeaderView(0)

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
        navView.setNavigationItemSelectedListener(this)
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
            R.id.item_account -> {
                finish()
                startActivity(Intent(this, HomeActivity::class.java))
            }
            R.id.item_category -> startActivity(Intent(this, CategoryActivity::class.java))
            R.id.item_logout -> {
                var sharedPreference = getSharedPreferences("my_user", Context.MODE_PRIVATE)
                var edit = sharedPreference.edit()
                edit.clear()
                edit.commit()
                finish()
            }
            R.id.item_orders -> startActivity(Intent(this, OrderHistoryActivity::class.java))

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return  true
    }
}