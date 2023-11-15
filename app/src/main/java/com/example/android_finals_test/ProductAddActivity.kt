package com.example.android_finals_test

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.time.Instant
import java.time.format.DateTimeFormatter

class ProductAddActivity : AppCompatActivity() {
    private val menuCollectionName = "items"
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_add)

        val titleET = findViewById<EditText>(R.id.productAddTitleET)
        val descriptionET = findViewById<EditText>(R.id.productAddDescriptionET)
        val priceET = findViewById<EditText>(R.id.productAddPriceET)
        val imageUrlET = findViewById<EditText>(R.id.productAddImageUrlET)
        val addProductButton = findViewById<Button>(R.id.productAddButton)
        val db = Firebase.firestore

        addProductButton.setOnClickListener {
            val title = titleET.text.toString()
            val description = descriptionET.text.toString()
            val price = priceET.text.toString()
            val imageUrl = imageUrlET.text.toString()

            if (title != "" && description != "" && price != "" && imageUrl != ""){
                val data = hashMapOf(
                    "name" to title,
                    "description" to description,
                    "price" to price,
                    "photoUrl" to imageUrl,
                    "dateCreated" to DateTimeFormatter.ISO_INSTANT.format(Instant.now())
                )

                db.collection(menuCollectionName).document()
                    .set(data)
                    .addOnSuccessListener {
                        Toast.makeText(
                            baseContext,
                            "Added Product Successfully",
                            Toast.LENGTH_SHORT,
                        ).show()
                        finish()
                        val mainActivity = Intent(this, MainActivity::class.java)
                        startActivity(mainActivity)
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            baseContext,
                            "Addition of Product Failed",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
            }
        }

    }
}