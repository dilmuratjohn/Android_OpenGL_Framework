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
    ParticleRenderer mParticle1;
    ParticleRenderer mParticle2;
    SpriteRenderer mSprite1;
    SpriteRenderer mSprite2;
    SpriteRenderer mSprite3;
    SpriteRenderer mSprite4;
    SpriteRenderer mSprite5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);

        mGLFrame = findViewById(R.id.frame);
        mGLView = new GLView(getApplicationContext());

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        particleEffect(event.getRawX(), event.getRawY());
        return super.onTouchEvent(event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mParticle1 = new ParticleRenderer(getApplicationContext(), R.raw.particle_star);
        mParticle2 = new ParticleRenderer(getApplicationContext(), R.raw.particle_star2);

        mSprite1 = new SpriteRenderer(getApplicationContext(), R.drawable.wall);
//        mSprite2 = new SpriteRenderer(getApplicationContext(), R.drawable.magic_wand);
//        mSprite3 = new SpriteRenderer(getApplicationContext(), R.drawable.star);
//        mSprite4 = new SpriteRenderer(getApplicationContext(), R.drawable.star);
//        mSprite5 = new SpriteRenderer(getApplicationContext(), R.drawable.star);

        mGLView.add(mParticle1);
        mGLView.add(mParticle2);

        mGLView.add(mSprite1);
//        mGLView.add(mSprite2);
//        mGLView.add(mSprite3);
//        mGLView.add(mSprite4);
//        mGLView.add(mSprite5);
        mGLFrame.addView(mGLView);
        spriteAnim();
    }

    private void spriteAnim() {
        super.onStart();

        new Animator(getApplicationContext(), R.raw.sprite_test_3, mSprite1.getActionInterval()).play();
//        new Animator(getApplicationContext(), R.raw.sprite_test_position, mSprite2.getActionInterval()).play();
//        new Animator(getApplicationContext(), R.raw.sprite_test_color, mSprite3.getActionInterval()).play();
//        new Animator(getApplicationContext(), R.raw.sprite_test_scale, mSprite4.getActionInterval()).play();
//        new Animator(getApplicationContext(), R.raw.sprite_test_rotate, mSprite5.getActionInterval()).play();

//        mSprite1.getActionInterval().move(0f, 1.0f, 0, 0f);
//        mSprite2.getActionInterval().move(0f, 0.5f, 0, 0f);
//        mSprite3.getActionInterval().move(0f, 0f, 0, 0f);
//        mSprite4.getActionInterval().move(0f, -0.5f, 0, 0f);
//        mSprite5.getActionInterval().move(0f, -1.0f, 0, 0f);
    }

    private void particleEffect(float x, float y) {
        if (mGLView != null && mParticle1 != null) {
            mParticle1.show(x, y, 0f);
        }

        if (mGLView != null && mParticle2 != null) {
            mParticle2.show(x, y, 0f);
            mParticle2.translate(0f, 1f, 0f);
        }
    }

}
