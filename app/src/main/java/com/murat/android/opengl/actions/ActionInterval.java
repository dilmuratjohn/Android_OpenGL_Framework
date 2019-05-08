package com.murat.android.opengl.actions;

import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;

public class ActionInterval {

    private final float sample = 60f;
    private final float speed = 1f;
    private final float deltaTime = speed / sample * 60 * 16.6f;

    private Runnable mAction = new Runnable() {
        @Override
        public void run() {
            if (mMoveTime > 0) {
                mMoveTime--;
                move();
            } else {
                if (mListMoveTimes.size() > 0) {
                    mMoveTime = mListMoveTimes.remove(0);
                    mMoveFraction = mListMoveFractions.remove(0);
                }
            }
            if (mScaleTime > 0) {
                mScaleTime--;
                scale();
            } else {
                if (mListScaleTimes.size() > 0) {
                    mScaleTime = mListScaleTimes.remove(0);
                    mScaleFraction = mListScaleFractions.remove(0);
                }
            }
            if (mRotateTimes > 0) {
                mRotateTimes--;
                rotate();
            } else {
                if (mListRotateTimes.size() > 0) {
                    mRotateTimes = mListRotateTimes.remove(0);
                    mRotateFraction = mListRotateFractions.remove(0);
                }
            }
            if (mFadeTimes > 0) {
                mFadeTimes--;
                fade();
            } else {
                if (mListFadeTimes.size() > 0) {
                    mFadeTimes = mListFadeTimes.remove(0);
                    mFadeFraction = mListFadeFractions.remove(0);
                }
            }
            if (mTintTimes > 0) {
                mTintTimes--;
                tint();
            } else {
                if (mListTintTimes.size() > 0) {
                    mTintTimes = mListTintTimes.remove(0);
                    mTintFraction = mListTintFractions.remove(0);
                }
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

    private float[] mMoveFraction = new float[3];
    private float[] mScaleFraction = new float[3];
    private float[] mRotateFraction = new float[4];
    private float[] mTintFraction = new float[3];
    private float[] mFadeFraction = new float[1];

    private int mMoveTime;
    private int mScaleTime;
    private int mRotateTimes;
    private int mFadeTimes;
    private int mTintTimes;

    private ArrayList<Integer> mListMoveTimes = new ArrayList<>();
    private ArrayList<float[]> mListMoveFractions = new ArrayList<>();

    private ArrayList<Integer> mListScaleTimes = new ArrayList<>();
    private ArrayList<float[]> mListScaleFractions = new ArrayList<>();

    private ArrayList<Integer> mListRotateTimes = new ArrayList<>();
    private ArrayList<float[]> mListRotateFractions = new ArrayList<>();

    private ArrayList<Integer> mListFadeTimes = new ArrayList<>();
    private ArrayList<float[]> mListFadeFractions = new ArrayList<>();

    private ArrayList<Integer> mListTintTimes = new ArrayList<>();
    private ArrayList<float[]> mListTintFractions = new ArrayList<>();

    public ActionInterval move(float x, float y, float z, float duration) {
        int moveTimes = (int) (duration * 1000f / deltaTime);
        float[] moveFraction;
        if (duration <= 0) {
            moveFraction = new float[]{x, y, z};
        } else {
            moveFraction = new float[]{x / moveTimes, y / moveTimes, z / moveTimes};
        }
        mListMoveTimes.add(moveTimes);
        mListMoveFractions.add(moveFraction);
        return this;
    }

    public ActionInterval scale(float x, float y, float z, float duration) {
        int scaleTimes = (int) (duration * 1000f / deltaTime);
        float[] scaleFraction;
        if (duration <= 0) {
            scaleFraction = new float[]{x, y, z};
        } else {
            scaleFraction = new float[]{x / scaleTimes, y / scaleTimes, z / scaleTimes};
        }
        mListScaleTimes.add(scaleTimes);
        mListScaleFractions.add(scaleFraction);
        return this;
    }


    public ActionInterval rotate(float a, float x, float y, float z, float duration) {
        int rotateTimes = (int) (duration * 1000f / deltaTime);
        float[] rotateFraction;
        if (duration <= 0) {
            rotateFraction = new float[]{a, x, y, z};
        } else {
            rotateFraction = new float[]{a / rotateTimes, x / rotateTimes, y / rotateTimes, z / rotateTimes};
        }
        mListRotateTimes.add(rotateTimes);
        mListRotateFractions.add(rotateFraction);
        return this;
    }


    public ActionInterval tint(float r, float g, float b, float duration) {
        int tintTimes = (int) (duration * 1000f / deltaTime);
        float[] tintFraction;
        if (duration <= 0) {
            tintFraction = new float[]{r, g, b};
        } else {
            tintFraction = new float[]{r / tintTimes, g / tintTimes, b / tintTimes};
            Log.e("Murat", "r " + tintFraction[0] + " g " + tintFraction[1] + " b " + tintFraction[2] + " times " + tintTimes);
        }
        mListTintTimes.add(tintTimes);
        mListTintFractions.add(tintFraction);
        return this;
    }

    public ActionInterval fade(float a, float duration) {
        int fadeTimes = (int) (duration * 1000f / deltaTime);
        float[] fadeFraction;
        if (duration <= 0) {
            fadeFraction = new float[]{a};
        } else {
            fadeFraction = new float[]{a / fadeTimes};
            Log.e("Murat", "a " + fadeFraction[0] + " times " + fadeTimes);
        }
        mListFadeTimes.add(fadeTimes);
        mListFadeFractions.add(fadeFraction);
        return this;
    }

    private void move() {
        ref.translate(mMoveFraction[0], mMoveFraction[1], mMoveFraction[2]);
    }

    private void scale() {
        ref.scale(mScaleFraction[0], mScaleFraction[1], mScaleFraction[2]);
    }

    private void rotate() {
        ref.rotate(mRotateFraction[0], mRotateFraction[1], mRotateFraction[2], mRotateFraction[3]);
    }

    private void tint() {
        Log.e("Murat", "tint"+mTintFraction[0]+ mTintFraction[1]+ mTintFraction[2]);
        ref.tint(mTintFraction[0], mTintFraction[1], mTintFraction[2]);
    }

    private void fade() {
        Log.e("Murat", "fade"+mFadeFraction[0]);
        ref.fade(mFadeFraction[0]);
    }


}