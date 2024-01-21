package com.onedive.passwordstore.presentation.receiver

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import com.onedive.passwordstore.utils.showToast
import java.io.File


class DownloadReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val downloadId = intent!!.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1)
        if (downloadId.toInt() != -1){
            showToast(context!!,"Download complete ",Toast.LENGTH_SHORT)
            showInstallDialog(context,downloadId)
        }
    }

    private fun showInstallDialog(context: Context, downloadId: Long) {
        val uri = getDownloadedFileUri(context, downloadId)
        if (uri != null){
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(uri, "application/vnd.android.package-archive")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK)

            context.startActivity(intent)
        }
    }

    private fun getDownloadedFileUri(context: Context, downloadId: Long): Uri? {

        val query = DownloadManager.Query()
        query.setFilterById(downloadId)

        val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val cursor: Cursor = manager.query(query)

        if (cursor.moveToFirst()) {
            val columnIndex: Int = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
            val status: Int = cursor.getInt(columnIndex)

            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                val uriIndex: Int = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)
                val uriString: String = cursor.getString(uriIndex)
                return FileProvider.getUriForFile(context, "com.onedive.passwordstore.fileprovider",File(Uri.parse(uriString).path!!))
            }

        }
        return null
    }


}