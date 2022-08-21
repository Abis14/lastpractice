package com.example.groceryapp

import java.util.ArrayList
import java.util.HashMap

data class DatabaseModel(
    val title:String,
    val description: String?
) {

    fun toMap(): Map<String, Any?> {

        return mapOf<String, Any?>(
            "Title" to title,
            "Description" to description
        )
    }

}
