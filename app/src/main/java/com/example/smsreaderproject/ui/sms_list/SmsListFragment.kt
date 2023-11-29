package com.example.smsreaderproject.ui.sms_list

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.smsreaderproject.R
import com.example.smsreaderproject.ui.sms_list.adapter.SmsEntriesAdapter
import com.example.smsreaderproject.data.model.SmsChatEntry
import com.example.smsreaderproject.databinding.FragmentSmsListBinding
import com.example.smsreaderproject.ui.utils.requirePermission

class SmsListFragment : Fragment(R.layout.fragment_sms_list) {

    private val binding: FragmentSmsListBinding by viewBinding()
    private val viewModel: SmsListViewModel by lazy {
        ViewModelProvider(this)[SmsListViewModel::class.java]
    }
    private lateinit var adapter: SmsEntriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initializeUI()
        requireSmsPermission()
    }

    private fun initObserver() {
        viewModel.chatMessageEntries.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun initializeUI() {
        initializeRecycler()
        showFailureMessage(false)
    }

    private fun requireSmsPermission() {
        requirePermission(
            permission = Manifest.permission.READ_SMS,
            successDelegate = {
                viewModel.loadSmsMessages(requireContext().contentResolver)
            },
            failureDelegate = {
                showFailureMessage(true)
            }
        )
    }

    private fun showFailureMessage(shown: Boolean) {
        val visibility = if (shown) View.VISIBLE else View.GONE
        listOf(
            binding.failSmsImageView,
            binding.failSmsTitle,
            binding.failSmsDescription
        ).forEach { it.visibility = visibility }
        binding.smsListRecycler.visibility = if (shown) View.GONE else View.VISIBLE
    }

    private fun onChatItemClick(entry: SmsChatEntry) {
        findNavController().navigate(
            SmsListFragmentDirections.actionSmsListFragmentToChatFragment(
                entry.address.toString(),
                entry.messages!!.toTypedArray()
            )
        )
    }

    private fun initializeRecycler() {
        adapter = SmsEntriesAdapter(this::onChatItemClick)
        binding.smsListRecycler.adapter = adapter
    }
}