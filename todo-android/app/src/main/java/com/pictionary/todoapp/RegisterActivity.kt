package com.pictionary.todoapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.pictionary.todoapp.UtilsService.SharedPreferenceClass
import com.pictionary.todoapp.UtilsService.UtilService
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException


class RegisterActivity : AppCompatActivity() {
    lateinit var loginBtn: Button
    lateinit var registerBtn: Button
    lateinit var name_ET: EditText
    lateinit var email_ET: EditText
    lateinit var password_ET: EditText
    lateinit var progressBar: ProgressBar
    lateinit var name: String
    lateinit var email: String
    lateinit var password: String
    lateinit var utilService: UtilService
    lateinit var sharedPreferenceClass: SharedPreferenceClass
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        bindViews()

        setClickListener()
    }

    private fun setClickListener() {
        loginBtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                val intent: Intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        })
        registerBtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View?) {
                if (view != null) {
                    utilService.hideKeyboard(view, this@RegisterActivity)
                }
                name = name_ET.text.toString()
                email = email_ET.text.toString()
                password = password_ET.text.toString()
                if(validate(view)){
                    registerUser(view)
                }
            }
        })
    }

    private fun registerUser(view: View?) {
        progressBar.visibility = View.VISIBLE
        val params: HashMap<String, String> = HashMap()
        params["username"] = name
        params["email"] = email
        params["password"] = password
        val apiKey: String = "https://todoappnodeandroid.herokuapp.com/api/todo/auth/register"
        val jsonObjectRequest: JsonObjectRequest =
            object : JsonObjectRequest(Method.POST, apiKey, JSONObject(params as Map<*, *>),
                Response.Listener { response ->
                    try {
                        if (response.getBoolean("success")) {
                            val token = response.getString("token")
                            sharedPreferenceClass.setValue_string("token", token)
                            Toast.makeText(this@RegisterActivity, token, Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                        }
                        progressBar.visibility = View.GONE
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        progressBar.visibility = View.GONE
                    }
                },
                Response.ErrorListener { error ->
                    val response = error.networkResponse
                    if (error is ServerError && response != null) {
                        try {
                            val res: String = String(response.data, charset(HttpHeaderParser.parseCharset(response.headers, "utf-8")))
                            val obj = JSONObject(res)
                            Toast.makeText(this@RegisterActivity, obj.getString("msg"), Toast.LENGTH_SHORT).show()
                            progressBar.visibility = View.GONE
                        } catch (je: JSONException) {
                            je.printStackTrace()
                            progressBar.visibility = View.GONE
                        } catch (je: UnsupportedEncodingException) {
                            je.printStackTrace()
                            progressBar.visibility = View.GONE
                        }
                    }
                }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers: HashMap<String, String> = HashMap()
                    headers["Content-Type"] = "application/json"
                    return params
                }
            }

        // set retry policy
        val socketTime = 3000
        val policy: RetryPolicy = DefaultRetryPolicy(
            socketTime,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        jsonObjectRequest.retryPolicy = policy

        // request add
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonObjectRequest)
    }


    private fun bindViews() {
        loginBtn = findViewById(R.id.loginBtn)
        name_ET = findViewById(R.id.name_ET)
        email_ET = findViewById(R.id.email_ET)
        password_ET = findViewById(R.id.password_ET)
        registerBtn = findViewById(R.id.registerBtn)
        progressBar = findViewById(R.id.progress_bar)
        utilService = UtilService()
        sharedPreferenceClass = SharedPreferenceClass(this)
    }

    fun validate(view: View?): Boolean{
        var isValid: Boolean
        if(!TextUtils.isEmpty(name)){
            if(!TextUtils.isEmpty(email)){
                if(!TextUtils.isEmpty(password)){
                    isValid = true
                } else {
                    utilService.showSnackBar(view!!, "please enter password ..")
                    isValid = false
                }
            } else {
                utilService.showSnackBar(view!!, "please enter email ..")
                isValid = false
            }
        } else {
            utilService.showSnackBar(view!!, "please enter name ...")
            isValid = false
        }
        return isValid
    }

    override fun onStart() {
        super.onStart()
        val sharedPreferences =
            getSharedPreferences("user_todo", Context.MODE_PRIVATE)
        if (sharedPreferences.contains("token")) {
            startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
            finish()
        }
    }
}