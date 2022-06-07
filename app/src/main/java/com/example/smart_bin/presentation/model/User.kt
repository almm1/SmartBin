package com.example.smart_bin.presentation.model

var user = User()

data class User(
    val id: String = "",
    val phone: String = "",
    val full_name: String = "",
    var icon: String = "",
//    val bonuses: Map<String, Map<String, Any>>? = null
)