package com.murat.gles.actions;

import android.os.Handler;

public class ActionInterval {

    private final float sample = 30f;
    private final float speed = 1f;
    private final float deltaTime = speed / sample * 60 * 16;

    private Runnable mAction = new Runnable() {
        @Override
        public void run() {
            if (moveTimes > 0) {
                moveTimes--;
                move();
            }
            if (scaleTimes > 0) {
                scaleTimes--;
                scale();
            }
            if (rotateTimes > 0) {
                rotateTimes--;
                rotate();
            }
            if (fadeTimes > 0) {
                fadeTimes--;
                fade();
            }
            if (tintTimes > 0) {
                tintTimes--;
                tint();
            }
            mHandler.postDelayed(mAction, (long) deltaTime);
        }
    };
    private Handler mHandler = new Handler();

    private Action ref;

    public ActionInterval(Action ref) {
        this.ref = ref;
        mHandler.postDelayed(mAction, (long) deltaTime);
    }

    private float[] moveFraction = new float[3];
    private float[] scaleFraction = new float[3];
    private float[] rotateFraction = new float[4];
    private float[] colorFraction = new float[4];

    private int moveTimes;
    private int scaleTimes;
    private int rotateTimes;
    private int fadeTimes;
    private int tintTimes;

    public ActionInterval move(float x, float y, float z, float duration) {
        moveTimes = (int) (duration * 1000f / deltaTime) + 1;
        if (moveTimes <= 0) {
            moveFraction = new float[]{x, y, z};
        } else {
            moveFraction = new float[]{x / moveTimes, y / moveTimes, z / moveTimes};
        }
        return this;
    }

    public ActionInterval move(final float x, final float y, final float z, final float duration, final float delayed) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                move(x, y, z, duration);
            }
        }, (long) delayed * 1000);
        return this;
    }

    private void move() {
        ref.translate(moveFraction[0], moveFraction[1], moveFraction[2]);
    }

    public ActionInterval scale(float x, float y, float z, float duration) {
        scaleTimes = (int) (duration * 1000f / deltaTime) + 1;
        if (scaleTimes <= 0) {
            scaleFraction = new float[]{x, y, z};
        } else {
            scaleFraction = new float[]{x / scaleTimes, y / scaleTimes, z / scaleTimes};
        }
        return this;
    }

    public ActionInterval scale(final float x, final float y, final float z, final float duration, final float delayed) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                scale(x, y, z, duration);
            }
        }, (long) delayed * 1000);
        return this;
    }

    private void scale() {
        ref.scale(scaleFraction[0], scaleFraction[1], scaleFraction[2]);
    }

    public ActionInterval rotate(float a, float x, float y, float z, float duration) {
        rotateTimes = (int) (duration * 1000f / deltaTime) + 1;
        if (duration <= 0) {
            rotateFraction = new float[]{a, x, y, z};
        } else {
            rotateFraction = new float[]{a / rotateTimes, x / rotateTimes, y / rotateTimes, z / rotateTimes};
        }
        return this;
    }

    public ActionInterval rotate(final float a, final float x, final float y, final float z, final float duration, final float delayed) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                rotate(a, x, y, z, duration);
            }
        }, (long) delayed * 1000);
        return this;
    }

    private void rotate() {
        ref.rotate(rotateFraction[0], rotateFraction[1], rotateFraction[2], rotateFraction[3]);
    }

    public ActionInterval fade(float a, float duration) {
        fadeTimes = (int) (duration * 1000f / deltaTime) + 1;
        if (fadeTimes <= 0) {
            colorFraction = new float[]{0, 0, 0, a};
        } else {
            colorFraction = new float[]{0, 0, 0, a / fadeTimes};
        }
        return this;
    }

    public ActionInterval fade(final float a, final float duration, final float delayed) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fade(a, duration);
            }
        }, (long) delayed * 1000);
        return this;
    }

    private void fade() {
        ref.fade(colorFraction[3]);
    }

    public ActionInterval tint(float r, float g, float b, float duration) {
        tintTimes = (int) (duration * 1000f / deltaTime) + 1;
        if (tintTimes <= 0) {
            colorFraction = new float[]{r, g, b, 0};
        } else {
            colorFraction = new float[]{r / tintTimes, g / tintTimes, b / tintTimes, 0};
        }
        return this;
    }

    public ActionInterval tint(final float r, final float g, final float b, final float duration, final float delayed) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tint(r, g, b, duration);
            }
        }, (long) delayed * 1000);
        return this;
    }

    private void tint() {
        ref.tint(colorFraction[0], colorFraction[1], colorFraction[2]);
    }

}