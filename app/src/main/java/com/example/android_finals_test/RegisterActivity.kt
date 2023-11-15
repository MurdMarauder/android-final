package com.example.android_finals_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val userCollectionName = "users"
    private val logoUrl = "https://firebasestorage.googleapis.com/v0/b/android-dev-final-b7273.appspot.com/o/logo.jpg?alt=media&token=1c34d817-65f5-41e9-9893-978fd3859726"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerAndLoginButton = findViewById<Button>(R.id.registerAndLoginButton)
        auth = Firebase.auth
        val db = Firebase.firestore
        val emailET = findViewById<EditText>(R.id.registerEmailET)
        val passwordET = findViewById<EditText>(R.id.registerPasswordET)
        val displayNameET = findViewById<EditText>(R.id.registerDisplayNameET)
        val registerLogo = findViewById<ImageView>(R.id.registerLogo)
        val registerBack = findViewById<Button>(R.id.registerBack)

        Glide.with(this).load(logoUrl).into(registerLogo)

        registerBack.setOnClickListener {
            finish()
            val loginActivity = Intent(this, LoginActivity::class.java)
            startActivity(loginActivity)
        }

        registerAndLoginButton.setOnClickListener {
            val email = emailET.text.toString()
            val password = passwordET.text.toString()
            val displayName = displayNameET.text.toString()

            if (email != "" && password != "" && displayName != ""){
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid
                            val data = hashMapOf(
                                "email" to email,
                                "displayName" to displayName,
                            )

                            db.collection(userCollectionName).document()
                                .set(data)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        baseContext,
                                        "Registered Successfully",
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(
                                        baseContext,
                                        "Registration Failed",
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                }

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
}