package com.murat.gles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.FrameLayout;

import com.murat.gles.common.GLView;
import com.murat.gles.particle.ParticleRenderer;
import com.murat.gles.picture.SpriteRenderer;
import com.murat.particles.R;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mGLFrame;
    private GLView mGLView;
    private SpriteRenderer mMagicWand;
    private SpriteRenderer mMagicWandLight;
    private ParticleRenderer mParticleRibbon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mGLFrame = findViewById(R.id.frame);
        mGLView = new GLView(getApplicationContext());
        mMagicWand = new SpriteRenderer(R.drawable.magic_stick1);
        mMagicWandLight = new SpriteRenderer(R.drawable.magic_stick2);
        mParticleRibbon = new ParticleRenderer(getApplicationContext(), R.raw.particle_ribbon);

        mGLView.add(mMagicWand);
        mGLView.add(mParticleRibbon);
        mGLView.add(mMagicWandLight);
        mGLFrame.addView(mGLView);

    }

}
