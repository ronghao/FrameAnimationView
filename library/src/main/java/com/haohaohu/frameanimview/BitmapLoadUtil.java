package com.haohaohu.frameanimview;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RawRes;

import static android.graphics.BitmapFactory.decodeStream;

/**
 * 加载res图片工具类
 *
 * @author haohao on 2017/6/27 18:18
 * @version v1.0
 */
public class BitmapLoadUtil {
    /**
     * 读取图片 优先从缓存中获取图片
     */
    static BitmapDrawable decodeBitmapFromResLruCache(Resources resources, @RawRes int resId,
                                                      int reqWidth, int reqHeight,
                                                      ImageCache cache) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        decodeStream(resources.openRawResource(resId), null, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        BitmapDrawable bitmapFromCache;
        String resourceName = resources.getResourceName(resId);
        bitmapFromCache = cache.getBitmapFromCache(resourceName);
        if (bitmapFromCache == null) {
            bitmapFromCache = readBitmapFromRes(resources, resId, cache, options);
            cache.addBitmapToCache(resourceName, bitmapFromCache);
        }
        return bitmapFromCache;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight, ImageCache cache) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // END_INCLUDE (read_bitmap_dimensions)

        addInBitmapOptions(options, cache);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    @NonNull
    private static BitmapDrawable readBitmapFromRes(Resources resources, @RawRes int resId, ImageCache cache, BitmapFactory.Options options) {
        addInBitmapOptions(options, cache);
        options.inJustDecodeBounds = false;
        return new BitmapDrawable(resources,
                decodeStream(resources.openRawResource(resId), null, options));
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static void addInBitmapOptions(BitmapFactory.Options options, ImageCache cache) {
        //BEGIN_INCLUDE(add_bitmap_options)
        // inBitmap only works with mutable bitmaps so force the decoder to
        // return mutable bitmaps.
        options.inMutable = true;

        if (cache != null) {
            // Try and find a bitmap to use for inBitmap
            Bitmap inBitmap = cache.getBitmapFromReusableSet(options);

            if (inBitmap != null) {
                options.inBitmap = inBitmap;
            }
        }
        //END_INCLUDE(add_bitmap_options)
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // BEGIN_INCLUDE (calculate_sample_size)
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).

            long totalPixels = width * height / inSampleSize;

            // Anything more than 2x the requested pixels we'll sample down further
            final long totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels > totalReqPixelsCap) {
                inSampleSize *= 2;
                totalPixels /= 2;
            }
        }
        return inSampleSize;
        // END_INCLUDE (calculate_sample_size)
    }
}
