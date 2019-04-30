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

    public ActionInterval moveTo(float x, float y, float z, float duration) {
        moveTimes = (int) (duration * 1000f / deltaTime);
        if (moveTimes <= 0) {
            moveFraction = new float[]{x, y, z};
        } else {
            moveFraction = new float[]{x / moveTimes, y / moveTimes, z / moveTimes};
        }
        return this;
    }

    public ActionInterval moveToDelayed(final float x, final float y, final float z, final float duration, final float delayed) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                moveTo(x, y, z, duration);
            }
        }, (long)delayed * 1000);
        return this;
    }

    public ActionInterval scaleBy(float x, float y, float z, float duration) {
        scaleTimes = (int) (duration * 1000f / deltaTime);
        if (scaleTimes <= 0) {
            scaleFraction = new float[]{x, y, z};
        } else {
            scaleFraction = new float[]{x / scaleTimes, y / scaleTimes, z / scaleTimes};
        }
        return this;
    }

    public ActionInterval scaleByDelayed(final float x, final float y, final float z, final float duration, final float delayed) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                scaleBy(x, y, z, duration);
            }
        }, (long)delayed * 1000);
        return this;
    }

    public ActionInterval rotateTo(float a, float x, float y, float z, float duration) {
        rotateTimes = (int) (duration * 1000f / deltaTime);
        if (duration <= 0) {
            rotateFraction = new float[]{a, x, y, z};
        } else {
            rotateFraction = new float[]{a / rotateTimes, x / rotateTimes, y / rotateTimes, z / rotateTimes};
        }
        return this;
    }

    public ActionInterval rotateToDelayed(final float a, final float x, final float y, final float z, final float duration, final float delayed) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                rotateTo(a, x, y, z, duration);
            }
        }, (long)delayed * 1000);
        return this;
    }

    public ActionInterval fadeTo(float a, float duration) {
        fadeTimes = (int) (duration * 1000f / deltaTime);
        if (fadeTimes <= 0) {
            colorFraction = new float[]{0, 0, 0, a};
        } else {
            colorFraction = new float[]{0, 0, 0, a / fadeTimes};
        }
        return this;
    }

    public ActionInterval fadeByDelayed(final float a, final float duration, final float delayed) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fadeTo(a, duration);
            }
        }, (long)delayed * 1000);
        return this;
    }


    // Tint
    public ActionInterval tintTo(float r, float g, float b, float duration) {
        tintTimes = (int) (duration * 1000f / deltaTime);
        if (tintTimes <= 0) {
            colorFraction = new float[]{r, g, b, 0};
        } else {
            colorFraction = new float[]{r / tintTimes, g / tintTimes, b / tintTimes, 0};
        }
        return this;
    }

    public ActionInterval tintByDelayed(final float r, final float g, final float b, final float duration, final float delayed) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tintTo(r, g, b, duration);
            }
        }, (long)delayed * 1000);
        return this;
    }

    private void move() {
        ref.translate(moveFraction[0], moveFraction[1], moveFraction[2]);
    }

    private void scale() {
        ref.scale(scaleFraction[0], scaleFraction[1], scaleFraction[2]);
    }

    private void rotate() {
        ref.rotate(rotateFraction[0], rotateFraction[1], rotateFraction[2], rotateFraction[3]);
    }

    private void fade() {
        ref.fade(colorFraction[3]);
    }

    private void tint() {
        ref.tint(colorFraction[0], colorFraction[1], colorFraction[2]);
    }


}