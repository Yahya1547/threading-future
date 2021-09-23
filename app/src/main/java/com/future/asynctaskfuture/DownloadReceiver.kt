package com.future.asynctaskfuture

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class DownloadReceiver: BroadcastReceiver() {
  override fun onReceive(context: Context?, intent: Intent?) {
    Log.d("Download Receiver", "Download complete")
  }
}