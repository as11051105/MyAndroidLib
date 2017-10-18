package com.infrastructure.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.google.webp.libwebp;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class WebpUtils {

    static {
        System.loadLibrary("webp");
    }

    public static Bitmap webpToBitmap(byte[] encoded) {
        int[] width = new int[]{0};
        int[] height = new int[]{0};
        byte[] decoded = libwebp.WebPDecodeARGB(encoded, encoded.length, width, height);

        int[] pixels = new int[decoded.length / 4];
        ByteBuffer.wrap(decoded).asIntBuffer().get(pixels);

        return Bitmap.createBitmap(pixels, width[0], height[0], Bitmap.Config.ARGB_8888);

    }

    public static byte[] streamToBytes(InputStream in) {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[1024];
        int len = -1;
        try {
            while ((len = in.read(buffer)) >= 0) {
                out.write(buffer, 0, len);
                out.flush();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return out.toByteArray();
    }

    public static Bitmap getWebpBitmap(Context ctx, int imageId) {
        InputStream rawImageStream = ctx.getResources().openRawResource(imageId);
        byte[] data = WebpUtils.streamToBytes(rawImageStream);
        return WebpUtils.webpToBitmap(data);
    }

    public static Bitmap getWebpBitmapIS(Context ctx, InputStream is) {
        byte[] data = WebpUtils.streamToBytes(is);
        return WebpUtils.webpToBitmap(data);
    }

    public static Drawable getWebpDrawable(Context ctx, int imageId) {
        return ImageUtil.bitmapToDrawable(getWebpBitmap(ctx, imageId));
    }

    public static Drawable getWebpDrawableIS(Context ctx, InputStream is) {
        return ImageUtil.bitmapToDrawable(getWebpBitmapIS(ctx, is));
    }
}
