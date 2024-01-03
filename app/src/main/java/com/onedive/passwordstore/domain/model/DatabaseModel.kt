package com.onedive.passwordstore.domain.model

data class DatabaseModel(
    val title:String,
    val username:String,
    val password:String,
    val desc:String,
    val roundedColor: Int,
    val tags: String,
    val date: String,
    val id: Long? = 0
)
