package com.example.groceryapp

data class member(var uid:String?="",var role:String?="",var joiningtime:String?="")
{
    fun toMap(): Map<String, Any?> {

        return mapOf<String, Any?>(
            "uid" to uid,
            "role" to role,
            "joiningtime" to joiningtime,

            )
    }
}
