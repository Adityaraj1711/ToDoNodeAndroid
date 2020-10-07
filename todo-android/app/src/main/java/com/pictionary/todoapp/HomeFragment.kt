package com.pictionary.todoapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomeFragment : Fragment() {

    lateinit var floatingActionButton: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        floatingActionButton = view.findViewById(R.id.add_task_btn)
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
        val title : EditText = alertLayout.findViewById(R.id.title)
        val description: EditText = alertLayout.findViewById(R.id.description)
//        val alertDialog: AlertDialog = AlertDialog.Builder(activity).create()
//        alertDialog.setView(alertLayout)
//        alertDialog.setTitle("Add Task")
//
//
//        alertDialog.show()

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
                        Toast.makeText(activity, "Positive Button Pressed", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        })
        dialog.show()
    }


}