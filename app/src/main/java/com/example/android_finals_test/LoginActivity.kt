package com.example.android_finals_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    private val logoUrl = "https://firebasestorage.googleapis.com/v0/b/android-dev-final-b7273.appspot.com/o/logo.jpg?alt=media&token=1c34d817-65f5-41e9-9893-978fd3859726"

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        val emailET = findViewById<EditText>(R.id.loginEmailET)
        val passwordET = findViewById<EditText>(R.id.loginPasswordET)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerButton = findViewById<Button>(R.id.registerButton)
        val loginLogo = findViewById<ImageView>(R.id.loginLogo)
        Glide.with(this).load(logoUrl).into(loginLogo)

        loginButton.setOnClickListener {
            val email = emailET.text.toString()
            val password = passwordET.text.toString()
            if (email != "" && password != ""){
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                baseContext,
                                "Success",
                                Toast.LENGTH_SHORT,
                            ).show()

                            finish()
                        } else {
                            Toast.makeText(
                                baseContext,
                                "Login failed",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                }
            }else{
                Toast.makeText(
                    baseContext,
                    "Login failed",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }

        registerButton.setOnClickListener {
            finish()
            val registerActivity = Intent(this, RegisterActivity::class.java)
            startActivity(registerActivity)
        }

    }
}