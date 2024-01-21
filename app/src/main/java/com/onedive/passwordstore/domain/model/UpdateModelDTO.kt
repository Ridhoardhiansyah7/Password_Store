package com.onedive.passwordstore.domain.model

/**
 * This is a data transfer object class used to change/transfer model data in the datasource layer into this class
 *
 * @see com.onedive.passwordstore.data.mapper
 */
data class UpdateModelDTO(
    val updateVersion : String ,
    val updateTitle : String,
    val updateMessage : String,
    val updateApkUrl : String
)