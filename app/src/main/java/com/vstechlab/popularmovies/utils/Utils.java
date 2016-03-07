package com.vstechlab.popularmovies.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.nio.ByteBuffer;

public class Utils {

    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        int bytes = bitmap.getByteCount();
        ByteBuffer byteBuffer = ByteBuffer.allocate(bytes);
        bitmap.copyPixelsToBuffer(byteBuffer);
        return byteBuffer.array();
    }

    public static Bitmap byteArrayToBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
