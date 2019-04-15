package com.murat.gles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.FrameLayout;
import android.widget.Toast;

import com.murat.gles.common.GLView;
import com.murat.gles.particle.ParticleConfig;
import com.murat.gles.particle.ParticleShooter;
import com.murat.gles.common.GLUtils;
import com.murat.particles.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean mSupport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mSupport = GLUtils.isSupportES20(this);
        findViewById(R.id.btn_start).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start: {
                if (mSupport) {
                    GLView view = new GLView(this);
                    ParticleShooter particleShooter = new ParticleShooter(ParticleConfig.JSON);
                    view.removeAll();
                    view.add(particleShooter, "particle");
                    FrameLayout frameLayout = findViewById(R.id.frame);
                    frameLayout.removeAllViews();
                    frameLayout.addView(view);
                } else {
                    Toast.makeText(this, "Your device Does not support OpenGL ES 2.0", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
