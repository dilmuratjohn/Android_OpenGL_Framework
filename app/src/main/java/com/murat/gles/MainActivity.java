package com.murat.gles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.murat.gles.particle.ParticleRenderer;
import com.murat.gles.sprite.SpriteRenderer;
import com.murat.opengl.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main);

        FrameLayout mGLFrame = findViewById(R.id.frame);
        GLView mGLView = new GLView(getApplicationContext());
        SpriteRenderer magicWand = new SpriteRenderer(getApplicationContext(), R.drawable.magic_stick1);
        SpriteRenderer magicWandLight = new SpriteRenderer(getApplicationContext(), R.drawable.magic_stick2);
        SpriteRenderer magicStick = new SpriteRenderer(getApplicationContext(), R.drawable.magic_wand);
        SpriteRenderer wall = new SpriteRenderer(getApplicationContext(), R.drawable.wall);
        ParticleRenderer particleFirework = new ParticleRenderer(getApplicationContext(), R.raw.particle_firework);
        ParticleRenderer particleGalaxy = new ParticleRenderer(getApplicationContext(), R.raw.particle_galaxy);
        ParticleRenderer particleCircle = new ParticleRenderer(getApplicationContext(), R.raw.particle_circle);

        ParticleRenderer particleHeal = new ParticleRenderer(getApplicationContext(), R.raw.particle_heal);
        ParticleRenderer particleMeteor = new ParticleRenderer(getApplicationContext(), R.raw.particle_meteor);
        ParticleRenderer particleRibbon = new ParticleRenderer(getApplicationContext(), R.raw.particle_ribbon);
        ParticleRenderer particleSmoke = new ParticleRenderer(getApplicationContext(), R.raw.particle_smoke);

        mGLView
//                .add(magicStick)
//                .add(magicWand)
//                .add(magicWandLight)
//                .add(wall)
                .add(particleFirework)
                .add(particleCircle)
                .add(particleGalaxy)
//                .add(particleHeal)
//                .add(particleMeteor)
                .add(particleRibbon)
//                .add(particleSmoke)
        ;

        mGLFrame.addView(mGLView);

    }

}
