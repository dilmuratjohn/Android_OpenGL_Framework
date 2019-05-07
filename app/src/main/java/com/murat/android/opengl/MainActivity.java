package com.murat.android.opengl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.murat.android.opengl.particle.ParticleRenderer;

import java.util.Random;


public class MainActivity extends AppCompatActivity {


    FrameLayout mGLFrame;
    GLView mGLView;
    ParticleRenderer mBomb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);

        mGLFrame = findViewById(R.id.frame);
        mGLView = new GLView(getApplicationContext());
        mBomb = new ParticleRenderer(getApplicationContext(), R.raw.particle_bomb);
        mGLView.add(mBomb);
        mGLFrame.addView(mGLView);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        showBombEffect(event.getRawX(), event.getRawY());
        return super.onTouchEvent(event);
    }

    private void showBombEffect(float x, float y) {
        if (mGLView != null && mBomb != null) {
            mBomb.show(x, y, new Random().nextFloat());
        }
    }

}
