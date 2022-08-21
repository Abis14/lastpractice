package com.example.groceryapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_grocerylist.*
import kotlinx.android.synthetic.main.fragment_editfragment.*

class grocerylist : AppCompatActivity(),grocerylistparentadapter.Adaptercallback {
    lateinit var Assingedtoall: Button
    lateinit var Assingedtome: Button


    var lists: String? = null
    var counters: Int = 0
    var ids: String = ""
    lateinit var addbtn:Button
    lateinit var img: ImageView
   lateinit var ic:ImageView
   lateinit var mainla:ConstraintLayout
    lateinit var cancel: ImageView
    lateinit var icon:ImageView
    lateinit var title: TextView
    lateinit var databaseref: DatabaseReference
    lateinit var listdetails: listbasicinfo
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var rey: RecyclerView
    lateinit var data: ArrayList<listbasicinfo>
    lateinit var adapter: grocerylistparentadapter
    private val myMap: LinkedHashMap<String, java.util.ArrayList<listdetails>> = LinkedHashMap()
    lateinit var assingedtoall: assingedtoalllist
    lateinit var delete: ImageView
    var fragmentManager = supportFragmentManager
lateinit var frame:FrameLayout
    lateinit var listbasicinfome: listbasicinfo
    private val myMapme: LinkedHashMap<String, java.util.ArrayList<listdetails>> = LinkedHashMap()

    @SuppressLint("Range", "RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grocerylist)
        toolbar = findViewById(R.id.my)


        cancel = findViewById(R.id.imageView14)
        img = findViewById(R.id.action)

        Assingedtome = findViewById(R.id.button6)
        Assingedtome.setOnClickListener {
            Assingedtome.setBackgroundResource(R.drawable.border)
            assingtomelist()
        }
        frame=findViewById(R.id.unique)
addbtn=findViewById(R.id.button10)
        ic=findViewById(R.id.imageView8)
        title = findViewById(R.id.textView12)
        Assingedtoall = findViewById(R.id.button4)
        rey = findViewById(R.id.assingedparent)
        data = java.util.ArrayList()
        mainla=findViewById(R.id.main)
        val gson: Gson = Gson()
        val list = intent.getStringExtra("mydata")
        lists = list
icon=findViewById(R.id.imageView8)
        icon.setOnClickListener {
            laodfragment()
        }
        delete=findViewById(R.id.delete)
        delete.setOnClickListener {
            deleteoperation()


        }
        listdetails = gson.fromJson(list, listbasicinfo::class.java)
//        toolbar.title=listdetails.title
//        toolbar.setBackgroundColor(Color.parseColor(listdetails.color))
//        supportActionBar?.setDisplayShowTitleEnabled(false)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        title.text = listdetails.title.toString()
        databaseref = FirebaseDatabase.getInstance().getReference("grocerylist")
            .child("listbasicinfo")
        databaseref.orderByChild("title")
            .equalTo(listdetails.title.toString()).get().addOnCompleteListener {
                if (it.isSuccessful) {
                    it.result.children.forEach { children ->
                        ids = children.key.toString()
                        Log.d("parent", ids.toString())
                    }
                }

            }
        listdetails.listdetails?.forEach {

Log.d("okkk","kk")
//            myMap[it.value.category]?.add(it.value.Itemdetails.toString())
            myMap.put(it.value.category.toString(), java.util.ArrayList())


        }
        listdetails.listdetails?.forEach {

            Log.d("TAG", "onCreateViewss: ${it.value.category}")

            myMap.get(it.value.category.toString())?.add(it.value)

            assingedtoall = assingedtoalllist()
            Assingedtoall.setOnClickListener {
                Assingedtoall.setBackgroundResource(R.drawable.border)
                data.add(listdetails)
                myMap.put("Done", java.util.ArrayList())


                adapter = grocerylistparentadapter(myMap, listdetails, this, this)

                rey.adapter = adapter
                rey.layoutManager =
                    LinearLayoutManager(this@grocerylist, LinearLayoutManager.VERTICAL, false)

            }
            cancel.setOnClickListener {
                oncancel(listdetails.title.toString(), listdetails.color.toString())
            }

        }
//    fun loadfragment()
//    {
//        var fragmenttransaction=fragmentManager.beginTransaction()
//        val bundle:Bundle= Bundle()
//        bundle.putString("mydata",lists)
//        assingedtoall.arguments=bundle
//        fragmenttransaction.replace(R.id.fr2,assingedtoall)
//        fragmenttransaction.commit()
//    }
        addbtn.background.setColorFilter(Color.parseColor(listdetails.color.toString()), PorterDuff.Mode.ADD);
        var  mfragment=groceryadd()
        addbtn.setOnClickListener {

            val fragmentTransaction = fragmentManager.beginTransaction()
            val bundle=Bundle()
            bundle.putString("title",listdetails.title)
            mfragment.arguments=bundle

            fragmentTransaction.replace(R.id.unique, mfragment)
          getActivity(this)?.getWindow()?.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            //frame.background.setAlpha(220);

            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
     img.setOnClickListener {
       val intents= Intent(this,share::class.java)
           intents.putExtra("title",listdetails.title)
          startActivity(intents)
      }
        ic.setOnClickListener {
            var icondisplay=icondisplay()
            val fragmentTransaction=fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.showmenu,icondisplay)
            val bundle=Bundle()
            bundle.putString("title",listdetails.title)
            icondisplay.arguments=bundle
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

    }

    private fun laodfragment() {
        var gson=Gson()
        val data=gson.toJson(listdetails)
        val bundle=Bundle()
        bundle.putString("share",data)
        val icon=icondisplay()
        icon.arguments=bundle
        val fragmenttrans=supportFragmentManager.beginTransaction()
        fragmenttrans.replace(R.id.showmenu,icon)
        fragmenttrans.commit()
    }

    private fun assingtomelist() {
        FirebaseDatabase.getInstance().getReference("grocerylist")
            .child("listbasicinfo").get().addOnCompleteListener {
                if(it.isSuccessful)
                {
                    it.result.children.forEach {
                        if(it.child("title").value==listdetails.title)
                        {
                            it.child("listdetails").children.forEach { childed->
                                if(childed.child("assinged").value==FirebaseAuth.getInstance().currentUser?.uid.toString())
                                {
                                    val mainid=it.key.toString()
                                    FirebaseDatabase.getInstance().getReference("grocerylist").child("listbasicinfo").child(mainid).addValueEventListener(object:ValueEventListener{
                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            val data=snapshot.getValue(listbasicinfo::class.java)
                                            data?.listdetails?.forEach {


//            myMap[it.value.category]?.add(it.value.Itemdetails.toString())
                                                myMap.put(it.value.category.toString(), java.util.ArrayList())


                                            }
                                            data?.listdetails?.forEach {

                                                Log.d("TAG", "onCreateViewss: ${it.value.category}")

                                                myMap.get(it.value.category.toString())
                                                    ?.add(it.value)
                                                adapter= grocerylistparentadapter(myMap,data,this@grocerylist,this@grocerylist)
                                                myMap.put("Done", java.util.ArrayList())
                                                rey.adapter = adapter
                                                rey.layoutManager =
                                                    LinearLayoutManager(this@grocerylist, LinearLayoutManager.VERTICAL, false)
                                            }

                                        }

                                        override fun onCancelled(error: DatabaseError) {
                                            TODO("Not yet implemented")
                                        }

                                    })
                                    Log.d("mainid",mainid)
                                }
                            }

                        }
                    }
                }
            }

    }







