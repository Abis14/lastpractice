package com.example.groceryapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class adap(var piclist: ArrayList<Any>, var count: HashMap<String, member>?):RecyclerView.Adapter<adap.ViewHolder>()
{
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
val img:ImageView
init {
    img=view.findViewById(R.id.imageView21)
}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adap.ViewHolder {
      val view=LayoutInflater.from(parent.context).inflate(R.layout.personimages,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: adap.ViewHolder, position: Int) {
       holder.img.setImageResource(piclist[position].hashCode())





    }

    override fun getItemCount(): Int {

        return count?.count()!!
    }
}