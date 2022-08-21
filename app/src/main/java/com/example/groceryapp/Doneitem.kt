package com.example.groceryapp

data class Doneitem(var Itemdetails: String? = "",
                    var category: String? = "",
                    var assinged: String? = ""
)
{

        fun toMap(): Map<String, Any?> {

            return mapOf<String, Any?>(
                "Itemdetails" to Itemdetails,
                "category" to category,
                "assinged" to assinged
            )
        }
}
