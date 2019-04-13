package com.murat.gles;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.murat.gles.particle.ParticleView;
import com.murat.particles.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnStart;
    private boolean mSupport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mSupport = isSupportES20();
        mBtnStart = findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(this);
    }

    public boolean isSupportES20() {
        final ActivityManager activityManager = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        return configurationInfo.reqGlEsVersion >= 0x20000
                || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                && (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")));
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_start && mSupport) {
            if (mSupport) {
                ParticleView view = new ParticleView(this);
                FrameLayout frameLayout = findViewById(R.id.frame);
                frameLayout.removeAllViews();
                frameLayout.addView(view);
            } else {
                Toast.makeText(this, "Your device Does not support OpenGL ES 2.0", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
