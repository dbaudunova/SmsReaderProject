package com.example.smsreaderproject.ui.utils

import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.requirePermission(
    permission: String,
    successDelegate: () -> Unit,
    failureDelegate: () -> Unit,
) {
    val permissionState = ContextCompat.checkSelfPermission(
        this.requireContext(),
        permission
    )
    when (permissionState) {
        PackageManager.PERMISSION_GRANTED -> successDelegate()
        else -> this.registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) successDelegate() else failureDelegate()
        }.launch(permission)
    }
}