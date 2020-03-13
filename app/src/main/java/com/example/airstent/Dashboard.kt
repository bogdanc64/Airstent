package com.example.airstent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dashboard.*

class Dashboard : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_dashboard)
        signout_button.setOnClickListener{
            _signout()
        }
    }
    private fun _signout(){
        auth.signOut()
        finish()
        startActivity(Intent(this, Login::class.java))
    }

}
