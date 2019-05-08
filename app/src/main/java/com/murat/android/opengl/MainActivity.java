package com.murat.android.opengl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.murat.android.opengl.animation.Animator;
import com.murat.android.opengl.particle.ParticleRenderer;
import com.murat.android.opengl.sprite.SpriteRenderer;


public class MainActivity extends AppCompatActivity {


    FrameLayout mGLFrame;
    GLView mGLView;
    ParticleRenderer mBomb1;
    ParticleRenderer mBomb2;
    SpriteRenderer mWall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);

        mGLFrame = findViewById(R.id.frame);
        mGLView = new GLView(getApplicationContext());
        mBomb1 = new ParticleRenderer(getApplicationContext(), R.raw.particle_star);
        mBomb2 = new ParticleRenderer(getApplicationContext(), R.raw.particle_star2);
        mWall= new SpriteRenderer(getApplicationContext(), R.drawable.wall);
        mGLView.add(mBomb1);
        mGLView.add(mBomb2);
        mGLView.add(mWall);
        mGLFrame.addView(mGLView);


        Animator animator = new Animator(getApplicationContext(), R.raw.sprite_test, mWall.getActionInterval());
        animator.play();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        showBombEffect(event.getRawX(), event.getRawY());
        return super.onTouchEvent(event);
    }

    private void showBombEffect(float x, float y) {
        if (mGLView != null && mBomb1 != null) {
            mBomb1.show(x, y, 0f);
        }

        if (mGLView != null && mBomb2 != null) {
            mBomb2.show(x, y, 0f);
            mBomb2.translate(0f, 1f, 0f);
        }
    }

}
