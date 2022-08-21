package com.example.groceryapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.net.URL
import java.time.LocalDateTime


class SIGNIN : AppCompatActivity() {
    companion object {
        val Req_Code: Int = 123;
    }

    lateinit var database: FirebaseDatabase
    lateinit var dref: DatabaseReference

    @RequiresApi(Build.VERSION_CODES.O)
    val getaction = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        if (Req_Code ==123) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {
                    val account = task.getResult(ApiException::class.java)
                    firebaseauthwithgoogle(account.idToken)
                } catch (e: ApiException) {
                    Toast.makeText(this, "Sorry Authentication failed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.d("excep", exception.toString())
            }
        } else {
            Toast.makeText(this, "noooo", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun firebaseauthwithgoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                saveuserdata()

                val intent = Intent(this, Showlist::class.java)
                intent.putExtra("data","reg")
                startActivity(intent)
                finish()
val result:Intent=Intent()
                setResult(Activity.RESULT_OK)
                finish()

            }
        }


    }

    lateinit var GoogleSignInClient: GoogleSignInClient
    lateinit var auth: FirebaseAuth;
    lateinit var callbackManager: CallbackManager
    var user: FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        GoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun googlesign(view: View) {


        val signInIntent: Intent = GoogleSignInClient.signInIntent
        getaction.launch(signInIntent);


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveuserdata() {

var exist:Int=0
        var name:String=""
        var email:String=""
        var id:String=""
var img: String? =null
        database = FirebaseDatabase.getInstance()
        dref = database.getReference("Users")
        val user = Firebase.auth.currentUser

        user?.let {
            for (profile in it.providerData) {
                name = auth.currentUser?.displayName.toString()
                email = auth.currentUser?.email.toString()
                id = auth.currentUser?.uid.toString()
                img= auth.currentUser?.photoUrl.toString()
            }
            FirebaseDatabase.getInstance().getReference("Users").child(auth.uid.toString()).get().addOnCompleteListener {
                if(it.isSuccessful)
                {
                    if (!it.result.exists()) {

                        val uses = user(name, email, id,img!!)

                        dref.child(auth.uid.toString()).updateChildren(uses.toMap()).addOnSuccessListener {
                            Toast.makeText(this, "Suceesfully registered", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {
                            Toast.makeText(this, "Sorry try again", Toast.LENGTH_SHORT).show()
                        }
                    }
                    /*it.result.children.forEach { children->

                        if( children.child("Email").value==email) {
                            exist++


                        }

                    }*/
                }
            }
//            val uses = user(name, email, id,img!!)
            //val uid = dref.push().key!!
            /*if (exist==0) {
                Log.d("inside",exist.toString())
                dref.child(auth.uid.toString()).updateChildren(uses.toMap()).addOnSuccessListener {
                    Toast.makeText(this, "Suceesfully registered", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Sorry try again", Toast.LENGTH_SHORT).show()
                }

            }*/
        }

val linkid=intent.getStringExtra("id").toString()
        if(linkid!="no") {
            val member = member(
                FirebaseAuth.getInstance().uid.toString(), "Editor",
                LocalDateTime.now().toString()

            )
            Log.d("linkid",linkid.toString())
            FirebaseDatabase.getInstance().getReference("grocerylist").child("listbasicinfo")
                .child(linkid).child("members").push().updateChildren(member.toMap())
                .addOnSuccessListener {
                    Toast.makeText(this, "sucessfully added to your list", Toast.LENGTH_SHORT)
                        .show()
                }

        }
//        }
// name=auth.currentUser?.displayName
//         email=auth.currentUser?.email
//        val id=auth.currentUser?.uid
//        val pic=auth.currentUser?.photoUrl
//        val uses=user(name, email,id,pic)
//


        }
    }



