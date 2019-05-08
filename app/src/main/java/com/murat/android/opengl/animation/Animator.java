package com.murat.android.opengl.animation;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.murat.android.opengl.Utils;
import com.murat.android.opengl.actions.ActionInterval;

import java.util.List;

public class Animator {

    private ActionInterval mRef;
    private AnimationBean mAnimationBean;

    public Animator(Context context, int sourceId, ActionInterval ref) {
        mRef = ref;
        mAnimationBean = new Gson().fromJson(Utils.getJSONStringFromResource(context, sourceId), AnimationBean.class);
    }

    public void play() {
        playPosition();
        playRotation();
        playScale();
        playColor();
    }

    private void playPosition() {
        List<AnimationBean.PropsBean.PositionBean> positionList = mAnimationBean.getProps().getPosition();
        if (null != positionList && positionList.size() > 0) {
            int size = positionList.size();
            float delayTime = 0f;
            for (int i = 0; i < size; i++) {
                AnimationBean.PropsBean.PositionBean positionBean = positionList.get(i);
                float duration = positionBean.getFrame();
                float startX = positionBean.getValue().get(0) / 750 * 2;
                float startY = positionBean.getValue().get(1) / 1334 * 2;
                Log.e("Murat", "[X] " + positionBean.getValue().get(0) + " [Y] " + positionBean.getValue().get(1));
                mRef.move(startX, startY, 0f, duration, delayTime);
                delayTime += duration;
            }
        }
    }

    private void playRotation() {
        List<AnimationBean.PropsBean.AngleBean> rotationList = mAnimationBean.getProps().getAngle();
        if (null != rotationList && rotationList.size() > 0) {
            int size = rotationList.size();
            float delayTime = 0f;
            for (int i = 0; i < size; i++) {
                AnimationBean.PropsBean.AngleBean angleBean = rotationList.get(i);
                float duration = angleBean.getFrame();
                float angle = angleBean.getValue();
                mRef.rotate(angle, 0, 0, 1f, duration, delayTime);
                delayTime += duration;
            }
        }
    }

    private void playScale() {
        List<AnimationBean.PropsBean.ScaleBean> scaleList = mAnimationBean.getProps().getScale();
        if (null != scaleList && scaleList.size() > 0) {
            int size = scaleList.size();
            float delayTime = 0f;
            float scaleOriginX = 1f;
            float scaleOriginY = 1f;
            for (int i = 0; i < size; i++) {
                AnimationBean.PropsBean.ScaleBean scaleBean = scaleList.get(i);
                float duration = scaleBean.getFrame();
                float scaleX = scaleBean.getValue().getX() - scaleOriginX;
                float scaleY = scaleBean.getValue().getY() - scaleOriginY;
                scaleOriginX = scaleBean.getValue().getX();
                scaleOriginY = scaleBean.getValue().getY();
                Log.e("Murat", "[scaleX] " + scaleOriginX + " [scaleY] " + scaleOriginX);
                mRef.scale(scaleX, scaleY, 0f, duration, delayTime);
                delayTime += duration;
            }
        }
    }

    private void playColor() {
        List<AnimationBean.PropsBean.ColorBean> colorList = mAnimationBean.getProps().getColor();
        if (null != colorList && colorList.size() > 0) {
            int size = colorList.size();
            float delayTime = 0f;
            for (int i = 0; i < size; i++) {
                AnimationBean.PropsBean.ColorBean colorBean = colorList.get(i);
                float duration = colorBean.getFrame();
                mRef.fade(colorBean.getValue().getA() / 255f - 1f, duration, delayTime);
                mRef.tint(
                        colorBean.getValue().getR() / 255f - 1f,
                        colorBean.getValue().getG() / 255f - 1f,
                        colorBean.getValue().getB() / 255f - 1f,
                        duration, delayTime);
                Log.e("Murat",
                        " [r] " + colorBean.getValue().getR() / 255f
                                + " [g] " + colorBean.getValue().getG() / 255f
                                + " [b] " + colorBean.getValue().getB() / 255f
                                + " [a] " + colorBean.getValue().getA() / 255f
                );
                delayTime += duration;
            }
        }
    }
}
