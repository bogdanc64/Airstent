package com.example.airstent

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*


class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        back_button.setOnClickListener{
            _back()
        }
        register_text_button.setOnClickListener{
            _register()
        }
        login_button.setOnClickListener{
            _login();
        }
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser!=null && currentUser.isEmailVerified)
            updateUI(currentUser)
    }
    fun updateUI(currentUser: FirebaseUser?)
    {
        if(currentUser != null){
            if(currentUser.isEmailVerified)
            {
                startActivity(Intent(this,Dashboard::class.java))
                finish()
            }
            else
            {
                Toast.makeText(baseContext,"Email is not verified...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun _login()
    {
        if(!Patterns.EMAIL_ADDRESS.matcher(email_edittext.text.toString()).matches())
        {
            email_edittext.error="Please enter a valid email"
            email_edittext.requestFocus()
        }
        else if(email_edittext.text.toString().isEmpty())
        {
            email_edittext.error="Please enter a email"
            email_edittext.requestFocus()
        }
        else if(password_edittext.text.toString().isEmpty())
        {
            email_edittext.error="Please enter a password"
            email_edittext.requestFocus()
        }
        else if(password_edittext.text.toString().isEmpty() && email_edittext.text.toString().isEmpty())
        {
            email_edittext.requestFocus()
        }
        else{
        auth.signInWithEmailAndPassword(email_edittext.text.toString(), password_edittext.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(baseContext, "Email or password is wrong.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
        }

    }
    private fun _back()
    {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
    private fun _register()
    {
        startActivity(Intent(this, Register::class.java))
    }
}

