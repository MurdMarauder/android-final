package com.example.android_finals_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.marginBottom
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = Firebase.firestore
        val linearL = findViewById<LinearLayout>(R.id.linear)




        db.collection("collection-1")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val cardV = CardView(this)


                    val linearInsideCard = LinearLayout(this)
                    val tv = TextView(this)
                    tv.text = (document.data.get("description").toString())
                    linearInsideCard.addView(tv)
                    cardV.addView(linearInsideCard)
                    linearL.addView(cardV)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("zzzz", "Error getting documents.", exception)
            }



    }
}