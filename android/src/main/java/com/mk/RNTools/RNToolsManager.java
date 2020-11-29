package com.mk.RNTools;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import android.app.DownloadManager.Request;

public class RNToolsManager extends ReactContextBaseJavaModule {

  public static String description;
  public static String filePathName;

  DownloadManager downManager;
  Activity myActivity;

  public RNToolsManager(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "RNToolsManager";
  }


  @ReactMethod
  public void downloadApkAndroidOnly(String downLoadUrl, String fileName) {
//    Activity activity = getCurrentActivity();
//    getAppilicationContext
//    if (activity instanceof MainActivity) {
//      DownloadManager.Request request;
//      try {
//        request = new DownloadManager.Request(Uri.parse(downLoadUrl));
//      } catch (Exception e) {
//        e.printStackTrace();
//        return;
//      }
//      request.allowScanningByMediaScanner();
//      request.setVisibleInDownloadsUi(true);
//      request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//      request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName+".apk");
//      DownloadManager manager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
//      ((MainActivity) activity).downloadId = manager.enqueue(request);

    RNToolsManager.description = fileName;
    RNToolsManager.filePathName = fileName;
    myActivity = getCurrentActivity();
    downManager = (DownloadManager) myActivity.getSystemService(Context.DOWNLOAD_SERVICE);
    Uri uri = Uri.parse(downLoadUrl);
    DownloadManager.Request request = new Request(uri);

    // 设置允许使用的网络类型，这里是移动网络和wifi都可以
    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);

    //设置通知栏标题
    request.setNotificationVisibility(Request.VISIBILITY_VISIBLE);
    request.setMimeType("application/vnd.android.package-archive");
    request.setTitle("安装包");

    if (description == null || "".equals(description)) {
      description = "目标apk正在下载";
    }

    request.setDescription(description);
    request.setAllowedOverRoaming(false);

    // 设置文件存放目录
    request.setDestinationInExternalFilesDir(myActivity, Environment.DIRECTORY_DOWNLOADS, fileName);

    long downloadid = downManager.enqueue(request);
    SharedPreferences sPreferences = myActivity.getSharedPreferences("download_apk_file", 0);
    sPreferences.edit().putLong("download_apk", downloadid).commit();
  }
}

