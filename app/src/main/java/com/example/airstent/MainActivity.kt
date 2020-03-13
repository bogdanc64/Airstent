package com.example.airstent

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var preference : SharedPreferences
    val prof_show_get_started = "GetStarted"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preference = getSharedPreferences("Airstent", Context.MODE_PRIVATE)
        if(!preference.getBoolean(prof_show_get_started,true))
        {
            _getstarted()
            finish()
        }

        getStarted_button.setOnClickListener{
            _getstarted()
            val editor = preference.edit()
            editor.putBoolean(prof_show_get_started, false)
            editor.apply()
        }
    }
    private fun _getstarted()
    {
        startActivity(Intent(this, Login::class.java))
    }

}
