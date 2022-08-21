package com.example.groceryapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class assingedadapter(val person:ArrayList<String>,val img:ArrayList<Any>,var c:Context,var adapter:Adaptercallbacks,var uid:ArrayList<String>): RecyclerView.Adapter<assingedadapter.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
val text:TextView
var imgs:ImageView
init {
    text=view.findViewById(R.id.textView3)
    imgs=view.findViewById(R.id.assinged)
}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view=LayoutInflater.from(parent.context).inflate(R.layout.spinneritem,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text=person[position]
        if(uid.size>0) {
            Glide.with(c).load(img[position]).into(holder.imgs);
        }
        else
        {
            holder.imgs.setImageResource(img[position] as Int)
        }
        holder.text.setOnClickListener {
           adapter.onclick(person[position],uid[position])




        }
        holder.imgs.setOnClickListener {
            if(uid.size==0)
            adapter.onclick(person[position],"0")
        }
    }

    override fun getItemCount(): Int {
        return person.size
    }
    public interface Adaptercallbacks
    {
        public fun onclick(text:String,uid:String)

    }



}