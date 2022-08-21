package com.example.groceryapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class personadapter(val context:Context,val person:ArrayList<String>,val pic:ArrayList<Any>,val uid:ArrayList<String>):RecyclerView.Adapter<personadapter.ViewHolder>() {
    class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        val text:TextView
        val img:ImageView
        val cancel:ImageView
        init {
            text=view.findViewById(R.id.textView3)
            img=view.findViewById(R.id.assinged)
            cancel=view.findViewById(R.id.imageView6)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.spinneritem,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.text.text=person[position]
        Glide.with(context).load(pic[position]).into(holder.img);
    }

    override fun getItemCount(): Int {
        return person.size
    }
}