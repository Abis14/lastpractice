package com.example.groceryapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class spinneradapter(var c:Context, var fruits:ArrayList<String>, var imgs: ArrayList<Any>):
    BaseAdapter() {
    override fun getCount(): Int {
       return fruits.size
    }

    override fun getItem(p0: Int): Any {
        return fruits[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
      var view:View=LayoutInflater.from(p2?.context).inflate(R.layout.spinneritem,p2,false)
        var imageView:ImageView=view.findViewById(R.id.assinged)
        var text:TextView=view.findViewById(R.id.textView3)
        text.setOnClickListener {
            imageView.visibility=View.GONE
        }

Log.d("sie",imgs.size.toString())
       // Picasso.get().load(imgs[p0].toString()).resize(200, 200).into(imageView);
        Glide.with(c).load(imgs[p0]).into(imageView);
        text.setText(fruits[p0])
        return view
    }
}