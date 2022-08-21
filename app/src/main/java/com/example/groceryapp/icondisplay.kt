package com.example.groceryapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [icondisplay.newInstance] factory method to
 * create an instance of this fragment.
 */
class icondisplay : Fragment() {
    // TODO: Rename and change types of parameters

lateinit var setting:ImageView
lateinit var share:ImageView
lateinit var delete:ImageView
    var deletes:Boolean=false
var title:String=""
    var arraylist:ArrayList<totext> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_icondisplay, container, false)
        val data=arguments?.get("title")
        FirebaseDatabase.getInstance().getReference("grocerylist").child("listbasicinfo").get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    it.result.children.forEach { child ->
                        if (child.child("title").value == data) {
                            val tid = child.key.toString()
                            FirebaseDatabase.getInstance().getReference("grocerylist")
                                .child("listbasicinfo").child(tid)
                                .addValueEventListener(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        val title =
                                            snapshot.child("title").getValue(String::class.java)
                                        val color =
                                            snapshot.child("color").getValue(String::class.java)

                                        if (snapshot.hasChildren()) {
                                            for (item in snapshot.child("listdetails").children) {
                                                val listdetails =
                                                    item.getValue(listdetails::class.java)
                                                Log.d("send",listdetails.toString())
                                                val totext=totext(title,color,listdetails?.Itemdetails,listdetails?.category,listdetails?.assinged)
                                                if(listdetails!=null)
                                                    arraylist.add(totext)
                                                Log.d("send",totext.toString())
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

//        val gson=GSON()
//        val listbasicinfo=gson.fromJson(data.toString(), listbasicinfo::class.java)
//        val list=totext(listbasicinfo.title.toString(),listbasicinfo.color.toString(),listbasicinfo.listdetails?.toString())
        setting=view.findViewById(R.id.setting)
        share=view.findViewById(R.id.astext)
        share.setOnClickListener {

            val senddata=Intent()
            senddata.action=Intent.ACTION_SEND
            senddata.putExtra(Intent.EXTRA_TEXT,arraylist.toString())
            senddata.putExtra(android.content.Intent.EXTRA_SUBJECT,"plzz")
            senddata.type = "text/plain"
            startActivity(senddata)
        }
        var bundle=arguments
        title=bundle?.getString("title").toString()
        delete=view.findViewById(R.id.deletelist)

        setting.setOnClickListener {
            val setting=Intent(activity,com.example.groceryapp.setting::class.java)
            startActivity(setting)

        }
        delete.setOnClickListener {
            dialog()
            if (deletes == true) {
                FirebaseDatabase.getInstance().getReference("grocerylist").child("listbasicinfo")
                    .get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        it.result.children.forEach { children ->
                            children.child("members").children.forEach { childrend ->
                                if (childrend.child("uid").value == FirebaseAuth.getInstance().uid.toString()) {
                                    if (childrend.child("role").value == "Admin") {
                                        if (children.child("title").value == title) {
                                            val id = children.key.toString()
                                            FirebaseDatabase.getInstance()
                                                .getReference("grocerylist")
                                                .child("listbasicinfo").child(id).removeValue()
                                        } else {
                                            Log.d("care", title)
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(activity, "Sorry you are not Admin", Toast.LENGTH_SHORT).show()
                                    }
                                }

                            }
                        }
                    }
                }//.child("listbasicinfo").orderByChild("title").equalTo(title)
            }
            else
            {

            }
        }

        return view
    }
fun dialog()
{
    val alertDialog=AlertDialog.Builder(activity)
    alertDialog.setMessage("Are you sure you want to delete this list" +
            "all member invited to this list " +
            "will loose acces to it")
    alertDialog.setPositiveButton("Yes"){dialogInterface, which ->
        deletes=true
    }
    alertDialog.setNegativeButton("No"){dialogInterface,which->
        deletes=false
    }
    alertDialog.show()

}

}