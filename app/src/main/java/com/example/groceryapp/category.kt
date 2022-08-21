package com.example.groceryapp

data class category(val category: String?="") {
    fun toMap(): Map<String, Any?> {

        return mapOf<String, Any?>(

            "category" to category

        )
    }
}