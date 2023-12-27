package com.onedive.passwordstore.domainLayer.repository

import kotlinx.coroutines.flow.Flow

/**
 * This interface is a method blueprint that will exist in any database in this application
 * @param T entity / data model that will be used by the database
 * @author Ridh
 */
interface DatabaseRepository<T> {

    /**
     * Return All data in database
     */
    fun getAll() : Flow<List<T>>

    /**
     * Return DistinctTags in database
     */

    fun getDistinctTags() : Flow<List<String>>

    /**
     * Return data by tagName in database
     */

    fun getAllByTagName(tagName:String) : Flow<List<T>>

    /**
     * return all password row data in database by id
     */

    fun getDetailedPasswordItemById(id:Long) : Flow<T>

    /**
     * This function is used for: if data already exists in the database then the action taken is update,
     * if it doesn't exist then insert
     */

    suspend fun upsert(entity: T)

    /**
     * This function is used to: Delete data by id
     */
    suspend fun deleteById(id: Long)

}