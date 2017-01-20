// IEventBusService.aidl
package com.seasonfif.dynamicplugin;

// Declare any non-default types here with import statements

interface IEventBusService {
    String post(String className, String jsonStr);
}
