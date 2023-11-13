package com.example.android_finals_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerAndLoginButton = findViewById<Button>(R.id.registerAndLoginButton)
        auth = Firebase.auth
        val emailET = findViewById<EditText>(R.id.registerEmailET)
        val passwordET = findViewById<EditText>(R.id.registerPasswordET)

        registerAndLoginButton.setOnClickListener {
            val email = emailET.text.toString()
            val password = passwordET.text.toString()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            baseContext,
                            "Registered Successfully",
                            Toast.LENGTH_SHORT,
                        ).show()

                        finish()
                    } else {
                        Toast.makeText(
                            baseContext,
                            "Registration Failed",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }
    }
}