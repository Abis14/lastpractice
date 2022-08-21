package com.example.groceryapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import java.time.LocalDateTime


class MainActivity : AppCompatActivity() {
    lateinit var auth:FirebaseAuth
 var user: FirebaseUser? = null
    var id:String=""
    var check:Boolean=false
    var isadded:Boolean=false
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth=FirebaseAuth.getInstance()
        user=auth.currentUser

           if (user != null) {
               val intent = Intent(this@MainActivity, Showlist::class.java)

               handlingdynamiclink()
               startActivity(intent)
           } else {

               val signin = Intent(this, SIGNIN::class.java)

                   signin.putExtra("id", id)
                   Log.d("checks", "no")
               startActivity(signin)
               }







    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun handlingdynamiclink() {
        FirebaseDynamicLinks.getInstance().getDynamicLink(intent).addOnSuccessListener {
            if (it != null) {
                 id = it.link?.getQueryParameter("id").toString()
                Log.d("ids",id.toString())


                    check=true
                    val member=member(FirebaseAuth.getInstance().uid.toString(),"Editor",
                        LocalDateTime.now().toString())
                    FirebaseDatabase.getInstance().getReference("grocerylist").child("listbasicinfo").child(id).child("members").push().updateChildren(member.toMap()).addOnSuccessListener {
                        Toast.makeText(this, "sucessfully added to your list", Toast.LENGTH_SHORT).show()
                    }

            }
        }
    }
}