package com.example.smsreaderproject.ui.chat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.smsreaderproject.R
import com.example.smsreaderproject.databinding.FragmentChatBinding
import com.example.smsreaderproject.ui.chat.adapter.ChatAdapter

class ChatFragment : Fragment(R.layout.fragment_chat) {

    private val binding: FragmentChatBinding by viewBinding()
    private val viewModel by lazy {
        ViewModelProvider(this)[ChatViewModel::class.java]
    }
    private lateinit var adapter: ChatAdapter
    private lateinit var navArgs: ChatFragmentArgs

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initializeRecycler()
        initNavArgs()
    }

    private fun initNavArgs() {
        arguments?.let {
            navArgs = ChatFragmentArgs.fromBundle(it)
        }
        viewModel.getChatMessages(navArgs.address, navArgs.message.toList())
    }

    private fun initializeRecycler() {
        adapter = ChatAdapter()
        binding.smsListRecycler.adapter = adapter
    }

    private fun initObserver() {
        viewModel.chatMessage.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}