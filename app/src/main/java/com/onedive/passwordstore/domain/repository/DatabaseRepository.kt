package com.onedive.passwordstore.domain.repository

import kotlinx.coroutines.flow.Flow


/**
 * This interface is a method blueprint that will exist in any database in this application
 * @param E entity / data model that will be used by the database
 */
interface DatabaseRepository<E> {

    /**
     * Return All data in database
     */
    fun getAll() : Flow<List<E>>

    /**
     * Return DistinctTags in database
     */

    fun getDistinctTags() : Flow<List<String>>

    /**
     * Return data by tagName in database
     */

    fun getAllByTagName(tagName:String) : Flow<List<E>>

    /**
     * return all password row data in database by id
     */

    fun getDetailedPasswordItemById(id:Long) : Flow<E>

    /**
     * This function is used for: if data already exists in the database then the action taken is update,
     * if it doesn't exist then insert
     */

    suspend fun upsert(entity: E)

    /**
     * This function is used to: Delete data by id
     */
    suspend fun deleteById(id: Long)

}