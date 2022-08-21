package com.example.groceryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class donelistadapter(var list:ArrayList<String>):RecyclerView.Adapter<donelistadapter.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val title:TextView
        val item:TextView
        var check:CheckBox
        init {
            title=view.findViewById(R.id.textView9)

            item=view.findViewById(R.id.textView10)
            check=view.findViewById(R.id.checkBox)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.donelist,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
holder.item.text=list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }
}