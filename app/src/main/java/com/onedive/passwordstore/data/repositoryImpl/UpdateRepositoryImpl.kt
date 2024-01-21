package com.onedive.passwordstore.data.repositoryImpl

import androidx.annotation.Keep
import com.onedive.passwordstore.data.mapper.toUpdateModelDto
import com.onedive.passwordstore.data.remote.update.repository.UpdateServiceRepository
import com.onedive.passwordstore.domain.model.UpdateModelDTO
import com.onedive.passwordstore.domain.repository.UpdateRepository
import com.onedive.passwordstore.utils.Const
import com.onedive.passwordstore.utils.RetrofitInstance

/**
 * This class is an implementation class of UpdateRepository and uses the retrofit lib to call data via api
 *
 * @see UpdateRepository
 * @see UpdateModelDTO
 */

@Keep
class UpdateRepositoryImpl : UpdateRepository<UpdateModelDTO> {

    private val instance = RetrofitInstance.getInstanceService(UpdateServiceRepository::class.java)
    private lateinit var updateModelDto: UpdateModelDTO
    override suspend fun checkAvailableUpdate(): UpdateModelDTO {
        try {
            val result = instance.checkAvailableUpdate(Const.getUpdateJsonBinId(),false)
            if (result.isSuccessful) updateModelDto = result.body()!!.toUpdateModelDto()
        } catch (e: Throwable){
            updateModelDto = UpdateModelDTO(
                updateVersion = "Unknown",
                updateTitle = "No Available Update",
                updateMessage = "can't check latest update version because ${e.localizedMessage}",
                updateApkUrl = "https://github.com"
            )
        }
        return updateModelDto
    }

}
