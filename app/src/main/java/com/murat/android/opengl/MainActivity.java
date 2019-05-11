package com.murat.android.opengl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.murat.android.opengl.particle.Particle;
import com.murat.android.opengl.sprite.Sprite;


public class MainActivity extends AppCompatActivity {

    FrameLayout mGLFrame;
    GLView mGLView;
    Particle mParticle1;
    Sprite mSprite1;

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
        mGLView = new GLView(getApplicationContext());
        mParticle1 = new Particle(getApplicationContext(), R.raw.particle_star);
        mSprite1 = new Sprite(getApplicationContext(), R.drawable.magic_wand);

        mGLView.add(mParticle1);
        mGLView.add(mSprite1);
        mGLFrame.addView(mGLView);
        mGLView.start();
    }

    private void particleEffect(float x, float y) {
        if (mGLView != null && mParticle1 != null) {
            mSprite1.setAnim(R.raw.sprite_test_final);
            mSprite1.startAnim();
            mParticle1.show(x, y, 1f);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        particleEffect(event.getRawX(), event.getRawY());
        return super.onTouchEvent(event);
    }

}
