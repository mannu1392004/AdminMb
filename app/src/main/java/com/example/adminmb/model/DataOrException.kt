package com.example.adminmb.model

import java.lang.Exception

data class DataOrException<T, E: Exception>(
    var data: T? = null,
    var loading:Boolean = false,
    var e: E? = null
)