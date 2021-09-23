package com.future.asynctaskfuture

import android.os.AsyncTask
import android.widget.ProgressBar
import android.widget.TextView
import java.util.*

class SimpleAsyncTask(private val mTextView: TextView, private val mProgressBar: ProgressBar) : AsyncTask<Void, Int, String>() {
  override fun doInBackground(vararg params: Void?): String {
    val r = Random()
    val n: Int = r.nextInt(11)

    val s = n * 200

    val step = s / 100
    try {
      for(i in 1..100){
        Thread.sleep(step.toLong())
        publishProgress(i)
      }
    } catch (e: InterruptedException) {
      e.printStackTrace()
    }

    return "Awake at last after sleeping for $s milliseconds!"
  }

  override fun onProgressUpdate(vararg values: Int?) {
    super.onProgressUpdate(*values)
    mProgressBar.progress = values[0]!!
  }

  override fun onPostExecute(result: String?) {
    mTextView.text = result
  }

}