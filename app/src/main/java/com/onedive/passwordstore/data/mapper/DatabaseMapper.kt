package com.onedive.passwordstore.data.mapper

import com.onedive.passwordstore.data.dataSource.local.room.entity.PasswordRoomDatabaseEntity
import com.onedive.passwordstore.domain.model.DatabaseModelDTO

fun PasswordRoomDatabaseEntity.toDatabaseModel() : DatabaseModelDTO {
    return DatabaseModelDTO(
        title = title,
        username = username,
        password = password,
        desc = desc,
        roundedColor = roundedColor,
        tags = tags,
        date = date,
        id = id
    )
}

fun DatabaseModelDTO.toPasswordRoomDatabaseEntity() : PasswordRoomDatabaseEntity {
    return PasswordRoomDatabaseEntity(
        title = title,
        username = username,
        password = password,
        desc = desc,
        roundedColor = roundedColor,
        tags = tags,
        date = date,
        id = id
    )
}

fun List<PasswordRoomDatabaseEntity>.toDatabaseModelList() : List<DatabaseModelDTO> {
    return map {
        DatabaseModelDTO(
            title = it.title,
            username = it.username,
            password = it.password,
            desc = it.desc,
            roundedColor = it.roundedColor,
            tags = it.tags,
            date = it.date,
            id = it.id
        )
    }
}
