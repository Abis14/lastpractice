package com.example.groceryapp

import android.net.Uri
import java.net.URI
import java.net.URL

data class user(var Name:String?="", var Email:String?="", var Id:String?="",var img:String?="") {
    fun toMap(): Map<String, Any?> {

        return mapOf<String, Any?>(
            "Name" to Name,
            "Email" to Email,
        "Id" to Id,
            "img" to img

        )
    }
}
