package com.example.smsreaderproject.ui.chat.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.smsreaderproject.R
import com.example.smsreaderproject.data.model.MessageInfo
import com.example.smsreaderproject.databinding.ItemChatBinding

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    private val list = mutableListOf<MessageInfo>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<MessageInfo>) = with(this.list) {
        clear()
        addAll(list)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(
            ItemChatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ChatViewHolder(private val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(messageInfo: MessageInfo) {
            val context = binding.root.context

            binding.apply {
                smsAvatar.background = getRandomBackground(context)
                smsAvatar.text = messageInfo.address?.first().toString()
                smsSender.text = messageInfo.address
                messageAgenda.text = messageInfo.message
            }
        }

        private fun getRandomBackground(context: Context): Drawable? {
            val background =
                ContextCompat.getDrawable(context, R.drawable.sms_chat_entry_avatar)
            val color = context.resources.getIntArray(R.array.rainbow).random()
            background?.let { DrawableCompat.setTint(it, color) }
            return background
        }
    }
}