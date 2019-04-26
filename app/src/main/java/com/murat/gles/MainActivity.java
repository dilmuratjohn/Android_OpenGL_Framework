package com.murat.gles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import android.widget.FrameLayout;
import android.widget.Toast;

import com.murat.gles.common.GLView;
import com.murat.gles.particle.ParticleShooter;
import com.murat.gles.common.GLUtils;
import com.murat.particles.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean mSupport;
    private FrameLayout mGLFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mGLFrame = findViewById(R.id.frame);
        mSupport = GLUtils.isSupportES20(this);
        findViewById(R.id.frame).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frame: {
                if (mSupport) {
                    mGLFrame.removeAllViews();
                    GLView view = new GLView(this);
                    view.removeAll();
                    String config = Utils.getJSONStringFromResource(getApplicationContext(), R.raw.particle_ribbon);
                    if (!TextUtils.isEmpty(config)) {
                        ParticleShooter particleRibbon = new ParticleShooter(config);
                        view.add(particleRibbon);
                        mGLFrame.addView(view);
                    }
                } else {
                    Toast.makeText(this, "Your device Does not support OpenGL ES 2.0", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
