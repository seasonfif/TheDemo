package com.demo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class BmpUtil {

    public static byte[] convertBitmap2Bytes(Bitmap bm) {
        if (bm == null || bm.isRecycled()) {
//            return null;
        }

        boolean finished = false;
        int quality = 1000;
        byte[] bytes = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while(!finished){
            try{
                baos.reset();
//                bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
//                bytes = baos.toByteArray();
//                bytes = new byte[2000000000 * quality];
                big(quality*1024*512);
                finished = true;
            }catch (OutOfMemoryError e){
                quality /= 10;
                Log.e("OutOfMemoryError", ""+quality);
            }
        }
        return bytes;
    }

    public static void testBmpOOM(Context context){
//        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.big);
        convertBitmap2Bytes(null);

    }

    private static void big(int length) {
        StringBuilder sb = new StringBuilder(length);
        for(int i=0; i < length; i++)
            sb.append('a');
        sb.append(System.nanoTime());
        sb.toString();
    }
}
