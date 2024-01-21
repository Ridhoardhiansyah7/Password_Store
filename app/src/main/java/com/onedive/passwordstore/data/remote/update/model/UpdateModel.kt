package com.onedive.passwordstore.data.remote.update.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * this is the class that represents the data retrieved from the api
 * @see SerializedName
 */
@Keep
data class UpdateModel(
    @SerializedName("updateVersion") val updateVersion : String?,
    @SerializedName("updateTitle") val updateTitle : String?,
    @SerializedName("updateMessage") val updateMessage : String?,
    @SerializedName("updateApkUrl") val updateApkUrl : String?
)