package com.future.asynctaskfuture

import android.app.DownloadManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
  private lateinit var mTextView: TextView
  private lateinit var mProgressBar: ProgressBar
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    mTextView = findViewById(R.id.textView1)
    mProgressBar = findViewById(R.id.progressBar)

    findViewById<Button>(R.id.btn_download).setOnClickListener {
      mProgressBar.progress = 0
//      DownloadAsyncTask(mProgressBar, downloadManager)
      downloadFile()
    }
  }

  fun startTask(view: View) {
    mTextView.text = "Napping..."
    mProgressBar.progress = 0

    SimpleAsyncTask(mTextView, mProgressBar).execute()
  }

  private fun downloadFile() {

    val url = "http://speedtest.ftp.otenet.gr/files/test10Mb.db"
    val filename = url.substring(url.lastIndexOf("/") + 1)

    val downloadRequest = DownloadManager.Request(Uri.parse(url))
      .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
      .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename)
      .setTitle(filename)
      .setDescription("Downloading file...")
      .setAllowedOverMetered(true)
      .setAllowedOverRoaming(true).apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
          setRequiresCharging(false)
        }
      }

    val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
    val currentDownloadId = downloadManager.enqueue(downloadRequest)
    DownloadAsyncTask(mProgressBar, downloadManager, currentDownloadId).execute()
  }

}