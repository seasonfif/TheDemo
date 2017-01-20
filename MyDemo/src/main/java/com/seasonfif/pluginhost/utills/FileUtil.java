package com.seasonfif.pluginhost.utills;

import android.content.Context;
import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

/**
 * 创建时间：2017年01月19日14:03 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class FileUtil {

  /**
   * 提取文件到目标位置，并处理文件夹是否存在，是否校验，是否强制覆盖。不会释放SO库
   * @param context
   * @param assertName asset名称（asset的相对路径，可包含子路径）
   * @param dir 目标文件夹（asset的输出目录）
   * @param verify 是否校验MD5
   * @param endec 是否采用异或解密
   * @param dexOutputDir 成功提取该文件时，是否删除同名的DEX文件
   * @return
   */
  public static final boolean quickExtractTo(Context context, final String assertName, final String dir, final String dstName, boolean verify, byte endec[], String dexOutputDir) {
    File file = new File(dir + "/" + dstName);

    // 建立子目录
    File d = file.getParentFile();
    if (!d.exists()) {
      if (!d.mkdirs()) {
        return false;
      }
    }

    // 检查创建是否成功
    if (!d.exists() || !d.isDirectory()) {
      return false;
    }

    // 文件已存在
    if (file.exists()) {
      /*// 计算文件求和，避免重新创建文件
      if (verify) {
        if (checkMD5(context, name, dir, dstName)) {
          return true;
        }
      }

      // 先删除对应的DEX文件
      if ((dstName.endsWith(".jar") || dstName.endsWith(".apk")) && !TextUtils.isEmpty(dexOutputDir)) {
        String baseName = dstName.substring(0, dstName.length() - 4);
        File dexf = new File(dexOutputDir + "/" + baseName + ".dex");
        if (dexf.exists()) {
          dexf.delete();
          // 检查删除是否成功
          if (dexf.exists()) {
            return false;
          }
        }
      }*/


      // 删除存在的，便于重新写入
      if (!file.delete()) {
        return false;
      }

      // 检查删除是否成功
      if (file.exists()) {
        return false;
      }
    }

    boolean rc = extractTo(context, assertName, dir, dstName, endec);
    if (!rc) {
      return false;
    }
    return true;
  }

  /**
   * @param context
   * @param name
   * @param dir
   * @return
   */
  public static final boolean checkMD5(Context context, String name, String dir, String dstName) {
    String md5 = readUTF8(context, name + ".md5");

    if (TextUtils.isEmpty(md5)) {
      return false;
    }
    md5 = md5.trim();
    md5 = md5.toUpperCase(Locale.ENGLISH);
    if (TextUtils.isEmpty(md5)) {
      return false;
    }

    FileInputStream is = null;
    try {
      is = new FileInputStream(dir + "/" + dstName);
      byte rc[] = IoStreamUtils.readMD5(is);
      if (rc == null || rc.length <= 0) {
        return false;
      }
      String md5New = StringUtils.toHexString(rc);

      if (TextUtils.isEmpty(md5New)) {
        return false;
      }
      return md5.equals(md5New);
    } catch (Throwable e) {

    } finally {
      IoStreamUtils.closeSilently(is);
    }

    return false;
  }

  /**
   * @param context
   * @param name
   * @return
   */
  public static final String readUTF8(Context context, String name) {
    InputStream is = null;
    try {
      is = context.getAssets().open(name);
      return IoStreamUtils.readUTF8(is);
    } catch (Throwable e) {

    } finally {
      IoStreamUtils.closeSilently(is);
    }
    return null;
  }

  /**
   * 提取文件到目标位置
   * @param context
   * @param name asset名称（asset的相对路径，可包含子路径）
   * @param dir 目标文件夹（asset的输出目录）
   * @param endec 解密
   * @return
   */
  public static final boolean extractTo(Context context, final String name, final String dir, final String dstName, byte endec[]) {
    File file = new File(dir + "/" + dstName);
    InputStream is = null;
    OutputStream os = null;
    try {
      is = context.getAssets().open(name);
      os = new FileOutputStream(file);
      byte bs[] = new byte[4096];
      int rc = 0;
      if (endec != null && endec.length > 0) {
        int idx = 0;
        while ((rc = is.read(bs)) >= 0) {
          if (rc > 0) {
            for (int i = 0; i < rc; i++) {
              bs[i] ^= endec[idx++];
              if (idx >= endec.length) {
                idx = 0;
              }
            }
            os.write(bs, 0, rc);
          }
        }
      } else {
        while ((rc = is.read(bs)) >= 0) {
          if (rc > 0) {
            os.write(bs, 0, rc);
          }
        }
      }
      return true;
    } catch (Throwable e) {

    } finally {
      IoStreamUtils.closeSilently(is);
      IoStreamUtils.closeSilently(os);
    }

    return false;
  }
}
