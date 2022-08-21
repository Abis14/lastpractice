package com.example.groceryapp


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class adapter(
    var c: Context,
    private var dataSet: ArrayList<listbasicinfo>,
    var piclist: ArrayList<Any>,
  // private var list: ArrayList<listdetails>
) :
    RecyclerView.Adapter<adapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false);
        return ViewHolder(view);
    }

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: adapter.ViewHolder, position: Int) {


        //val list=dataSet.get(position).Description?.split(",")?.toList()
        var i = 0


        holder.textView.text = dataSet[position].title

   holder.parentcard.setOnClickListener{
      val int=Intent(c,grocerylist::class.java)
       val gson:Gson=Gson()
       val data=gson.toJson(dataSet[position])


       int.putExtra("mydata",data)

       Log.d("ada",dataSet[position].title.toString())
       c.startActivity(int)

   }

        holder.rec.layoutManager =
            LinearLayoutManager(holder.rec.context, LinearLayoutManager.VERTICAL, false)

        val childList: java.util.ArrayList<listdetails> = java.util.ArrayList()

        dataSet[position].listdetails?.forEach {
            childList.add(it.value)
        }
        Log.d("checks",dataSet[position].listdetails.toString())
        val child: childadapterlass = childadapterlass(holder.rec.context, childList,dataSet[position].color!!)
        holder.rec.adapter = child
        holder.rec.setHasFixedSize(true)
        i++;

        Log.d("sizelist", i.toString())

          holder.parentcard.setCardBackgroundColor(Color.parseColor(dataSet[position].color).toInt())

       Log.d("memberc",dataSet[position].members?.count().toString())





                holder.recy.layoutManager=LinearLayoutManager(holder.recy.context,LinearLayoutManager.HORIZONTAL,false)
            Log.d("picsize",piclist.size.toString())
        var adap=adap(piclist,dataSet[position].members)
                holder.recy.adapter=adap





        }


    override fun getItemCount(): Int {
        return dataSet.size;

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val rec: RecyclerView
        val parentcard: CardView
        val recy:RecyclerView
// img:ImageView
        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.textView7)
            rec = view.findViewById(R.id.recchild)
            parentcard = view.findViewById(R.id.groceryparentcard)
   recy=view.findViewById(R.id.imgrec)
//img=view.findViewById(R.id.img)

        }
    }
}
