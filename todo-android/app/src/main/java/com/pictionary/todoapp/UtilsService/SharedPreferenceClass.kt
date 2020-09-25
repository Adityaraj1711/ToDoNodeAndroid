package com.pictionary.todoapp.UtilsService

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

class SharedPreferenceClass(context: Context) {
    private val appSharedPref: SharedPreferences
    private val prefsEditor: Editor

    //int
    fun getValue_int(intKey: String?): Int {
        return appSharedPref.getInt(intKey, 0)
    }

    fun setValue_int(intKey: String?, intKeyValue: Int) {
        prefsEditor.putInt(intKey, intKeyValue).commit()
    }

    // string
    fun getValue_string(stringKey: String?): String? {
        return appSharedPref.getString(stringKey, "")
    }

    fun setValue_string(stringKey: String?, stringKeyValue: String?) {
        prefsEditor.putString(stringKey, stringKeyValue).commit()
    }

    //boolean
    fun getValue_boolean(booleanKey: String?): Boolean {
        return appSharedPref.getBoolean(booleanKey, false)
    }

    fun setValue_boolean(booleanKey: String?, booleanKeyValue: Boolean) {
        prefsEditor.putBoolean(booleanKey, booleanKeyValue).commit()
    }

    fun clearData() {
        prefsEditor.clear().commit()
    }

    companion object {
        private const val USER_PREF = "user_todo"
    }

    init {
        appSharedPref = context.getSharedPreferences(USER_PREF, Activity.MODE_PRIVATE)
        prefsEditor = appSharedPref.edit()
    }
}