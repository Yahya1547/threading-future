package com.future.asynctaskfuture

import android.app.DownloadManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Environment
import android.widget.ProgressBar
import android.widget.Toast

class DownloadAsyncTask(
  private val mProgressBar: ProgressBar,
  private val downloadManager: DownloadManager,
  private val currentDownloadId: Long
) : AsyncTask<Void, Int, Int>() {
  override fun doInBackground(vararg params: Void?): Int {
    return downloadFile()
  }

  private fun downloadFile(): Int {

    val finishDownload = false
    while (!finishDownload) {
      val cursor =
        downloadManager.query(DownloadManager.Query().setFilterById(currentDownloadId ?: 0L))

      if (cursor.moveToFirst()) {
        val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
        when (status) {
          DownloadManager.STATUS_RUNNING -> {
            val totalBytes =
              cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
            if (totalBytes > 0) {
              val downloadedBytes =
                cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
              val progress = (downloadedBytes * 100L / totalBytes).toInt()

              publishProgress(progress)
            }
          }
          DownloadManager.STATUS_SUCCESSFUL -> {
            return 100
          }
        }
      }
      Thread.sleep(1000)
    }
    return 0
  }

  override fun onProgressUpdate(vararg values: Int?) {
    super.onProgressUpdate(*values)
    mProgressBar.progress = values[0] ?: 0
  }

  override fun onPostExecute(result: Int?) {
    super.onPostExecute(result)
    mProgressBar.progress = result ?: 0
    Toast.makeText(mProgressBar.context, "Download finished", Toast.LENGTH_SHORT).show()
  }
}