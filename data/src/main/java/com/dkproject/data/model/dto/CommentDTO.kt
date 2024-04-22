package com.dkproject.data.model.dto

import com.dkproject.domain.model.Comment
import kotlinx.serialization.Serializable

@Serializable
data class CommentDTO(
    val id:Long,
    val comment:String,
    val createdAt:String,
    val createUserId:Long,
    val createUserName:String,
    val profileImageUrl:String,
){
    fun todomainModel(): Comment{
        return Comment(
            id=id,
            comment=comment,
            createdAt=createdAt,
            createUserId=createUserId,
            userName = createUserName,
            profileImageUrl=profileImageUrl
        )
    }
}
