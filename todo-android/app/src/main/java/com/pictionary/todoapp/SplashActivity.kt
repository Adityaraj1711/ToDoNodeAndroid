package com.pictionary.todoapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import java.lang.Exception
import java.lang.Thread.sleep

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // it will change splash screen to full screen
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        val thread: Thread = Thread {
            try {
                sleep(3000)
                Log.d("Splash", "activity start")
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            } catch (exception: Exception) {
                Log.d("Splash", exception.toString())
            }
        }
        thread.start()
    }
}