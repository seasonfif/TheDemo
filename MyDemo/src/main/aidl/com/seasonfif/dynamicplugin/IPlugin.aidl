// Plugin.aidl
package com.seasonfif.dynamicplugin;

// Declare any non-default types here with import statements

interface IPlugin {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

    IBinder query(String name);
}
