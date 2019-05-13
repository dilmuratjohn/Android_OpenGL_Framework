package com.murat.android.opengl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.murat.android.opengl.particle.Particle;
import com.murat.android.opengl.sprite.Sprite;


public class MainActivity extends AppCompatActivity {

    FrameLayout mGLFrame;
    GLView mGLView;
    Particle mParticle;
    Sprite mSprite;


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
        mParticle = new Particle(getApplicationContext(), R.raw.particle_galaxy);
        mGLFrame.addView(mGLView);
        Particle particle = new Particle(getApplicationContext(), R.raw.particle_ribbon);
        mGLView.add(particle);
        mGLView.start();
        particle.start();
    }


}
