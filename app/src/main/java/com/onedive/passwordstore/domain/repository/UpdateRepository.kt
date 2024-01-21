package com.onedive.passwordstore.domain.repository

/**
 *This interface is used as a contact for the class that will create the application update checking feature,
 * this repository does not depend on any technology / lib
 *
 * @param E Generic Data Model
 */
interface UpdateRepository<E> {

    /**
     *
     * @return E generic data model
     */
    suspend fun checkAvailableUpdate() : E

}