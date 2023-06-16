package com.example.cookiejar

import android.R.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import java.io.File


class MainActivity : AppCompatActivity() {

    // TODO: what is lateinit ?
    private lateinit var username: String
    val db = com.google.firebase.ktx.Firebase.database
    private val profile = "profile.txt"
    private var sad = mutableMapOf<String, String>()
    private var des = mutableMapOf<String, String>()
    private var mad = mutableMapOf<String, String>()
    private var pre = mutableMapOf<String, String>()
    private var low = mutableMapOf<String, String>()
    private val lookup = mapOf("sad" to "Triste", "des" to "Desanimada", "mad" to "Enfadado", "pre" to "Preocupada", "low" to "Espiritualmente mal")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO: read or write file
        //username = getUserName()
        username = "Becka"

        if (username != "") {
            queryFirebase(username)
        }

        // listen for click, show new screen
        val sadThought = findViewById<TextView>(R.id.sadView)
        sadThought.setOnClickListener(){
            val intent = Intent(this, Sad::class.java)
            startActivity(intent)
        }

        // listen for click, show new screen
        val desThought = findViewById<TextView>(R.id.desView)
        desThought.setOnClickListener(){
            // show random thought
            showThought("des")
        }

        // listen for click, show new screen
        val madThought = findViewById<TextView>(R.id.madView)
        madThought.setOnClickListener(){
            // show random thought
            showThought("mad")
        }

        // listen for click, show new screen
        val preThought = findViewById<TextView>(R.id.preView)
        preThought.setOnClickListener(){
            // show random thought
            showThought("pre")
        }

        // listen for click, show new screen
        val lowThought = findViewById<TextView>(R.id.lowView)
        lowThought.setOnClickListener(){
            // show random thought
            showThought("low")
        }

        // listen for click, show new screen
        val newThought = findViewById<Button>(R.id.addButton)
        newThought.setOnClickListener(){
            val intent = Intent(this, NewThought::class.java)
            startActivity(intent)
        }

