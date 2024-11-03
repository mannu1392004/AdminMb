package com.example.adminmb.model

data class UserToSend(
    val id: String,
    val name: String,
    val email: String,
    val subscribed: Boolean,
    val subscribedUpTo: Int,
    val mobileNumber: String,
    val dateOfSubscription:String
)

