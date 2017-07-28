package com.haohaohu.frameanimationviewsample;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.haohaohu.frameanimationview.R;
import com.haohaohu.frameanimview.FrameAnimView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private MyFrameAnimView animView;//自定义FrameAnimView
    private AnimationDrawable animationDrawable;//常规动画
    private FrameAnimView frameAnimView;//FrameAnimView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {

        findViewById(R.id.main_btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animView != null) {
                    animView.start();
                }
            }
        });

        findViewById(R.id.main_btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animView != null) {
                    animView.stop();
                }
            }
        });

        findViewById(R.id.main_btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView image = (ImageView) findViewById(R.id.main_animview1);
                image.setImageResource(R.drawable.anim_loading);
                animationDrawable = (AnimationDrawable) image.getDrawable();
                animationDrawable.start();
            }
        });

        findViewById(R.id.main_btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animationDrawable != null) {
                    animationDrawable.stop();
                }
            }
        });

        findViewById(R.id.main_btn5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (frameAnimView != null) {
                    frameAnimView.start();
                }
            }
        });

        findViewById(R.id.main_btn6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (frameAnimView != null) {
                    frameAnimView.stop();
                }
            }
        });
        animView = (MyFrameAnimView) findViewById(R.id.main_animview);
        frameAnimView = (FrameAnimView) findViewById(R.id.main_animview2);

    }

    private boolean isExit;

    public boolean onKeyDown(int kCode, KeyEvent kEvent) {
        switch (kCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
            case KeyEvent.KEYCODE_DPAD_DOWN:
            case KeyEvent.KEYCODE_DPAD_CENTER:
                break;
            case KeyEvent.KEYCODE_BACK:
                exitBy2Click();
                return true;
        }
        return super.onKeyDown(kCode, kEvent);
    }

    private void exitBy2Click() {
        Timer tExit;
        if (!isExit) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            finish();
            System.exit(0);
        }
    }
}
