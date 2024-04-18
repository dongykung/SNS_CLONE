package com.dkproject.domain.model

data class User(
    val id:Long,
    val loginId:String,
    val userName:String,
    val profileFilePath:String?=null,
    val extraUserInfo:String
)
