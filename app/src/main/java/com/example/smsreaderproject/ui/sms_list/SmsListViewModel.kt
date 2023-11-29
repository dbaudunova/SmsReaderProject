package com.example.smsreaderproject.ui.sms_list

import android.content.ContentResolver
import android.provider.Telephony
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smsreaderproject.data.model.SmsChatEntry

class SmsListViewModel : ViewModel() {

    private val _chatMessageEntries = MutableLiveData<List<SmsChatEntry>>()
    val chatMessageEntries: LiveData<List<SmsChatEntry>>
        get() = _chatMessageEntries


    fun loadSmsMessages(contentResolver: ContentResolver) {
        val cursor = contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        val result = AddressAndMessageList(mutableListOf())

        cursor?.apply {
            if (moveToFirst()) {
                do {
                    val address = getString(getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
                    val body = getString(getColumnIndexOrThrow(Telephony.Sms.BODY))
                    result.list.add(address to body)
                } while (moveToNext())
            }
            close()
        }
        _chatMessageEntries.postValue(result.toSmsChatEntriesList())
    }
}

@JvmInline
value class AddressAndMessageList(
    val list: MutableList<Pair<String, String>>
) {
    fun toSmsChatEntriesList(): List<SmsChatEntry> =
        list.groupBy { it.first }
            .map {
                SmsChatEntry(it.key, it.value.map { pair ->
                    pair.second
                })
            }
}