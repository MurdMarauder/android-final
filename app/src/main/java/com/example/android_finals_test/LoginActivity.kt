package com.example.android_finals_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        val emailET = findViewById<EditText>(R.id.loginEmailET)
        val passwordET = findViewById<EditText>(R.id.loginPasswordET)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerButton = findViewById<Button>(R.id.registerButton)

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

        }

    }

    public override fun onResume() {
        super.onResume()

        val auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser != null) {
            finish()
        }
    }
}