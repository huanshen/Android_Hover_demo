package com.example.shenjiaqi.hoverdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

/**
 * 主activity
 *
 * @author shenjiaqi
 * @since 2017/5/28
 */
public class MainActivity extends AppCompatActivity {

    private HoverWindowController mHoverWindowController;
    private MyHoverView mHoverPlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mHoverWindowController = new HoverWindowController((WindowManager) getSystemService(Context.WINDOW_SERVICE), MainActivity.this);
        Button button = findViewById(R.id.btn);
        mHoverPlayerView = MyHoverView.createForWindow(MainActivity.this, mHoverWindowController);

        // 移除
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHoverWindowController != null) {
                    mHoverWindowController.removeView(mHoverPlayerView);
                    mHoverPlayerView = MyHoverView.createForWindow(MainActivity.this, mHoverWindowController);
                }
            }
        });
        // 添加
        Button button1 = findViewById(R.id.btn1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHoverWindowController != null && !mHoverPlayerView.isShow()) {
                    mHoverPlayerView.addToWindow(300, 1000);
                    mHoverPlayerView.moved();
                }else {
                    Toast.makeText(MainActivity.this, "已加载到window中", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

    }
}
