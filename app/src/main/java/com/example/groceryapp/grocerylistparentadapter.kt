package com.example.groceryapp

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import java.util.HashMap
import kotlin.collections.ArrayList

class grocerylistparentadapter(var dataset: HashMap<String, ArrayList<listdetails>>,var listbasicinfo: listbasicinfo,var Adapter:Adaptercallback,var c:Context): RecyclerView.Adapter<grocerylistparentadapter.ViewHolder>() {
    var isselected:Boolean=false
    var counters:Int=0
    var selecteditem:ArrayList<String> = ArrayList()
    lateinit var childadapter: gocerylistchildadapter
    class ViewHolder(val view:View): RecyclerView.ViewHolder(view) {
val title:TextView
val childrecy:RecyclerView
val card:CardView


        //var list:ArrayList<String> = ArrayList()

        init {
    title=view.findViewById(R.id.textView6)
    childrecy=view.findViewById(R.id.grocerylistchildrec)
            card=view.findViewById(R.id.groceryparentcard)

}

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view=LayoutInflater.from(parent.context).inflate(R.layout.grocerylistparentcard,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        var childlist: java.util.ArrayList<listdetails> = java.util.ArrayList()
//        Log.d("mylist", dataset[position].listdetails.toString())
//        dataset[position].listdetails?.forEach {
//            childlist.add(it.value)

        var key:MutableList<String>
        key=dataset.keys.toMutableList()

        Log.d("jjjj",key.toString())
        var categorylis: java.util.ArrayList<String> = java.util.ArrayList()
        holder.title.setBackgroundColor(Color.parseColor(listbasicinfo.color.toString()))
if(key[position].toString()=="Done")
{
    listbasicinfo.listdetails?.forEach {
        if(it.value.done=="true") {
            dataset.get("Done")?.add(it.value)
            holder.title.text = key[position].toString()
            holder.title.setBackgroundColor(Color.parseColor(listbasicinfo.color.toString()))
             childadapter =
                gocerylistchildadapter(dataset.getValue(key[position]), listbasicinfo.title!!, Adapter,c,)
            holder.childrecy.adapter = childadapter
            holder.childrecy.layoutManager =
                LinearLayoutManager(holder.childrecy.context, LinearLayoutManager.VERTICAL, false)
        }

    }
}
        else {
    holder.title.text = key[position].toString()
    childadapter =
        gocerylistchildadapter(dataset.getValue(key[position]), listbasicinfo.title!!,Adapter,c)
    holder.childrecy.adapter = childadapter
    holder.childrecy.layoutManager =
        LinearLayoutManager(holder.childrecy.context, LinearLayoutManager.VERTICAL, false)
}
holder.title.setOnLongClickListener {
    if(isselected==true)
    {
        if(selecteditem.contains(key[position])) {
            selecteditem.remove(key[position])
            counters--
            holder.title.setBackgroundColor(Color.parseColor("#ffffff"))
            //counter(counters)
Adapter.onchange(-1)
        }
        else
        {
            selecteditem.add(key[position])
            counters++
            Adapter.onchange(1)
            holder.title.setBackgroundColor(Color.parseColor("#FFFFF7"))
        }


    }
    else
    {
        isselected=true
        counters++
        Adapter.onchange(1)
        holder.title.setBackgroundColor(Color.parseColor("#FFFFF7"))
        selecteditem.add(key[position])


        //counter(counters)
//        val grocerylist:grocerylist= c as grocerylist
//        grocerylist.supportActionBar?.title= counters.toString()
//        grocerylist.  supportActionBar?.setDisplayShowTitleEnabled(false)

    }

    true
}
        //holder.card.setCardBackgroundColor(Color.parseColor(listbasicinfo.color.toString()))
        }


    override fun getItemCount(): Int {
   return dataset.size
    }
    fun getlistchild():ArrayList<String>
    {
        return childadapter.getlist()
    }
    fun getparentlist():ArrayList<String>
    {
        return selecteditem
    }
    public interface Adaptercallback
    {
        public fun onchange(count:Int)
        public fun oncancel(title:String,color:String)
        public fun edit(title:String,itemname:String,category:String,assined:String)
    }



}