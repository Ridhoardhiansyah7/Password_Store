package com.onedive.passwordstore.data.mapper

import com.onedive.passwordstore.data.dataSource.local.room.entity.PasswordRoomDatabaseEntity
import com.onedive.passwordstore.domain.model.DatabaseModel

fun PasswordRoomDatabaseEntity.toDatabaseModel() : DatabaseModel {
    return DatabaseModel(
        title = title ?: "Unknown Title",
        username = username ?: "Unknown UserName",
        password = password ?: "Unknown Password",
        desc = desc ?: "No Description Found",
        roundedColor = roundedColor ?: 0XFFFFFF,
        tags = tags ?: "#Unknown",
        date = date ?: "Not Found"
    )
}

fun DatabaseModel.toPasswordRoomDatabaseEntity() : PasswordRoomDatabaseEntity {
    return PasswordRoomDatabaseEntity(
        title = title,
        username = username,
        password = password,
        desc = desc,
        roundedColor = roundedColor,
        tags = tags,
        date = date
    )
}
