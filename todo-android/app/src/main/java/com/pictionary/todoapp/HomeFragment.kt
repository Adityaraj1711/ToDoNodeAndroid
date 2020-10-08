package com.pictionary.todoapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pictionary.todoapp.UtilsService.SharedPreferenceClass
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException


class HomeFragment : Fragment() {

    lateinit var floatingActionButton: FloatingActionButton
    lateinit var sharedPreferenceClass: SharedPreferenceClass
    var token: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        floatingActionButton = view.findViewById(R.id.add_task_btn)
        sharedPreferenceClass = SharedPreferenceClass(context!!)
        token = sharedPreferenceClass.getValue_string("token")
        floatingActionButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                showAlertDialog()
            }
        })

        return view
    }

    private fun showAlertDialog() {
        val inflater: LayoutInflater = layoutInflater
        val alertLayout: View = inflater.inflate(R.layout.custom_dialog_layout, null)
        val titleText : EditText = alertLayout.findViewById(R.id.title)
        val descriptionText: EditText = alertLayout.findViewById(R.id.description)

        val dialog: AlertDialog = AlertDialog.Builder(activity)
            .setView(alertLayout)
            .setTitle("Add Task")
            .setPositiveButton("Add", null)
            .setNegativeButton("Cancel", null)
            .create()
        dialog.setOnShowListener(object : DialogInterface.OnShowListener{
            override fun onShow(dialogInterface: DialogInterface) {
                val button: Button = (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
                button.setOnClickListener(object : View.OnClickListener{
                    override fun onClick(p0: View?) {
                        val title: String = titleText.text.toString()
                        val description: String = descriptionText.text.toString()
                        if(!TextUtils.isEmpty(title)){
                            addTask(title, description)
                            dialog.dismiss()
                        } else {
                            Toast.makeText(activity, "Please enter title .. ", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        })
        dialog.show()
    }

    private fun addTask(title: String, description: String) {
        val url: String = "https://todoappnodeandroid.herokuapp.com/api/todo"
        val body = HashMap<String, String>()
        body.put("title", title)
        body.put("description", description)
        val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest(
                Method.POST, url, JSONObject(body as Map<*, *>),
                Response.Listener { response ->
                    try {
                        if (response.getBoolean("success")) {
                            Toast.makeText(activity, "posted successfully", Toast.LENGTH_SHORT).show()
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    val response = error.networkResponse
                    if (error is ServerError && response != null) {
                        try {
                            val res: String = String(response.data, charset(HttpHeaderParser.parseCharset(response.headers, "utf-8")))
                            val obj = JSONObject(res)
                            Toast.makeText(activity, obj.getString("msg"), Toast.LENGTH_SHORT).show()
                        } catch (je: JSONException) {
                            je.printStackTrace()
                        } catch (je: UnsupportedEncodingException) {
                            je.printStackTrace()
                        }
                    }
                }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers: HashMap<String, String> = HashMap()
                    headers["Content-Type"] = "application/json"
                    headers["Authorization"] = token.toString()
                    return headers
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
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(jsonObjectRequest)
    }
}