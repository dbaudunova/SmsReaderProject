package com.example.smsreaderproject.data.model

data class SmsChatEntry(
    val address: String? = null,
    val messages: List<String>? = null
)

data class MessageInfo(
    val address: String? = null,
    val message: String? = null
)
