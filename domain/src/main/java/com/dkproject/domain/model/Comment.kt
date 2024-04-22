package com.dkproject.domain.model

data class Comment(
    val id:Long,
    val comment:String,
    val createdAt:String,
    val createUserId:Long,
    val userName:String,
    val profileImageUrl:String,
)


