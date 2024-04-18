package com.dkproject.domain.model


data class Board(
    val id:Long,
    val title:String,
    val content:String,
    val images:List<String>,
    val createdAt:String,
    val createUserId:Long,
    val createUserName:String,
    val createUserProfileFilePath:String,
    val updateUserId:Long,
    val updateUserName:String,
    val updateUserProfileFilePath:String,
)
