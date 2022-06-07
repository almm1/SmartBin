package com.example.smart_bin.presentation.model


//data class BonusMap(val bonuses: Map<String, Bonus>)

data class Bonus(
    val promocode: String = "",
    val body: String = "",
    val title: String = "",
    var is_used: Boolean? = null
)
