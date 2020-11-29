package com.mk.RNTools;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Toast;

import java.io.File;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.facebook.react.BuildConfig;

public class DownLoadBroadcastReceiver extends BroadcastReceiver {

  public void onReceive(Context context, Intent intent) {
    String action = intent.getAction();
    if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
      long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
      SharedPreferences sPreferences = context.getSharedPreferences("download_apk_file", 0);
      long refernece = sPreferences.getLong("download_apk", 0);

      if (id == refernece) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(id);
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Cursor cursor = manager.query(query);
        if (cursor.moveToFirst()) {
          Uri uri = null;
          if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            int fileUriIdx = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
            String file = cursor.getString(fileUriIdx);
            if (file != null) {
              uri = Uri.parse(file);
            }
          } else {
            // 过时的方式：DownloadManager.COLUMN_LOCAL_FILENAME
            int fileNameIdx = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
            String file = cursor.getString(fileNameIdx);
            if (file != null) {
              uri = Uri.parse("file://" + file);
            }
          }

          if (uri != null) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setDataAndType(uri, "application/vnd.android.package-archive");
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
          }
        }
      }
    }
  }
  
}

