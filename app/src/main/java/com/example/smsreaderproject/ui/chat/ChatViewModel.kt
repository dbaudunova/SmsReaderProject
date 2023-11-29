package com.example.smsreaderproject.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smsreaderproject.data.model.MessageInfo

class ChatViewModel: ViewModel() {
    private val _chatMessage = MutableLiveData<List<MessageInfo>>()
    val chatMessage : LiveData<List<MessageInfo>>
        get() = _chatMessage

    fun getChatMessages(address: String, messages: List<String>) {
        _chatMessage.postValue(messages.map { MessageInfo(address, it) })
    }
}