package com.haohaohu.frameanimview;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;

import java.lang.ref.SoftReference;

import static android.R.attr.height;
import static android.R.attr.width;

/**
 * 帧动画动画类
 *
 * @author haohao on 2017/6/27 14:28
 * @version v1.0
 */
public class FrameAnimation {

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (onImageLoadListener != null) {
                onImageLoadListener.onImageLoad(bitmapDrawable);
            }
            loadInThreadFromRes();
        }
    };

    public static final float DEFAULT_DURATION = 100f;

    private int[] resIds;//动画列表
    private volatile float duration = DEFAULT_DURATION;//动画间隔时间
    private boolean loop = false;//是否循环
    private boolean isRunning = false;
    private boolean needStop = false;

    private volatile int index = 0;//当前显示图片
    private ImageCache imageCache;
    private volatile BitmapDrawable bitmapDrawable;
    private OnImageLoadListener onImageLoadListener;
    private Resources resources;

    FrameAnimation(Resources resources, int[] resIds, boolean loop, float duration) {
        imageCache = new ImageCache();
        this.resources = resources;
        this.resIds = resIds;
        this.loop = loop;
        this.duration = duration;
    }

    public void setResIds(int[] resIds) {
        this.resIds = resIds;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public void start() {
        if (isRunning) {
            return;
        }
        loadInThreadFromRes();
    }

    public void pause() {
        if (!isRunning) {
            return;
        }
        needStop = true;
    }

    public void stop() {
        if (!isRunning) {
            return;
        }
        index = 0;
        needStop = true;
        imageCache.destory();
    }

    private void loadInThreadFromRes() {
        if (resIds == null || resIds.length == 0) {
            isRunning = false;
            return;
        }
        if (needStop) {
            isRunning = false;
            needStop = false;
            return;
        }
        isRunning = true;
        if (index < resIds.length) {
            int resId = resIds[index];
            if (bitmapDrawable != null) {
                imageCache.mReusableBitmaps.add(new SoftReference<>(bitmapDrawable.getBitmap()));
            }
            long start = System.currentTimeMillis();
            bitmapDrawable =
                    BitmapLoadUtil.decodeBitmapFromResLruCache(resources, resId, width,
                            height,
                            imageCache);
            long end = System.currentTimeMillis();
            float updateTime = (duration - (end - start)) > 0 ? (duration - (end - start)) : 0;
            Message message = Message.obtain();
            message.obj = resIds;
            handler.sendMessageAtTime(message,
                    index == 0 ? 0 : (int) (SystemClock.uptimeMillis() + updateTime));
            index++;
        } else {
            if (loop) {
                index = 0;
                loadInThreadFromRes();
            } else {
                index++;
                bitmapDrawable = null;
                duration = 0;
                if (onImageLoadListener != null) {
                    onImageLoadListener.onFinish();
                }
                isRunning = false;
                onImageLoadListener = null;
            }
        }
    }

    public void setOnImageLoadListener(OnImageLoadListener onImageLoadListener) {
        this.onImageLoadListener = onImageLoadListener;
    }

    public static class FrameAnimationBuilder {

        private Resources resources;
        private float duration = DEFAULT_DURATION;//动画间隔时间
        private boolean loop = false;//是否循环
        private int[] resIds;//动画列表

        public FrameAnimationBuilder(@NonNull Resources resources) {
            this.resources = resources;
        }

        public FrameAnimationBuilder setResIds(int[] resIds) {
            this.resIds = resIds;
            return this;
        }

        public FrameAnimationBuilder setLoop(boolean loop) {
            this.loop = loop;
            return this;
        }

        public FrameAnimationBuilder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public FrameAnimation build() {
            return new FrameAnimation(resources, resIds, loop, duration);
        }
    }
}
