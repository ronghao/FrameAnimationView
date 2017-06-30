package com.haohaohu.frameanimationviewsample;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.haohaohu.frameanimationview.R;
import com.haohaohu.frameanimview.FrameAnimView;

public class MainActivity extends AppCompatActivity {

    private MyFrameAnimView animView;
    private AnimationDrawable animationDrawable;
    private FrameAnimView frameAnimView;

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
                ImageView image = (ImageView) findViewById(R.id.main_animview1);
                image.setImageResource(R.drawable.anim_loading);
                animationDrawable = (AnimationDrawable) image.getDrawable();
                animationDrawable.start();
            }
        });

        findViewById(R.id.main_btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animView != null) {
                    animView.stop();
                }
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
}
