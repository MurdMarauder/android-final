package com.example.android_finals_test

import android.app.ActionBar.LayoutParams
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.marginTop
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore


class MainActivity : AppCompatActivity() {
    private val menuCollectionName = "collection-1"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = Firebase.firestore
        val linearL = findViewById<LinearLayout>(R.id.linear)
        val logoutButton = findViewById<Button>(R.id.mainLogoutButton)

        logoutButton.setOnClickListener {
            Firebase.auth.signOut()
            val loginActivity = Intent(this, LoginActivity::class.java)
            startActivity(loginActivity)
        }

        db.collection(menuCollectionName)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val description = document.data["description"].toString()
                    val imageUrl = document.data["imageUrl"].toString()
                    val price = document.data["price"].toString()
                    val title = document.data["title"].toString()


                    val cardV = CardView(this)
                    val cardVParams = LayoutParams(resources.getDimensionPixelOffset
                        (R.dimen.card_width), LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(0, 0, 0, resources.getDimensionPixelOffset(R.dimen.card_margin_bottom))
                    }
                    cardV.layoutParams = cardVParams


                    val cardLinearLayout = LinearLayout(this)
                    val cardLinearLayoutParams = LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT
                    )
                    cardLinearLayout.gravity = Gravity.CENTER_HORIZONTAL
                    cardLinearLayout.orientation = LinearLayout.VERTICAL
                    cardLinearLayout.setPadding(
                        resources.getDimensionPixelOffset(R.dimen.cardLinearLayout_padding_x),
                        resources.getDimensionPixelOffset(R.dimen.cardLinearLayout_padding_y),
                        resources.getDimensionPixelOffset(R.dimen.cardLinearLayout_padding_x),
                        resources.getDimensionPixelOffset(R.dimen.cardLinearLayout_padding_y)
                    )
                    cardLinearLayout.layoutParams = cardLinearLayoutParams


                    val imageV = ImageView(this)
                    val imageVParams = LinearLayout.LayoutParams(
                        resources.getDimensionPixelOffset(R.dimen.imageView_width), resources.getDimensionPixelOffset(R.dimen.imageView_height)
                    )
                    imageV.layoutParams = imageVParams
                    Glide.with(this).load(imageUrl).into(imageV)


                    val titleTv = TextView(this)
                    titleTv.layoutParams = LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT
                    ).apply{
                        topMargin = resources.getDimensionPixelOffset(R.dimen.textView_title_margin_top)
                    }
                    titleTv.text = (title)
                    titleTv.textSize = 18F
                    titleTv.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    titleTv.setTypeface(null, Typeface.BOLD)


                    val descriptionTv = TextView(this)
                    descriptionTv.layoutParams = LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT
                    ).apply{
                        topMargin = resources.getDimensionPixelOffset(R.dimen.textView_title_margin_top)
                    }
                    descriptionTv.text = (description)
                    descriptionTv.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START


                    val priceViewBtnLinearLayout = LinearLayout(this)
                    val priceViewBtnLinearLayoutParams = LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT
                    ).apply{
                        topMargin = resources.getDimensionPixelOffset(R.dimen.textView_title_margin_top)
                    }
                    priceViewBtnLinearLayout.orientation = LinearLayout.HORIZONTAL
                    priceViewBtnLinearLayout.layoutParams = priceViewBtnLinearLayoutParams


                    val priceTv = TextView(this)
                    val priceTvParams = LinearLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT,
                        1F
                    )
                    priceTv.layoutParams = priceTvParams

                    priceTv.text = (price)
                    priceTv.textSize = 18F
                    priceTv.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START
                    priceTv.setTypeface(null, Typeface.BOLD)


                    val viewBtnTv = TextView(this)
                    viewBtnTv.layoutParams = LinearLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT,
                        1F
                    )
                    viewBtnTv.text = "View"
                    viewBtnTv.textSize = 18F
                    viewBtnTv.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_END
                    viewBtnTv.setTypeface(null, Typeface.BOLD)

                    viewBtnTv.setOnClickListener {
                        val productViewActivity = Intent(this, ProductViewActivity::class.java)
                        productViewActivity.putExtra("imageUrl", imageUrl)
                        productViewActivity.putExtra("title", title)
                        productViewActivity.putExtra("description", description)
                        productViewActivity.putExtra("price", price)
                        startActivity(productViewActivity)
                    }

                    priceViewBtnLinearLayout.addView(priceTv)
                    priceViewBtnLinearLayout.addView(viewBtnTv)

                    cardLinearLayout.addView(imageV)
                    cardLinearLayout.addView(titleTv)
                    cardLinearLayout.addView(descriptionTv)
                    cardLinearLayout.addView(priceViewBtnLinearLayout)
                    cardV.addView(cardLinearLayout)
                    linearL.addView(cardV)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("zzzz", "Error getting documents.", exception)
            }
    }

    public override fun onStart() {
        super.onStart()

        val auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser == null) {
            val loginActivity = Intent(this, LoginActivity::class.java)
            startActivity(loginActivity)
        }
    }
}