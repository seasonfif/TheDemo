package com.demo.dynamicload.utills;

import android.database.Cursor;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author devast
 *
 */
public final class IoStreamUtils {

    /**
     * 完全读取，直到缓冲区满，或者文件末尾
     * @param in
     * @param dst
     * @param offset
     * @param count
     * @return 返回当前dst位置
     * @throws IOException
     */
    public static final int readFully(InputStream in, byte[] dst, int offset, int count) throws IOException {
        while (count > 0) {
            int bytesRead = in.read(dst, offset, count);
            if (bytesRead < 0) {
                return offset;
            }
            offset += bytesRead;
            count -= bytesRead;
        }
        return offset;
    }

    /**
     * @param is
     * @param os
     * @throws IOException
     */
    public static final void copyStream(InputStream is, OutputStream os) throws IOException {
        byte buffer[] = new byte[4096];
        int rc = 0;
        while ((rc = is.read(buffer)) >= 0) {
            if (rc > 0) {
                os.write(buffer, 0, rc);
            }
        }
    }

    /**
     * @param is
     * @param os
     * @param length
     * @throws IOException
     */
    public static final void copyStream(InputStream is, OutputStream os, int length) throws IOException {
        byte buffer[] = new byte[4096];
        int rc = 0;
        int left = length;
        while ((rc = is.read(buffer)) >= 0) {
            if (rc > 0) {
                if (rc > left) {
                    os.write(buffer, 0, left);
                    break;
                } else {
                    os.write(buffer, 0, rc);
                }
                left -= rc;
            }
        }
    }

    public static final void closeSilently(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (Throwable e) {

            }
        }
    }

    /**
     * 发现一个问题，android 4.1.1以下Cursor都没有实现Closeable接口，因此调用Closeable参数会出现转换异常
     * java.lang.IncompatibleClassChangeError: interface not implemented,
     * 因此从新加上以前版本的closeSilently(Cursor c)
     */
    public static final void closeSilently(Cursor c) {
        if (c != null) {
            try {
                c.close();
            } catch (Throwable e) {

            }
        }
    }

    /**
     * @param in
     * @return
     * @throws IOException
     * 读取有中文且大于4K的文件时候,有BUG,建议使用readUTF8New
     */
    public static final String readUTF8(InputStream in) throws IOException {
        if (in == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        byte buffer[] = new byte[4096];
        int rc = 0;
        while ((rc = in.read(buffer)) >= 0) {
            if (rc > 0) {
                sb.append(new String(buffer, 0, rc, "UTF-8"));
            }
        }
        return sb.toString();
    }

    public static String readUTF8New(InputStream in) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\r\n");
            }
        } catch (Throwable e) {
            //
        } finally {
            closeSilently(br);
        }

        return sb.toString();
    }

    /**
     * @param in
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static final byte[] readMD5(InputStream in) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte buffer[] = new byte[4096];
        int rc = 0;
        while ((rc = in.read(buffer)) >= 0) {
            if (rc > 0) {
                digest.update(buffer, 0, rc);
            }
        }
        return digest.digest();
    }

    /**
     * @param file
     * @return
     */
    public static final byte[] readMD5(File file) {
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            return readMD5(is);
        } catch (Throwable e) {

        } finally {
            closeSilently(is);
        }
        return null;
    }

}
