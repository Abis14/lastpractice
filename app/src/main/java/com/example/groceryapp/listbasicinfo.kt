package com.example.groceryapp


import kotlin.collections.HashMap

data class listbasicinfo(var title:String?="",var color:String?="", var listdetails:HashMap<String, listdetails>? = HashMap(),var members:HashMap<String,member>?=HashMap()) {


    fun toMap(): Map<String, Any?> {

        return mapOf<String, Any?>(
            "title" to title,
            "color" to color,
            "listdetails" to listdetails
        )
    }


}