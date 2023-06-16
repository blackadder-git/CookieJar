package com.example.cookiejar

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Firebase(username: String) {

    //private val username = ""

    //private lateinit var db: DatabaseReference // https://www.youtube.com/watch?v=miJooBq9iwE
    //private var db = FirebaseDatabase.getInstance()
    val db = Firebase.database
    //var reference: DatabaseReference

    init {
        // https://cookiejar-1949-default-rtdb.europe-west1.firebasedatabase.app
        //db = FirebaseDatabase.getInstance().getReference(username)
        // https://firebase.google.com/docs/database/android/read-and-write not sure if this is better but doc uses Firebase rather than FirebaseDatabase
        //db = Firebase.database
        //reference = db.getReference("Becka")

        Log.d("DEBUG", "Username: $username")
    }

    /*******************************************
    CREATE: create thought
     *******************************************/
    fun createThought() {

    }

    /*******************************************
    READ: get thoughts from server
    https://firebase.google.com/docs/database/android/start
    *******************************************/
    fun readThoughts() {
        Log.d("DEBUG", "BEGIN THOUGHTS")

        val collection = db.getReference("Becka")

        collection.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (recordSnapshot in snapshot.children) {
                    val record = recordSnapshot.getValue(Quote::class.java)
                    val key = recordSnapshot.key
                    Log.d("DEBUG", "$key = ${record.toString()}")

                    val quote = record?.quote
                    Log.d("DEBUG", quote.toString())

                    if (key == "1") {
                        val updateData = mapOf<String, Any>(
                            "quote" to "aquaman"
                        )
                        collection.child(key!!).updateChildren(updateData)
                        .addOnSuccessListener {
                            Log.d("DEBUG", "update success")
                        }
                        .addOnFailureListener { err ->
                            Log.d("DEBUG", "update fail: $err")
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // handle error
            }
        })

        Log.d("DEBUG", "END THOUGHTS")
    }

    /*******************************************
    READ: get users from server
     *******************************************/
    fun readUsers() : MutableList<String> {
        Log.d("DEBUG", "BEGIN USERS")
        val collection = db.getReference("users")

        var users = mutableListOf<String>()

        collection.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (recordSnapshot in snapshot.children) {
                    val record = recordSnapshot.getValue(User::class.java)
                    val key = recordSnapshot.key
                    Log.d("DEBUG", "$key = ${record.toString()}")

                    val name = record?.name
                    users.add(name.toString())
                    Log.d("DEBUG", record.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // handle error
            }
        })

        Log.d("DEBUG", "END USERS")
        return users
    }

    /*******************************************
    UPDATE: update thought
     *******************************************/
    fun updateThought() {

    }

    /*******************************************
    DELETE: delete thought
     *******************************************/
    fun deleteThought() {

    }



    /*******************************************

    private fun savePerson() {
        Toast.makeText(this, "add person", Toast.LENGTH_LONG).show()
        val personName = nameTxt.text.toString()

        // validate form
        if (personName.isEmpty()) {
            // show error
        }


        val newRecord = db.getReference("Becka")
        val id = newRecord.push().key
        val quote = Quote("apple", "pie", read = false, favorite = false)
        newRecord.child(id!!).setValue(quote)
        /*
        val personId = db.push().key!! // push seems to work when I used Firebase.database.reference above
        Toast.makeText(this, "db push $personId", Toast.LENGTH_LONG).show()
        val per = Person(personId, personName)
        Toast.makeText(this, "per = $personName", Toast.LENGTH_LONG).show()
        //db.child(personId)
            db.child("People")
            .child(personId)
            .setValue(per)
            .addOnSuccessListener{
                Toast.makeText(this, "person added", Toast.LENGTH_LONG).show()
            }
            .addOnCompleteListener{
                Toast.makeText(this, "done", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener{ err ->
                Toast.makeText(this, "Error $err.message", Toast.LENGTH_LONG).show()
            }

        Toast.makeText(this, "end", Toast.LENGTH_LONG).show()
        */
    }

    private fun getPerson() {
        Toast.makeText(this, "get person", Toast.LENGTH_LONG).show()
        Log.d("test", "BEGINGAME")
        // TODO: get user name from properties file

        val collection = db.getReference("Becka")

        collection.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (recordSnapshot in snapshot.children) {
                    val record = recordSnapshot.getValue(Quote::class.java)
                    val key = recordSnapshot.key
                    Log.d("test", "$key = ${record.toString()}")

                    val quote = record?.quote
                    Log.d("test", quote.toString())

                    if (key == "1") {
                        val updateData = mapOf<String, Any>(
                            "quote" to "batman"
                        )
                        collection.child(key!!).updateChildren(updateData)
                            .addOnSuccessListener {
                                Log.d("test", "update success")
                            }
                            .addOnFailureListener { err ->
                                Log.d("test", "update fail: $err")
                            }
                    }


                    /*
                    2023-06-09 12:52:28.033 15498-15498 test                    com.example.cookiejar                D  Person(quote=something inspiring, topic=red, read=false, favorite=true)
                    2023-06-09 12:52:28.033 15498-15498 test                    com.example.cookiejar                D  something inspiring
                    2023-06-09 12:52:28.035 15498-15498 test                    com.example.cookiejar                D  Person(quote=more inspiration, topic=blue, read=true, favorite=true)
                    2023-06-09 12:52:28.035 15498-15498 test                    com.example.cookiejar                D  more inspiration
                    2023-06-09 12:52:28.036 15498-15498 test                    com.example.cookiejar                D  Person(quote=even more inspiration, topic=yellow, read=true, favorite=false)
                    2023-06-09 12:52:28.037 15498-15498 test                    com.example.cookiejar                D  even more inspiration
                    */


                }
            }

            override fun onCancelled(error: DatabaseError) {
                // handle error
            }
        })

        Log.d("test", "ENDGAME")


        /*
        db.child("Becka").get()
            .addOnSuccessListener {
                if (it.exists()) {
                    Toast.makeText(this, "got person", Toast.LENGTH_LONG).show()

                    // iterate list
                    Log.d("DEBUG", it.toString())

                }
                else {
                    Toast.makeText(this, "FAILED to get person", Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener{ err ->
                Toast.makeText(this, "Error $err.message", Toast.LENGTH_LONG).show()
            }

        Toast.makeText(this, "end person", Toast.LENGTH_LONG).show()
        */
    }

*******************************************/


}