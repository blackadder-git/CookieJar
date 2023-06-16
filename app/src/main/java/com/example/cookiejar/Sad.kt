package com.example.cookiejar

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database

class Sad : AppCompatActivity() {

    val db = com.google.firebase.ktx.Firebase.database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sad)

        getStatsThoughts(this)
    }

    /******************************************************
    display sad thoughts
    ******************************************************/
    private fun getStatsThoughts(context: Context) {
        val collection = db.getReference("Becka")
        collection.addValueEventListener(object: ValueEventListener {
            //val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                var stats = mutableMapOf("sad" to 0,
                )

                val quotes = ArrayList<String>()
                var scrollLayout = findViewById<LinearLayout>(R.id.linearLayout)

                for (recordSnapshot in snapshot.children) {
                    val record = recordSnapshot.getValue(Quote::class.java)
                    val key = recordSnapshot.key

                    Log.d("DEBUG", "$key = ${record!!.topic.toString()}")
                    when(record!!.topic.toString()) {
                        "triste" -> {
                            //quotes.add(record!!.quote.toString())

                            val textView = TextView(context)
                            textView.layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                            textView.text = record!!.quote.toString()
                            textView.textSize = 32f
                            textView.setPadding(8, 8, 8, 8)

                            textView.setOnClickListener{
                                clickQuote(record!!.quote.toString())
                            }

                            scrollLayout.addView(textView)

                        }
                        else -> {
                            "test"
                        }
                    }
                }

                /*
                // https://www.geeksforgeeks.org/dynamic-textview-in-kotlin/
                var scrollLayout = findViewById<LinearLayout>(R.id.linearLayout)

                // Example items to add
                // val itemTexts = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
                // for (itemText in itemTexts) {

                for (quote in quotes) {
                    val textView = TextView(context)
                    textView.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    textView.text = quote
                    textView.textSize = 32f
                    textView.setPadding(8, 8, 8, 8)

                    scrollLayout.addView(textView)
                }
                */
            }

            override fun onCancelled(error: DatabaseError) {
                // handle error
            }
        })
    }

    private fun clickQuote(quote: String) {
        Toast.makeText(this, "Clicked item: $quote", Toast.LENGTH_SHORT).show()
    }
}