package com.pictionary.todoapp

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.pictionary.todoapp.UtilsService.SharedPreferenceClass

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    lateinit var logout: Button
    lateinit var sharedPreferenceClass: SharedPreferenceClass
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var drawerToggle: ActionBarDrawerToggle
    lateinit var navigationView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setViews()
        sharedPreferenceClass = SharedPreferenceClass(this)
    }

    private fun setViews() {
        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        navigationView = findViewById<NavigationView>(R.id.navigationView)
        toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        navigationView.setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                setDrawerClick(item.itemId)
                item.setChecked(true)
                drawerLayout.closeDrawers()
                return true
            }
        })

        initDrawer()
    }

    private fun initDrawer() {
        val manager = supportFragmentManager
        val ft: FragmentTransaction = manager.beginTransaction()
        ft.replace(R.id.content, HomeFragment())
        ft.commit()
        drawerToggle = object : ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close){
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }
        }

        drawerToggle.drawerArrowDrawable.color = resources.getColor(R.color.white)
        drawerLayout.addDrawerListener(drawerToggle)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }

    private fun setDrawerClick(itemId: Int) {
        when(itemId){
            R.id.action_finished_task -> {
                supportFragmentManager.beginTransaction().replace(R.id.content, FinishedTask()).commit()
            }
            R.id.action_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.content, HomeFragment()).commit()
            }
            R.id.action_logout -> {
                sharedPreferenceClass.clearData()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }
        }
    }

}