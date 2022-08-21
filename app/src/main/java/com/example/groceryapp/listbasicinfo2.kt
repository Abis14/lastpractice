package com.example.groceryapp

data class listbasicinfo2(var title:String?="",var color:Any?="")
{
    fun toMap(): Map<String, Any?> {

        return mapOf<String, Any?>(
            "title" to title,
            "color" to color

        )
    }
}
