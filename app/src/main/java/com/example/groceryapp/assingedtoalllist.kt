package com.example.groceryapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [assingedtoalllist.newInstance] factory method to
 * create an instance of this fragment.
 */
class assingedtoalllist : Fragment() {
lateinit var rey:RecyclerView
lateinit var data:ArrayList<listbasicinfo>
lateinit var adapter: grocerylistparentadapter

private val myMap : LinkedHashMap<String, java.util.ArrayList<listdetails>> =LinkedHashMap()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_assingedtoalllist, container, false)
        rey=view.findViewById(R.id.assingedparentrec)
        var bundle:Bundle?=arguments
        data=java.util.ArrayList()
        val list= bundle?.get("mydata").toString()
        val gson:Gson= Gson()
        var listdetails:listbasicinfo=gson.fromJson(list,listbasicinfo::class.java)
        val title:String=listdetails.title.toString()

        Log.d("TAG", "onCreateView: ${listdetails.listdetails?.size}")

       listdetails.listdetails?.forEach {


//            myMap[it.value.category]?.add(it.value.Itemdetails.toString())
myMap.put(it.value.category.toString(),java.util.ArrayList())


        }
        listdetails.listdetails?.forEach {

                Log.d("TAG", "onCreateViewss: ${it.value.category}")

                myMap.get(it.value.category.toString())?.add(it.value)

//            else
//            {
//                myMap.put(it.value.category.toString(),java.util.ArrayList())
//                myMap.get(it.value.category.toString())?.add(it.value.Itemdetails.toString())
//                Log.d("TAG", "onCreateViewssy: ${it.value.category}")
//            }
        }




            Log.d("TAG", "onCreateViewss: ${myMap}")



        data.add(listdetails)
        myMap.put("Done",java.util.ArrayList())

//
//        adapter=grocerylistparentadapter(myMap,listdetails)
//            rey.adapter=adapter
        rey.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        return view

    }


}