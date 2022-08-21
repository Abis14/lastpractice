package com.example.groceryapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class listdetails2(var Itemdetails: String? = "",
                        var category: String? = "",
                        var assinged: String? = "",
                      var done:String="false"
): Parcelable {
    fun toMap(): Map<String, Any?> {

        return mapOf<String, Any?>(
            "Itemdetails" to Itemdetails,
            "category" to category,
            "assinged" to assinged,
        "done" to done
        )
    }
}
