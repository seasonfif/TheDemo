// IServiceHandle.aidl
package com.seasonfif.svcmanager;

// Declare any non-default types here with import statements

interface IServiceHandle {

    IBinder getService(String serviceName);

    void addService(String serviceName, IBinder service);

    void removeService(String serviceName);
}
