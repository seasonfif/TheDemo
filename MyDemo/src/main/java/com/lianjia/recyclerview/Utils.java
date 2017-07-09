package com.lianjia.recyclerview;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by seasonfif on 2017/7/9.
 */
public class Utils {

    /**
     * 网络是否已经连接
     */
    public static boolean isConnected(Context context) {
        if (context != null) {
            ConnectivityManager cm = null;
            try {
                cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            } catch (Exception e) {
                // ignore
            }
            if (cm != null) {
                NetworkInfo[] infos = cm.getAllNetworkInfo();
                if (infos != null) {
                    for (NetworkInfo ni : infos) {
                        if (ni.isConnected()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
