package com.onedive.passwordstore.domain.model

/**
 * This is a data transfer object class used to change/transfer model data in the datasource layer into this class
 *
 * @see com.onedive.passwordstore.data.mapper
 */
data class DatabaseModelDTO(
    val title:String,
    val username:String,
    val password:String,
    val desc:String,
    val roundedColor: Int,
    val tags: String,
    val date: String,
    val id: Long?,
)
