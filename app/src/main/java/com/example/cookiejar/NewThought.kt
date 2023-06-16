package com.example.cookiejar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database

class NewThought : AppCompatActivity() {

    val db = com.google.firebase.ktx.Firebase.database
    private lateinit var selectTopic: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_thought)

        // add checkboxes
        addUsers()

        // https://www.youtube.com/watch?v=Bw4bXBjNpFs
        selectTopic = findViewById<Spinner>(R.id.topic)
        val topics = arrayOf("Triste", "Demotivada", "Enfadada", "Preocupada", "Espiritualmente mal")
        // add items to spinner
        val arrayAdapt = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, topics)
        selectTopic.adapter = arrayAdapt

        // listen for click, show new screen
        val addButton = findViewById<Button>(R.id.addQuote)
        addButton.setOnClickListener() {
            addQuote()
        }
    }

    /******************************************************
    add users
    TODO: query users and add them as checkboxes to new quote activity
    ******************************************************/
    private fun addUsers() {
    }


    /******************************************************
    add quote
    https://firebase.google.com/docs/firestore/manage-data/add-data
    TODO: check for empty quote
    ******************************************************/
    private fun addQuote() {


        // TODO: get list of users see if they are marked
        val users = arrayOf("Becka", "Madison")

        users.forEach {

            var user = findViewById<CheckBox>(resources.getIdentifier(it, "id", packageName))
            val checked = user.isChecked

            //Toast.makeText(this, "$it is $checked", Toast.LENGTH_LONG).show()
            Log.d("DEBUG", "$it is $checked")

            if (checked) {
                //val collection = db.getReference("Becka")
                val collection = db.getReference(it)
                val id = collection.push().key
                val selected = selectTopic.getSelectedItem().toString()
                var textBox = findViewById<EditText>(R.id.newQuote)

                val newQuote = mapOf<String, Any>(
                    "topic" to selected,
                    "quote" to textBox.text.toString(),
                    "favorite" to false,
                    "read" to false
                )

                // add new document to database
                collection.child(id!!)
                    .setValue(newQuote)
                    .addOnSuccessListener{
                        Log.d("DEBUG", "Success creating document")
                        Toast.makeText(this, "Quote added for $it", Toast.LENGTH_LONG).show()

                        // clear textbox
                        textBox.text.clear()
                    }
                    .addOnCompleteListener{
                        Log.d("DEBUG", "Document created")
                    }
                    .addOnFailureListener{ e ->
                        Log.d("DEBUG", "Error creating document $e")
                        Toast.makeText(this, "Error $e.message", Toast.LENGTH_LONG).show()
                    }
            }

        }
    }
}