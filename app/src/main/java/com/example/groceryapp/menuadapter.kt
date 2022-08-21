package com.example.groceryapp


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class menuadapter (private var dataSet: ArrayList<String>,private var img: ArrayList<Any>) :
    RecyclerView.Adapter<menuadapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): menuadapter.ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.itemicon,parent,false);
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text=dataSet.get(position)
        holder.image.setImageResource(img.get(position).hashCode())
    }

    override fun getItemCount(): Int {
        return dataSet.size;

    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val image:ImageView

        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.menuitemname)
            image=view.findViewById(R.id.menuitem)




        }
    }


}