    fun settoolbar() {

    }

    override fun onchange(count: Int) {
        toolbar.setBackgroundColor(Color.parseColor("#ffffff"))
        setSupportActionBar(toolbar)
        title.visibility = View.VISIBLE
        cancel.visibility = View.VISIBLE
        supportActionBar?.title = ""
        cancel.setImageResource(R.drawable.cancel)
        counters += count
        var dis=counters.toString()

        title.text = dis.plus("ItemSelected")
       delete.visibility=View.VISIBLE
        img.visibility=View.GONE


    }

    override fun oncancel(titles: String, color: String) {
        toolbar.setBackgroundColor(Color.parseColor(color))
        setSupportActionBar(toolbar)
        counters = 0
        cancel.visibility = View.VISIBLE
        supportActionBar?.title = titles

        title.visibility = View.GONE
        cancel.visibility = View.GONE
        img.setImageResource(R.drawable.shareicon)
        adapter = grocerylistparentadapter(myMap, listdetails, this, this)
        rey.adapter = adapter
    }

    fun deleteoperation() {

        var itemlist: ArrayList<String> = adapter.getlistchild()

        var categorylist: ArrayList<String> = adapter.getparentlist()


        var childid: String = ""


        if (categorylist.size != 0) {
            for (item in categorylist) {
                databaseref = FirebaseDatabase.getInstance().getReference("grocerylist")
                    .child("listbasicinfo")
                databaseref
                    .child(ids).child("listdetails").orderByChild("category")
                    .equalTo(item).get().addOnCompleteListener {
                        if (it.isSuccessful) {
                            it.result.children.forEach { children ->
                                val cid = children.key.toString()
                                Log.d("ids", cid)
                                FirebaseDatabase.getInstance().getReference("grocerylist")

                                    .child("listbasicinfo").child(ids).child("listdetails")
                                    .child(cid)
                                    .addValueEventListener(object : ValueEventListener {
                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            for (item in snapshot.children) {
                                                item.ref.removeValue()
                                            }
                                        }

                                        override fun onCancelled(error: DatabaseError) {
                                            TODO("Not yet implemented")
                                        }

                                    })


                            }
                        } else {
                            Log.d("failed", "failes")
                        }


                    }
            }

        }

        for (item in itemlist) {
            Log.d("itemlist", itemlist.size.toString())
            databaseref = FirebaseDatabase.getInstance().getReference("grocerylist")
                .child("listbasicinfo")
            databaseref
                .child(ids).child("listdetails").orderByChild("Itemdetails")
                .equalTo(item).get().addOnCompleteListener {
                    if (it.isSuccessful) {

                        it.result.children.forEach { children ->
                            val itemid = children.key.toString()
                            Log.d("itemid", itemid)
                            FirebaseDatabase.getInstance().getReference("grocerylist")

                                .child("listbasicinfo")
                                .child(ids).child("listdetails").child(itemid)
                                .addValueEventListener(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        for (data in snapshot.children) {
                                            data.ref.removeValue()
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

    override fun edit(title: String, itemname: String, category: String, assined: String) {
        val mfr=editfragment()
        var us=user()



        BottomSheetBehavior.from(unique).apply {
            peekHeight=450
            this.state= BottomSheetBehavior.STATE_COLLAPSED
        }
        val fragmentransaction=fragmentManager.beginTransaction()
        fragmentransaction.replace(R.id.unique,mfr)
        val bundle=Bundle()
        bundle.putString("title",title)
        bundle.putString("item",itemname)
        bundle.putString("category",category)
        Log.d("jhon",assined)
        bundle.putString("selected",assined)
        mfr.arguments=bundle
        addbtn.visibility=  View.GONE
        addbtn.isEnabled=false
        fragmentransaction.commit()
    }


}


