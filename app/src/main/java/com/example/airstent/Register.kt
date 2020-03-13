package com.example.airstent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.back_button
import kotlinx.android.synthetic.main.activity_register.email_edittext
import kotlinx.android.synthetic.main.activity_register.password_edittext

class Register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()
        back_button.setOnClickListener{
            _back()
        }
        register_button.setOnClickListener{
            register()
        }

    }
    private fun _back()
    {
        startActivity(Intent(this, Login::class.java))
        finish()
    }
    fun register()
    {
        if(!Patterns.EMAIL_ADDRESS.matcher(email_edittext.text.toString()).matches())
        {
            email_edittext.error="Please enter a valid email"
            email_edittext.requestFocus()
            return
        }
        if(email_edittext.text.toString().isEmpty())
        {
            email_edittext.error="Please enter a email"
            email_edittext.requestFocus()
            return
        }
        if(password_edittext.text.toString().isEmpty())
        {
            email_edittext.error="Please enter a password"
            email_edittext.requestFocus()
            return
        }
        else if(password_edittext.text.toString().isEmpty() && email_edittext.text.toString().isEmpty())
        {
            email_edittext.error="Please fill the fields"
            email_edittext.requestFocus()
        }
        auth.createUserWithEmailAndPassword(email_edittext.text.toString(), password_edittext.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    Log.d("Main","created user with uid: ${task.result?.user?.uid}")
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(baseContext, "Account created. Please verify your email! :)",
                                    Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, Login::class.java))
                                finish()
                            }
                        }

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Register failed. Try again later...",
                        Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, Login::class.java))
                    finish()
                }

            }
    }

}
