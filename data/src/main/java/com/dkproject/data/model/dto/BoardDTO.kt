package com.dkproject.data.model.dto

import com.dkproject.data.model.ContentParam
import com.dkproject.domain.model.Board
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class BoardDTO(
    val id:Long,
    val title:String,
    val content:String,
    val createdAt:String,
    val updatedAt:String,
    val createUserId:Long,
    val createUserName:String,
    val createUserProfileFilePath:String,
    val updateUserId:Long,
    val updateUserName:String,
    val updateUserProfileFilePath:String,
){
    fun toDomainModel():Board{
        val contentParam = Json .decodeFromString<ContentParam>(content)
        return Board(
            id=id,
            title=title,
            content = contentParam.text,
            images = contentParam.images,
            createdAt=createdAt,
            createUserId = createUserId,
            createUserName = createUserName,
            createUserProfileFilePath = createUserProfileFilePath,
            updateUserId=updateUserId,
            updateUserName=updateUserName,
            updateUserProfileFilePath=updateUserProfileFilePath
        )
    }
}
