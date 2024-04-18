package com.dkproject.data.model.dto

import com.dkproject.domain.model.User
import kotlinx.serialization.Serializable


@Serializable
data class UserDTO(
    val id:Long,
    val loginId:String,
    val userName:String,
    val extraUserInfo:String,
    val profileFilePath:String
){
    fun toDomainModel():User{
        return User(
            id=id,
            loginId=loginId,
            userName=userName,
            profileFilePath=profileFilePath,
            extraUserInfo=extraUserInfo
        )
    }
}

