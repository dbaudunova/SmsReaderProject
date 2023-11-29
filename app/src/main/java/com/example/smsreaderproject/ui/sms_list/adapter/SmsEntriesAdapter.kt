package com.example.smsreaderproject.ui.sms_list.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.smsreaderproject.R
import com.example.smsreaderproject.data.model.SmsChatEntry
import com.example.smsreaderproject.databinding.SmsChatEntryItemBinding

class SmsEntriesAdapter(private val onItemClick: (SmsChatEntry) -> Unit) :
    RecyclerView.Adapter<SmsEntriesAdapter.SmsEntryViewHolder>() {

    private val list = mutableListOf<SmsChatEntry>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<SmsChatEntry>) = with(this.list) {
        clear()
        addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmsEntryViewHolder {
        return SmsEntryViewHolder(
            SmsChatEntryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: SmsEntryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class SmsEntryViewHolder(private val binding: SmsChatEntryItemBinding/*, private val onItemClick: (SmsChatEntry) -> Unit,*/) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(entry: SmsChatEntry) {
            binding.apply {
                val context = binding.root.context

                smsAvatar.background = getRandomBackground(context)
                smsAvatar.text = entry.address?.first().toString()
                smsSender.text = entry.address
                messageAgenda.text = entry.messages?.first()

                itemView.setOnClickListener {
                    onItemClick(entry)
                }
            }
        }

        private fun getRandomBackground(context: Context): Drawable? {
            val background = ContextCompat.getDrawable(context, R.drawable.sms_chat_entry_avatar)
            val color = context.resources.getIntArray(R.array.rainbow).random()
            background?.let { DrawableCompat.setTint(it, color) }
            return background
        }
    }
}