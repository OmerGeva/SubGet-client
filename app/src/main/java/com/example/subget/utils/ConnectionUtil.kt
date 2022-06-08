package com.example.subget.utils

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat
import com.example.subget.R

fun Context.internetEnabled(): Boolean {
    val connectionManager = ContextCompat.getSystemService(
        this,
        ConnectivityManager::class.java
    ) as ConnectivityManager
    val networkInfo = connectionManager.activeNetworkInfo
    return !(networkInfo == null || !networkInfo.isConnected)
}

fun Context.dialog(message: String) {
    val dialog = AlertDialog.Builder(this)
    dialog.setMessage(message)
    dialog.setPositiveButton(getString(R.string.ok)) { dialog, _ -> dialog.cancel() }
    dialog.create().show()
}