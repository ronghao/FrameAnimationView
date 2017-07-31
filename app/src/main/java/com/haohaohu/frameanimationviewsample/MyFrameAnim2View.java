package com.haohaohu.frameanimationviewsample;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;

import com.haohaohu.frameanimationview.R;
import com.haohaohu.frameanimview.FrameAnimView;
import com.haohaohu.frameanimview.FrameAnimation;
import com.haohaohu.frameanimview.OnImageLoadListener;

/**
 * 帧动画view
 *
 * @author haohao on 2017/6/27 13:43
 * @version v1.0
 */
public class MyFrameAnim2View extends FrameAnimView {


    int[] res = {R.drawable.spinner_0, R.drawable.spinner_1, R.drawable.spinner_2, R.drawable.spinner_3,
            R.drawable.spinner_4, R.drawable.spinner_5, R.drawable.spinner_6, R.drawable.spinner_7
            , R.drawable.spinner_8, R.drawable.spinner_9, R.drawable.spinner_10, R.drawable.spinner_11};

    public MyFrameAnim2View(Context context) {
        super(context);
        init();
    }

    public MyFrameAnim2View(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyFrameAnim2View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mAnimation = new FrameAnimation.FrameAnimationBuilder(getResources())
                .setLoop(true)
                .setResIds(res)
                .setDuration(100)
                .isLowMemory(true)
                .build();
        mAnimation.setOnImageLoadListener(new OnImageLoadListener() {
            @Override
            public void onImageLoad(BitmapDrawable drawable) {
                MyFrameAnim2View.this.setImageDrawable(drawable);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
