package com.example.groceryapp

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class searchchildadapter(context:android.content.Context,private var dataSet: List<listdetails>?,var color:String) :
RecyclerView.Adapter<searchchildadapter.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val textView: TextView
        val check: CheckBox
        val childcar: CardView
        init {
            textView=view.findViewById(R.id.item12)
            check=view.findViewById(R.id.checkBox12)
            childcar=view.findViewById(R.id.searchchildcard)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.searchchildcard,parent,false);
        return searchchildadapter.ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text= dataSet!!.get(position).Itemdetails
        if(dataSet!![position].done=="true")
        {
            holder.check.isChecked=true
        }
//        holder.childcar.setCardBackgroundColor(Color.parseColor(color))
        Log.d("check","child recycler view")
    }


    override fun getItemCount(): Int {
       return dataSet!!.size
    }
}