package com.example.groceryapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.ktx.Firebase

class share : AppCompatActivity() {
    var id: String = ""
    lateinit var text: TextView
    lateinit var button: Button
    var link: String = ""
    var title = ""
    var isshown:Boolean=false
    var email: ArrayList<String> = ArrayList()
    var dp: ArrayList<Any> = ArrayList()
    var uid: ArrayList<String> = ArrayList()
    lateinit var toolbartext:TextView
    lateinit var back:ImageView
    var fragmentmanager = supportFragmentManager
    lateinit var recy: RecyclerView
    lateinit var toolbar:Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)
        title = intent.getStringExtra("title").toString()
        recy = findViewById(R.id.recyclerView2)
        toolbar=findViewById(R.id.toolbar2)
        toolbartext=findViewById(R.id.textView16)
        back=findViewById(R.id.imageView12)


        FirebaseDatabase.getInstance().getReference("grocerylist").child("listbasicinfo")
            .orderByChild("title").equalTo(title).get().addOnCompleteListener {
                if (it.isSuccessful) {
                    it.result.children.forEach { children ->
                        id = children.key.toString()
                        Log.d("link",id)
                        Loadfragment()
                        gettingmemberlist()
                    }

                }


            }


//            val mydata = "hi please bring these grocery ".toString()
//            text.text = d.toString()
//
//            val action = Intent()
//            action.action = Intent.ACTION_SEND
//            action.putExtra(Intent.EXTRA_SUBJECT, mydata)
//            action.putExtra(Intent.EXTRA_TEXT, d.toString())
//            action.type = "text/plain"
//
//            startActivity(action)

    }

    fun createshareuri(id: String): Uri {
        val builder = Uri.Builder()
        builder.scheme("http")
        builder.authority("groceryapp")
            .appendPath("grocerylist")
            .appendQueryParameter("id", id)
        return builder.build()
    }

    fun dynamiclink(myuri: Uri): Uri {
        val dynamiclink = FirebaseDynamicLinks.getInstance().createDynamicLink().setLink(myuri)
            .setDomainUriPrefix("https://abis.page.link/")
            .setAndroidParameters(DynamicLink.AndroidParameters.Builder().build())
            .buildDynamicLink()
        return dynamiclink.uri
    }

    fun Loadfragment() {
        val myuri = createshareuri(id)
        link = dynamiclink(myuri).toString()

        val fragmenttransaction = fragmentmanager.beginTransaction()
        val linkfragment = linkfragment()
        val bundle = Bundle()
        bundle.putString("link", link)
        linkfragment.arguments = bundle
        fragmenttransaction.replace(R.id.showlink, linkfragment)
        fragmenttransaction.commit()
    }



        fun gettingmemberlist() {
            FirebaseDatabase.getInstance().getReference("grocerylist").child("listbasicinfo")
                .orderByChild("title").equalTo(title).get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        it.result.children.forEach { children ->
                            if (children.child("title").value == title) {
                                children.child("members").children.forEach { childa ->

                                    Log.d("uid", childa.child("uid").value.toString())

                                    FirebaseDatabase.getInstance().getReference("Users")
                                        .addValueEventListener(object :
                                            ValueEventListener {
                                            override fun onDataChange(snapshot: DataSnapshot) {
                                                for (item in snapshot.children) {
                                                    if (item.child("Id").value == childa.child("uid").value) {
                                                        email.add(item.child("Name").value.toString())
                                                        Log.d("emails",email.toString())
                                                        dp.add((item.child("img").value.toString()))
                                                        uid.add(childa.child("uid").value.toString())
                                                        val adapter = personadapter(
                                                            this@share,
                                                            email,
                                                            dp,
                                                            uid

                                                        )
                                                        recy.layoutManager = LinearLayoutManager(
                                                            this@share,
                                                            LinearLayoutManager.VERTICAL,
                                                            false
                                                        )
                                                        recy.adapter = adapter
                                                    } else {
                                                        Log.d("no", "nochild")
                                                    }
                                                }
                                            }

                                            override fun onCancelled(error: DatabaseError) {
                                                TODO("Not yet implemented")
                                            }

                                        })
                                }
                            }
                        }
                    }
                }
        }
    }

