package com.example.groceryapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.HashMap


data class listdetails(
    var Itemdetails: String? = "",
    var category: String? = "",
    var assinged: String? = "",
    var done:String?="false",
    var hash: HashMap<String, member>? = HashMap()
) {
    fun toMap(): Map<String, Any?> {

        return mapOf<String, Any?>(
            "Itemdetails" to Itemdetails,
            "category" to category,
            "assinged" to assinged,
        "done" to done
        )
    }
}
