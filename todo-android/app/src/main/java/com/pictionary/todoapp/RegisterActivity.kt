package com.pictionary.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class RegisterActivity : AppCompatActivity() {
    lateinit var loginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        loginBtn = findViewById(R.id.loginBtn)
        loginBtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                val intent: Intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        })
    }
}