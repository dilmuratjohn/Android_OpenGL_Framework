package com.murat.gles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.murat.gles.common.GLView;
import com.murat.gles.particle.ParticleRenderer;
import com.murat.gles.sprite.SpriteRenderer;
import com.murat.particles.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main);

        FrameLayout mGLFrame = findViewById(R.id.frame);
        GLView mGLView = new GLView(getApplicationContext());
        SpriteRenderer magicWand = new SpriteRenderer(R.drawable.magic_stick1);
        SpriteRenderer magicWandLight = new SpriteRenderer(R.drawable.magic_stick2);
        SpriteRenderer magicStick = new SpriteRenderer(R.drawable.magic_wand);
        SpriteRenderer wall = new SpriteRenderer(R.drawable.wall);
        ParticleRenderer particleRibbon = new ParticleRenderer(getApplicationContext(), R.raw.particle_ribbon);

        mGLView.add(magicWand)
        .add(magicWandLight)
        .add(magicStick)
        .add(wall);
        mGLFrame.addView(mGLView);

    }

}
