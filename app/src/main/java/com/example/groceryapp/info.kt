package com.example.groceryapp

data class info(var Title:String?=null,var color:String?=null) {
    fun get() = Title

    // setter
    fun set(value: String) {
        Title = value
    }
}

