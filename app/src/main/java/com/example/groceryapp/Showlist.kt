package com.example.groceryapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.collections.ArrayList

class Showlist : AppCompatActivity() {

    lateinit var adapt: adapter;
    lateinit var recy: RecyclerView;
    lateinit var gridLayoutManager: GridLayoutManager;
    lateinit var database: DatabaseReference
    lateinit var datalist: ArrayList<listbasicinfo>
    lateinit var detailslist: ArrayList<listdetails>
    var data: Boolean = false
    lateinit var framelayouts: FrameLayout

    lateinit var images: ImageView
    lateinit var search: ImageView
    var code:String=""
var piclist:ArrayList<Any> = ArrayList()
    //lateinit var car:CardView
    var linkdata: Boolean = false
    private val TAG = "TAG"
    private val args by lazy {

    }

    private val fragmentManager = supportFragmentManager

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("RestrictedApi")
//    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        if (result.resultCode == Activity.RESULT_OK) {
//            // There are no request codes
//            val data: Intent? = result.data
//            handlingdynamiclink()
//        }
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
//        if(FirebaseAuth.getInstance().currentUser!==null)
//        {
//            val inten=Intent(this@Showlist,SIGNIN::class.java)
////            inten.putExtra("isFromDynamicLink", true)
//
//           startActivity(inten)
//            finish()
//        }
//    code= intent.getStringExtra("data").toString()
//if(code=="reg") {
//    handlingdynamiclink()
//
//}
        piclist.add(R.drawable.first)
        piclist.add(R.drawable.second)
        piclist.add(R.drawable.third)
        piclist.add(R.drawable.fourth)
        search = findViewById(R.id.imageView5)
        search.setOnClickListener {
            val searc = Intent(this@Showlist, com.example.groceryapp.search::class.java)
            startActivity(searc)
        }
        framelayouts = findViewById(R.id.framelayout)
//car=findViewById(R.id.parentcard)
        images = findViewById(R.id.fr)
        images.setOnClickListener {
            loadfragment()

        }
        datalist = arrayListOf<listbasicinfo>()
        detailslist = arrayListOf<listdetails>()
        recy = findViewById(R.id.recyclerView);
        getdatas()


//val data= ArrayList<_root_ide_package_.com.example.groceryapp.DatabaseModel>()
//        val createbutton=findViewById<Button>(R.id.button2)
//        adapt= adapter(data)
//        if(adapt.itemCount==0)
//        {
//            onempty()
//        }
//        else
//        {
//recy=findViewById(R.id.recyclerview);
//            recy.layoutManager=GridLayoutManager(applicationContext,2);
//            recy.adapter=adapt;
//
//        }
//
//    }
    }


    fun onempty() {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.framelayout, emptyfragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun loadfragment() {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.iconm, menu())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun add(view: View) {

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.framelayout, addelement2())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }

    fun getdatas() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        //for currently login person data assing to me part
        var id: String = ""
        val data2: String = ""
//        val listdetails: listdetails = listdetails()
        datalist = ArrayList()
        Log.d("profile", FirebaseAuth.getInstance().currentUser?.photoUrl.toString())

        FirebaseDatabase.getInstance().getReference("grocerylist").child("listbasicinfo")

            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    it.result.children.forEach { children ->
                        Log.d("key", children.key.toString())

                        children.child("members").children.forEach { member ->
                            if (member.child("uid").value.toString() == FirebaseAuth.getInstance().uid.toString()) {
                                id = children.key.toString()
                                Log.d("testing", id)
                                FirebaseDatabase.getInstance().getReference("grocerylist")
                                    .child("listbasicinfo").child(id)
                                    .addValueEventListener(object : ValueEventListener {
                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            if (snapshot.exists()) {
                                                val data2 =
                                                    snapshot.getValue(listbasicinfo::class.java)

                                                datalist.add(data2!!)

//                                                if (snapshot.hasChildren()) {
//                                                    for (item in snapshot.child("listdetails").children) {
//                                                        val data =
//                                                            item.getValue(listdetails::class.java)
//                                                        detailslist.add(data!!)
//                                                    }
//                                                    Log.d("testing", detailslist.toString())
//                                                } else {
//                                                    Log.d("testing", "o child")
//                                                }
//                                            } else {
//                                                Log.d("error", "error")
                                            }
                                            adapt = adapter(this@Showlist, datalist,piclist)
                                            recy.adapter = adapt
                                            recy.layoutManager =
                                                GridLayoutManager(applicationContext, 2)
                                            //recy.setHasFixedSize(true)

                                        }


                                        override fun onCancelled(error: DatabaseError) {
                                            TODO("Not yet implemented")
                                        }

                                    })

                            }

                            Log.d("key", member.child("uid").value.toString())

                        }
                    }
                }
            }


    }

    //@RequiresApi(Build.VERSION_CODES.O)
//    fun handlingdynamiclink() {
//        FirebaseDynamicLinks.getInstance().getDynamicLink(intent).addOnSuccessListener {
//            if (it != null) {
//                val id = it.link?.getQueryParameter("id")
//                Log.d("ids",id.toString())
//                if(id!=null)
//                {
//
//                    val member=member(FirebaseAuth.getInstance().uid.toString(),"Editor",LocalDateTime.now().toString())
//                    FirebaseDatabase.getInstance().getReference("grocerylist").child("listbasicinfo").child(id).child("members").push().updateChildren(member.toMap()).addOnSuccessListener {
//                        Toast.makeText(this, "sucessfully added to your list", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//        }
//    }
}


