package com.pictionary.todoapp.UtilsService

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception

class UtilService{
    public fun hideKeyboard(view: View, activity: Activity){
        try {
            var inputMethodManager: InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (exception: Exception){
            exception.printStackTrace()
        }
    }

    public fun showSnackBar(view: View, msg: String){
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show()
    }

}