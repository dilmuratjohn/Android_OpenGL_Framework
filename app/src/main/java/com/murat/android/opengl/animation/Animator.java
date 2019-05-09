package com.murat.android.opengl.animation;

import android.content.Context;

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
        playRotation();
        playScale();
        playColor();
        playPosition();
    }

    private void playPosition() {
        List<AnimationBean.PropsBean.PositionBean> positionList = mAnimationBean.getProps().getPosition();
        if (null != positionList && positionList.size() > 0) {
            int size = positionList.size();
            float originX = 0f;
            float originY = 0f;
            float durationLast = 0f;
            for (int i = 0; i < size; i++) {
                AnimationBean.PropsBean.PositionBean positionBean = positionList.get(i);
                float duration = positionBean.getFrame();
                float startX = positionBean.getValue().get(0) / 1080 * 2 - originX;
                float startY = positionBean.getValue().get(1) / 1920 * 2 * 1920/1080 - originY;
                originX += startX;
                originY += startY;
                mRef.move(startX, startY, 0f, (duration - durationLast));
                durationLast = duration;
            }
        }
    }

    private void playRotation() {
        List<AnimationBean.PropsBean.AngleBean> rotationList = mAnimationBean.getProps().getAngle();
        if (null != rotationList && rotationList.size() > 0) {
            int size = rotationList.size();
            float durationLast = 0f;
            float rotationLast = 0f;
            for (int i = 0; i < size; i++) {
                AnimationBean.PropsBean.AngleBean angleBean = rotationList.get(i);
                float duration = angleBean.getFrame();
                float angle = angleBean.getValue();
                mRef.rotate(angle - rotationLast, 0, 0, -1f, (duration - durationLast));
                durationLast = duration;
                rotationLast = angle;
            }
        }
    }

    private void playScale() {
        List<AnimationBean.PropsBean.ScaleBean> scaleList = mAnimationBean.getProps().getScale();
        if (null != scaleList && scaleList.size() > 0) {
            int size = scaleList.size();
            float scaleOriginX = 1f;
            float scaleOriginY = 1f;
            float durationLast = 0f;
            for (int i = 0; i < size; i++) {
                AnimationBean.PropsBean.ScaleBean scaleBean = scaleList.get(i);
                float duration = scaleBean.getFrame();
                float scaleX = scaleBean.getValue().getX() - scaleOriginX;
                float scaleY = scaleBean.getValue().getY() - scaleOriginY;
                scaleOriginX = scaleBean.getValue().getX();
                scaleOriginY = scaleBean.getValue().getY();
                mRef.scale(scaleX, scaleY, 0f, (duration - durationLast));
                durationLast = duration;
            }
        }
    }

    private void playColor() {
        List<AnimationBean.PropsBean.ColorBean> colorList = mAnimationBean.getProps().getColor();
        if (null != colorList && colorList.size() > 0) {
            int size = colorList.size();
            float durationLast = 0f;
            float originR = 1f, originG = 1f, originB = 1f, originA = 1f;
            for (int i = 0; i < size; i++) {
                AnimationBean.PropsBean.ColorBean colorBean = colorList.get(i);
                float duration = colorBean.getFrame();
                float r = colorBean.getValue().getR() / 255f - originR;
                float g = colorBean.getValue().getG() / 255f - originG;
                float b = colorBean.getValue().getB() / 255f - originB;
                float a = colorBean.getValue().getA() / 255f - originA;
                mRef.fade(a, (duration - durationLast));
                mRef.tint(r, g, b, (duration - durationLast));
                originR += r;
                originG += g;
                originB += b;
                originA += a;
                durationLast = duration;
            }
        }
    }
}
