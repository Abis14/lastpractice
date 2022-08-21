package com.example.groceryapp

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import kotlin.collections.ArrayList

class gocerylistchildadapter(var dataset: ArrayList<listdetails>,var title:String,val inter:grocerylistparentadapter.Adaptercallback,var c:Context):RecyclerView.Adapter<gocerylistchildadapter.ViewHolder>() {
    var isselected:Boolean=false
    var selectedlist:ArrayList<String> = ArrayList()
    var counter:Int=0
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val check: CheckBox
        val text: TextView
val img:ImageView
val edit:ImageView
        init {
            check = view.findViewById(R.id.itemcheck)
            text = view.findViewById(R.id.textView8)
img=view.findViewById(R.id.assinged)
            edit=view.findViewById(R.id.edit)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.grocerylistchildcard, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            var ids: String = ""

            holder.text.text = dataset[position].Itemdetails
            if(dataset[position].done=="true")
            {
                holder.check.isChecked=true
            }
            holder.check.setOnClickListener {
                if (holder.check.isChecked) {
                    Log.d("checked","its checked")

                    var ref = FirebaseDatabase.getInstance().getReference("grocerylist")
                        .child("listbasicinfo").get().addOnCompleteListener {

                            if(it.isSuccessful)
                            {

                                it.result.children.forEach { children->
                                    val ids=children.key.toString()
                                    Log.d("checkk", ids)
                                    if(children.child("title").value==title) {
                                        children.child("listdetails").children.forEach { category ->
                                            if (category.child("category").value == dataset[position].category&&category.child("Itemdetails").value==dataset[position].Itemdetails.toString()) {
                                                val id = category.key.toString()
                                                Log.d("checkk", id)
                                                FirebaseDatabase.getInstance().getReference("grocerylist").child("listbasicinfo").child(ids).child("listdetails").child(id).child("done").setValue("true")
                                            }

                                        }

                                    }

                                    }
                                }
                            }
                        }
//
//                    ref.child("listbasicinfo").orderByChild("title").equalTo(title)
//                        .get()
//                        .addOnCompleteListener {
//                            if (it.isSuccessful) {
//
//                                it.result.children.forEach { children ->
//
//                                    ids = children.key.toString()
//                                    Log.d("ids",ids)
//
//                                }
//                            }
//                        }
//                    Log.d("run",dataset[position].category.toString())
//                    ref=FirebaseDatabase.getInstance().getReference("Users")
//                        .child(FirebaseAuth.getInstance().uid.toString())
//                    ref.child("listbasicinfo")
//                        .child(ids).child("listdetails").orderByChild("category")
//                        .equalTo(dataset[position].category).get().addOnCompleteListener {
//                            if(it.isSuccessful) {
//                                it.result.children.forEach { children ->
//                                    val id = children.key.toString()
//                                    Log.d("ids", id)
//                                    FirebaseDatabase.getInstance().getReference("Users")
//                                        .child(FirebaseAuth.getInstance().uid.toString())
//                                        .child("listbasicinfo").child(ids).child("listdetails")
//                                        .child(id).child("done").setValue("true")
//
//
//                                }
//                            }
//                            else
//                            {
//                                Log.d("failed","failes")
//                            }
//
//
//                        }
                }


            holder.text.setOnLongClickListener {
                if(isselected==true)
                {
                    if(selectedlist.contains(holder.text.text))
                    {
                        selectedlist.remove(holder.text.text)

                        holder.text.setBackgroundColor(Color.parseColor("#FFFFFF"))
                        inter.onchange(-1)
                    }
                    else
                    {
                        selectedlist.add(holder.text.text.toString())
                        counter++
                        holder.text.setBackgroundColor(Color.parseColor("#FFFFF7"))
                        inter.onchange(1)
                    }
                }
                else
                {
                    isselected=true
                    counter++
                    inter.onchange(1)
                    selectedlist.add(dataset[position].Itemdetails!!)
                    holder.text.setBackgroundColor(Color.parseColor("#FFFFF7"))


                }
                true
            }
        val uid=dataset[position].assinged
        Log.d("uid",uid.toString())
        FirebaseDatabase.getInstance().getReference("Users").child(uid.toString()).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val member=snapshot.getValue(user::class.java)
                Log.d("photo",member.toString())
                Glide.with(c).load(member?.img).into(holder.img);
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        holder.edit.setOnClickListener {
            inter.edit(title,dataset[position].Itemdetails.toString(),dataset[position].category.toString(),dataset[position].assinged.toString())
        }
        }


        override fun getItemCount(): Int {
            return dataset.size
        }
    fun getlist():ArrayList<String>
    {
        return selectedlist
    }

  public interface childAdaptercallback
    {

        public fun edit(title:String,itemname:String,category:String,assined:String)
    }

}