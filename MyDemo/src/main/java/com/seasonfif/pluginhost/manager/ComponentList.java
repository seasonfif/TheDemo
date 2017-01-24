package com.seasonfif.pluginhost.manager;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import java.util.HashMap;

/**
 * 用来快速获取四大组件的系统Info的List
 *
 * @see ActivityInfo
 * @see ServiceInfo
 * @see ProviderInfo
 *
 * @author Jiongxuan Zhang
 */
public class ComponentList {
    /**
     * Class类名 - Activity的Map表
     */
    final HashMap<String, ActivityInfo> mActivities = new HashMap<>();

    /**
     * Class类名 - Provider的Map表
     */
    final HashMap<String, ProviderInfo> mProvidersByName = new HashMap<>();

    /**
     * Authority - Provider的Map表
     */
    final HashMap<String, ProviderInfo> mProvidersByAuthority = new HashMap<>();

    /**
     * Class类名 - Service的Map表
     */
    final HashMap<String, ServiceInfo> mServices = new HashMap<>();

    /**
     * Class类名 - BroadcastReceiver的Map表
     * 注意：是的，你没有看错，系统缓存Receiver就是用的ActivityInfo
     */
    final HashMap<String, ActivityInfo> mReceivers = new HashMap<>();

    ComponentList(PackageInfo pi) {
        if (pi.activities != null) {
            for (ActivityInfo ai : pi.activities) {
                mActivities.put(ai.name, ai);
            }
        }
        if (pi.providers != null) {
            for (ProviderInfo ppi : pi.providers) {
                mProvidersByName.put(ppi.name, ppi);
                mProvidersByAuthority.put(ppi.authority, ppi);
            }
        }
        if (pi.services != null) {
            for (ServiceInfo si : pi.services) {
                mServices.put(si.name, si);
            }
        }
        if (pi.receivers != null) {
            for (ActivityInfo ri : pi.receivers) {
                mReceivers.put(ri.name, ri);
            }
        }
    }

    /**
     * 获取ServiceInfo对象
     */
    public ServiceInfo getService(String className) {
        return mServices.get(className);
    }

    /**
     * 获取ServiceInfo对象
     */
    public ActivityInfo getActivity(String className) {
        return mActivities.get(className);
    }

    /**
     * 通过类名获取ProviderInfo对象
     */
    public ProviderInfo getProvider(String className) {
        return mProvidersByName.get(className);
    }

    /**
     * 通过Authority获取ProviderInfo对象
     */
    public ProviderInfo getProviderByAuthority(String authority) {
        return mProvidersByAuthority.get(authority);
    }

    /**
     * 获取Receiver对象
     */
    public ActivityInfo getReceiver(String className) {
        return mReceivers.get(className);
    }
}
