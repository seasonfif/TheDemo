package com.seasonfif.svcmanager;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.support.annotation.Nullable;
import android.util.Log;
import com.example.svcmanager.BuildConfig;

/**
 * 创建时间：2017年04月07日16:23 <br>
 * 作者：zhangqiang <br>
 * 描述：Provider used for getting the ServiceChannel from other process only
 */

public class ServiceProvider extends ContentProvider {

  private static final String TAG = "ServiceProvider";
  public static final String PATH_SERVER_CHANNEL = "severchannel";

  @Override public boolean onCreate() {
    if (BuildConfig.DEBUG) {
      Log.d(TAG, "[onCreate]" + " App = " + getContext().getApplicationContext());
    }
    return false;
  }

  @Nullable @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
      String sortOrder) {
    return ServiceCursor.makeCursor(new Binder());
  }

  @Nullable @Override public String getType(Uri uri) {
    return null;
  }

  @Nullable @Override public Uri insert(Uri uri, ContentValues values) {
    return null;
  }

  @Override public int delete(Uri uri, String selection, String[] selectionArgs) {
    return 0;
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    return 0;
  }
}
