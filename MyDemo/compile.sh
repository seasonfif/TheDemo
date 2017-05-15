#!/usr/bin/env bash
echo "I'm shell script"
#cd ../
echo `pwd`
#gradle -v
#adb mount -o remount /
#adb shell mkdir sdcard/plu | adb push -p /c/Users/zhangqiang/Desktop/newlink-10-10-101.jar sdcard/plu/newlink.jar | adb shell am force-stop com.homelink.newlink | adb shell am start -n com.homelink.newlink/com.homelink.newlink.ui.app.SplashScreenActivity
#adb shell mkdir sdcard/plu | adb push -p /c/Users/zhangqiang/Desktop/newlink-10-10-101.jar sdcard/plu/newlink.jar
#adb shell mkdir sdcard/plu
adb shell rm -rf sdcard/plu
echo $?
#gradle -p /d/learn/TheDemo/MyDemo assembleBaiduDebug