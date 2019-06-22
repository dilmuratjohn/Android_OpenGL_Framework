package com.murat.android.opengl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.murat.android.opengl.sprite.Sprite;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ViewGroup mGLFrame;
    GLView mGLView;
    Sprite mSprite1;
    Sprite mSprite2;
    Button mSmall, mStart, mBig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        init();
    }

    private void init() {
        mGLFrame = findViewById(R.id.frame);
        mBig = findViewById(R.id.btn_big);
        mSmall = findViewById(R.id.btn_small);
        mStart = findViewById(R.id.btn_start);
        mBig.setOnClickListener(this);
        mSmall.setOnClickListener(this);
        mStart.setOnClickListener(this);
        mGLView = new GLView(getApplicationContext());
        mGLFrame.addView(mGLView);
        mSprite1 = new Sprite(this, R.drawable.number, 0, 0);
        mGLView.add(mSprite1);
        mSprite2 = new Sprite(this, R.drawable.number, 1, 1);
        mGLView.add(mSprite2);
        mGLView.start();
    }



    float scale1X = 1.5f, scale2X = 1f, scale1Y = 1.5f, scale2Y = 1f;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                mSprite1.translate(-0.5f, 0, 0);
                mSprite2.translate(0.5f,0,0);
                mSprite1.scale(1.5f, 1.5f, 1f);
            case R.id.btn_big:
                big();
                mSprite1.scale(scale1X, scale1Y, 1f);
                mSprite2.scale(scale2X, scale2Y, 1f);
                break;
            case R.id.btn_small:
                small();
                mSprite1.scale(scale1X, scale1Y, 1f);
                mSprite2.scale(scale2X, scale2Y, 1f);
        }
    }

    private void big(){
        scale1X += 0.1f;
        scale2X += 0.1f;
        scale1Y += .1f;
        scale2Y += .1f;
    }

    private void small(){
        scale1X -= .1f;
        scale2X -= .1f;
        scale1Y -= .1f;
        scale2Y -= .1f;
    }
}