        // establish firebase connection
        getStatsThoughts()

    }

    /******************************************************
    determine how many thoughts exist and how many have been read, bind ui
    ******************************************************/
    private fun getStatsThoughts() {
        val collection = db.getReference("Becka")
        collection.addValueEventListener(object: ValueEventListener {
            //val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                var stats = mutableMapOf("sad" to 0, "sadUn" to 0,
                                         "des" to 0, "desUn" to 0,
                                         "mad" to 0, "madUn" to 0,
                                         "pre" to 0, "preUn" to 0,
                                         "low" to 0, "lowUn" to 0
                )

                for (recordSnapshot in snapshot.children) {
                    val record = recordSnapshot.getValue(Quote::class.java)
                    val key = recordSnapshot.key

                    Log.d("DEBUG", "$key = ${record!!.topic.toString()}")
                    when(record!!.topic.toString().lowercase()) {
                        "triste" -> {
                            stats["sad"] = 1 + stats["sad"]!!.toInt() // increment overall count
                            if (!record!!.read) {
                                stats["sadUn"] = 1 + stats["sadUn"]!!.toInt() // increment unread count
                            }
                            sad[key.toString()] = record!!.quote.toString()
                        }
                        "desmotivada" -> {
                            stats["des"] = 1 + stats["des"]!!.toInt() // increment overall count
                            if (!record!!.read) {
                                stats["desUn"] = 1 + stats["desUn"]!!.toInt() // increment unread count
                            }
                            des[key.toString()] = record!!.quote.toString()
                        }
                        "enfadado" -> {
                            stats["mad"] = 1 + stats["mad"]!!.toInt() // increment overall count
                            if (!record!!.read) {
                                stats["madUn"] = 1 + stats["madUn"]!!.toInt() // increment unread count
                            }
                            mad[key.toString()] = record!!.quote.toString()
                        }
                        "preocupada" -> {
                            stats["pre"] = 1 + stats["pre"]!!.toInt() // increment overall count
                            if (!record!!.read) {
                                stats["preUn"] = 1 + stats["preUn"]!!.toInt() // increment unread count
                            }
                            pre[key.toString()] = record!!.quote.toString()
                        }
                        "bajo" -> {
                            stats["low"] = 1 + stats["low"]!!.toInt() // increment overall count
                            if (!record!!.read) {
                                stats["lowUn"] = 1 + stats["lowUn"]!!.toInt() // increment unread count
                            }
                            low[key.toString()] = record!!.quote.toString()
                        }
                        else -> {
                            "test"
                        }
                    }
                }

                var sadView = findViewById<TextView>(R.id.sadView)
                var desView = findViewById<TextView>(R.id.desView)
                var madView = findViewById<TextView>(R.id.madView)
                var preView = findViewById<TextView>(R.id.preView)
                var lowView = findViewById<TextView>(R.id.lowView)

                sadView.text = "Triste: ${stats["sad"]} thoughts, ${stats["sadUn"]} unread"
                desView.text = "Desmotivada: ${stats["des"]} thoughts, ${stats["desUn"]} unread"
                madView.text = "Enfadado: ${stats["mad"]} thoughts, ${stats["madUn"]} unread"
                preView.text = "Preocudada: ${stats["pre"]} thoughts, ${stats["preUn"]} unread"
                lowView.text = "Espiritumente mal: ${stats["low"]} thoughts, ${stats["lowUn"]} unread"

            }

            override fun onCancelled(error: DatabaseError) {
                // handle error
            }
        })
    }


    /******************************************************
    check for username
    https://www.tutorialkart.com/kotlin/kotlin-file-operations
    ******************************************************/
    private fun getUserName() : String {
        var file = File(profile)
        val fileExists = file.exists()
        var username = ""

        if (fileExists) {
            Toast.makeText(this, "file exists", Toast.LENGTH_LONG).show()
            // read from file
            username = file.readText()
        }
        else {
            Toast.makeText(this, "$profile does not exist", Toast.LENGTH_LONG).show()
            // prompt for name in little window
            promptForUserName()
        }

        return username // return local name and set as global
    }

    /******************************************************
    initialize the database connection
    ******************************************************/
    private fun queryFirebase(user: String) {
        //var fb = Firebase(user)
        //fb.readThoughts()
    }

    /******************************************************
    show a dialog box and get the user name
    ******************************************************/
    private fun promptForUserName() {
        val builder = AlertDialog.Builder(this)
        val inflater: LayoutInflater = layoutInflater
        val dialogLayout: View = inflater.inflate(R.layout.username_prompt,null)
        val editText:EditText = dialogLayout.findViewById<EditText>(R.id.username)
        var userName = ""

        with(builder) {
            setTitle("Enter your username")
            // https://stackoverflow.com/questions/70757388/alertdialog-in-android-studio-kotlin-not-displaying
            setPositiveButton("OK") { _: DialogInterface?, _: Int ->
                userName = editText.text.toString()
                // TODO: understand why context works here instead of "this"
                Toast.makeText(context, "Hello $userName", Toast.LENGTH_SHORT).show()
                createProfileFile(userName)
                queryFirebase(username)
            }

            setView(dialogLayout)
            show()
        }
    }

    /******************************************************
    show a dialog box with a random thought
    https://www.javatpoint.com/kotlin-android-alertdialog
    ******************************************************/
    private fun showThought(topic: String) {
        val builder = AlertDialog.Builder(this)
        var temp = emptyMap<String, String>()

        // set dialog title
        builder.setTitle(lookup[topic])
        when(topic) {
            "sad" -> {
                builder.setIcon(R.drawable.sad)
                temp = low
            }
            "pre" -> {
                builder.setIcon(R.drawable.pre)
                temp = pre
            }
            "mad" -> {
                builder.setIcon(R.drawable.mad)
                temp = mad
            }
            "des" -> {
                builder.setIcon(R.drawable.des)
                temp = des
            }
            "low" -> {
                builder.setIcon(R.drawable.low)
                temp = low
            }
            else -> {
                // what to do
            }
        }
        // set dialog message
        if (temp.isNotEmpty()) {

            // shuffle the map and get a random key
            val keys = temp.keys.shuffled()
            builder.setMessage("${temp[keys[0]]}")

            // mark as read
            updateQuote(keys[0])

            // TODO: mark as favorite
            builder.setNegativeButton("Favorite"){ dialogInterface, which ->
                Toast.makeText(applicationContext,"Marked as favorite",Toast.LENGTH_LONG).show()
            }
            // delete
            builder.setNeutralButton("Delete"){ dialogInterface , which ->
                Toast.makeText(applicationContext,"Delete ${keys[0]}",Toast.LENGTH_LONG).show()
                deleteQuote(keys[0])
            }
        }
        else {
            builder.setMessage("You don´t have any of these thoughts")
        }

        // performing close action
        builder.setPositiveButton("OK"){ dialogInterface, which ->
            // Toast.makeText(applicationContext,"close",Toast.LENGTH_LONG).show()
        }

        // create alert
        val alertDialog: AlertDialog = builder.create()
        // set properties and show
        alertDialog.setCancelable(false)
        alertDialog.show()

    }

    /******************************************************
    update quote, mark as read
    ******************************************************/
    private fun updateQuote(id: String) {
        val collection = db.getReference(username)
        val updateData = mapOf<String, Any>(
            "read" to true
        )
        collection.child(id).updateChildren(updateData)
            .addOnSuccessListener {
                Log.d("DEBUG", "Success: updated $id")
            }
            .addOnFailureListener { e ->
                Log.d("DEBUG", "Error updating document $e")
            }
    }


    /******************************************************
    delete quote
    ******************************************************/
    private fun deleteQuote(id: String) {
        val collection = db.getReference(username)
        collection.child(id).removeValue()
            .addOnSuccessListener {
                Log.d("DELETE", "Success: deleted $id")
            }
            .addOnFailureListener { e ->
                Log.d("DELETE", "Error deleting document", e)
            }
    }


    /******************************************************
    write the username to disk
    https://www.javatpoint.com/kotlin-android-read-and-write-internal-storage
    ******************************************************/
    private fun createProfileFile(username: String) {
        val filename = profile

        val fileOutputStream = openFileOutput(filename, Context.MODE_PRIVATE)
        fileOutputStream.write(username.toByteArray())

        var file = File(profile)
        val fileExists = file.exists()

        if (fileExists) {
            Toast.makeText(this, "file exists: ${file.readText()}", Toast.LENGTH_LONG).show()
        }
        else {
            Toast.makeText(this, "file does not exist", Toast.LENGTH_LONG).show()
        }
    }

    // https://stackoverflow.com/questions/68806876/firebase-realtime-database-connection-killed-different-region
}