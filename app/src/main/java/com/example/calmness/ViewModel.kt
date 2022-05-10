package com.example.calmness


import android.content.ContentValues.TAG
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

import com.google.android.gms.tasks.OnSuccessListener

import androidx.annotation.NonNull
import androidx.core.content.ContentProviderCompat.requireContext

import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.database.core.Context


class ViewModel: ViewModel() {

    private val USER = "USER"
    val myRef =
        Firebase.database("https://calmness-d0600-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference(USER)
    val myRef2 =
        Firebase.database("https://calmness-d0600-default-rtdb.europe-west1.firebasedatabase.app/").getReference()
    var storageRef = FirebaseStorage.getInstance().getReference("AvatarBD")
    private var auth: FirebaseAuth = Firebase.auth
    var localUri: Uri? = null
    var localuser:MutableLiveData<User> = MutableLiveData()
    var mood:MutableLiveData<String> = MutableLiveData()


    fun takeUser():LiveData<User>{
        return localuser
    }

    fun getMood():LiveData<String>{
        return mood
    }
    fun happyMood(){
        myRef2.child("Happy").get().addOnSuccessListener {
            mood.value=it.value.toString()
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }

    }
    fun normalMood(){
        myRef2.child("Hormal").get().addOnSuccessListener {
            mood.value=it.value.toString()
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }
    fun badMood(){
        val rnds = (1..2).random()
        if (rnds==1) {
            myRef2.child("Bad").get().addOnSuccessListener {
                mood.value = it.value.toString()
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }
        }else{
            myRef2.child("Bad2").get().addOnSuccessListener {
                mood.value = it.value.toString()
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }
        }
    }
    fun irritableMood(){
        val rnds = (1..2).random()
        if (rnds==1) {
            myRef2.child("Irritable").get().addOnSuccessListener {
                mood.value = it.value.toString()
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }
        }else{
            myRef2.child("Irritable2").get().addOnSuccessListener {
                mood.value = it.value.toString()
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }
        }
    }
    fun getUser(){
       // myRef.child(auth.currentUser!!.uid).addValueEventListener(object: ValueEventListener {

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
               // val post = dataSnapshot.getValue<User>()
                dataSnapshot.children.forEach {
                    val user: User = it.getValue(User::class.java)!!
                    if (user.id == auth.currentUser!!.uid)
                    {
                        localuser.value = user
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        myRef.addValueEventListener(postListener)
    }

    fun pushUser(
        name: String, email: String, age: String, weight: String,
        arteriotony: String, phone: String)
    {
        var photoUrl:String = ""
        if(localUri!=null){
            photoUrl=localUri.toString()
        }
        val user =
            User(auth.currentUser!!.uid, photoUrl, name, email, age, weight, arteriotony, phone)
         myRef.child(auth.currentUser!!.uid).setValue(user)
    }

    fun saveAvatar(byteArray: ByteArray) {

        val localRef = storageRef.child(auth.currentUser!!.email.toString() + "Avatar")
        var uploadTask = localRef.putBytes(byteArray)

        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->

        }
            .addOnCompleteListener {
                localUri= it.result.uploadSessionUri
            }
        localRef.downloadUrl
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    localUri = task.result
                } else {
                }
            }




    }


    fun exit(){
        auth.signOut()
    }

}


