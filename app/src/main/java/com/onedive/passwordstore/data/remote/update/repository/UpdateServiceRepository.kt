package com.onedive.passwordstore.data.remote.update.repository

import androidx.annotation.Keep
import com.onedive.passwordstore.data.remote.update.model.UpdateModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *
 * @see GET
 * @see Path
 * @see Query
 */

@Keep
interface UpdateServiceRepository  {

    @GET("b/{binId}")
    suspend fun checkAvailableUpdate(
        @Path("binId") binId : String,
        @Query("meta") includeMetaData : Boolean
    ) : Response<UpdateModel>

}