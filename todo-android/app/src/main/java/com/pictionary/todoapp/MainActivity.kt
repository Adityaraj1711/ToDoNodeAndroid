package com.pictionary.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.pictionary.todoapp.UtilsService.SharedPreferenceClass

class MainActivity : AppCompatActivity() {
    lateinit var logout: Button
    lateinit var sharedPreferenceClass: SharedPreferenceClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logout = findViewById(R.id.logout)
        sharedPreferenceClass = SharedPreferenceClass(this)
        logout.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                sharedPreferenceClass.clearData()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }
        })
    }
}