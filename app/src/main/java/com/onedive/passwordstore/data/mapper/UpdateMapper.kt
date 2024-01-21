package com.onedive.passwordstore.data.mapper

import androidx.annotation.Keep
import com.onedive.passwordstore.data.remote.update.model.UpdateModel
import com.onedive.passwordstore.domain.model.UpdateModelDTO

//This is mapper extension

@Keep
fun UpdateModel.toUpdateModelDto () : UpdateModelDTO {
    return UpdateModelDTO(
        updateVersion = updateVersion ?: "Err",
        updateTitle = updateTitle ?: " Err",
        updateMessage = updateMessage ?: " Err",
        updateApkUrl = updateApkUrl ?: " https://github.com"
    )
}