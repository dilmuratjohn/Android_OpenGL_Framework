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
    Sprite mSprite;
    Button mSmall, mBig, mUp, mDown, mRight, mLeft;

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
        mUp = findViewById(R.id.btn_up);
        mDown = findViewById(R.id.btn_down);
        mLeft = findViewById(R.id.btn_left);
        mRight = findViewById(R.id.btn_right);
        mBig.setOnClickListener(this);
        mSmall.setOnClickListener(this);
        mUp.setOnClickListener(this);
        mDown.setOnClickListener(this);
        mLeft.setOnClickListener(this);
        mRight.setOnClickListener(this);
        mGLView = new GLView(getApplicationContext());
        mGLFrame.addView(mGLView);
        int [][] numbers5 = new int[][]{{0,0},{0,1},{1,0},{1,1},{0,0}};
        int [][] positions5 = new int[][]{{0,0},{0,0},{0,0},{0,0},{0,0}};
        int [] scales5 = new int[]{1,1,1,1,1};
        int [][] numbers1 = new int[][]{{0,0}};
        int [][] positions1 = new int[][]{{0,0}};
        int [] scales1 = new int[]{1};
        float threshold = 0.1f;
        mSprite = new Sprite(this, R.drawable.number, numbers1, positions1, scales1, threshold);
        mGLView.add(mSprite);
        mGLView.start();
    }

    float scaleX = 1f, scaleY = 1f;
    float x,y;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_up:
                up();
                break;
            case R.id.btn_down:
                down();
                break;
            case R.id.btn_left:
                left();
                break;
            case R.id.btn_right:
                right();
                break;
            case R.id.btn_big:
                big();
                break;
            case R.id.btn_small:
                small();
                break;
        }
    }

    private void up(){
        y+=0.1f;
        mSprite.translate(x,y,0);
    }

    private void down(){
        y-=0.1f;
        mSprite.translate(x,y,0);
    }

    private void left(){
        x-=0.1f;
        mSprite.translate(x,y,0);
    }

    private void right(){
        x+=0.1f;
        mSprite.translate(x,y,0);
    }

    private void big(){
        scaleX += 0.1f;
        scaleY += .1f;
        mSprite.scale(scaleX, scaleY, 1f);

    }

    private void small(){
        scaleX -= .1f;
        scaleY -= .1f;
        mSprite.scale(scaleX, scaleY, 1f);

    }
}
