package com.murat.android.opengl.actions;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.murat.android.opengl.Utils;
import com.murat.android.opengl.common.data.Constants;

import java.util.ArrayList;
import java.util.List;

public class Animator {


    private AnimationBean mAnimationBean;
    private long mStartTime;

    public Animator(Context context, int resource) {
        mAnimationBean = new Gson().fromJson(Utils.getJSONStringFromResource(context, resource), AnimationBean.class);
        if (mAnimationBean == null) {
            Log.i("[OpenGL-Error]", "failed to read animation config.");
        } else {
            initTranslateAnimation();
            initRotateAnimation();
            initScaleAnimation();
            initColorAnimation();
        }
    }

    public void start() {
        mStartTime = System.currentTimeMillis();
    }

    private void initTranslateAnimation() {
        List<AnimationBean.PropsBean.PositionBean> positionList = mAnimationBean.getProps().getPosition();
        if (null != positionList && positionList.size() > 0) {
            int size = positionList.size();
            moveStartTime = positionList.get(0).getFrame();
            moveEndTime = positionList.get(size - 1).getFrame();
            for (int i = 0; i < size; i++) {
                AnimationBean.PropsBean.PositionBean positionBean = positionList.get(i);
                float startTime = positionBean.getFrame();
                float endTime = startTime;
                float startX = positionBean.getValue().get(0) / Constants.Designed_Width * 2;
                float startY = positionBean.getValue().get(1) / Constants.Designed_Height * 2 * Constants.Designed_Height / Constants.Designed_Width;
                float endX = startX;
                float endY = startY;
                if (i + 1 < size) {
                    endTime = positionList.get(i + 1).getFrame();
                    endX = positionList.get(i + 1).getValue().get(0) / Constants.Designed_Width * 2;
                    endY = positionList.get(i + 1).getValue().get(1) / Constants.Designed_Height * 2 * Constants.Designed_Height / Constants.Designed_Width;
                }
                mListMove.add(new Translate(startTime, endTime, startX, startY, (endX - startX), (endY - startY)));
            }
        }
    }

    private void initRotateAnimation() {
        List<AnimationBean.PropsBean.AngleBean> rotationList = mAnimationBean.getProps().getAngle();
        if (null != rotationList && rotationList.size() > 0) {
            int size = rotationList.size();
            rotateStartTime = rotationList.get(0).getFrame();
            rotateEndTime = rotationList.get(size - 1).getFrame();
            for (int i = 0; i < size; i++) {
                AnimationBean.PropsBean.AngleBean angleBean = rotationList.get(i);
                float startTime = angleBean.getFrame();
                float endTime = startTime;
                float angle1 = angleBean.getValue();
                float angle2 = angle1;
                if (i + 1 < size) {
                    endTime = rotationList.get(i + 1).getFrame();
                    angle2 = rotationList.get(i + 1).getValue();
                }
                mListAngle.add(new Angle(startTime, endTime, angle1, (angle2 - angle1)));
            }
        }
    }

    private void initScaleAnimation() {
        List<AnimationBean.PropsBean.ScaleBean> scaleList = mAnimationBean.getProps().getScale();
        if (null != scaleList && scaleList.size() > 0) {
            int size = scaleList.size();
            scaleStartTime = scaleList.get(0).getFrame();
            scaleEndTime = scaleList.get(size - 1).getFrame();
            for (int i = 0; i < size; i++) {
                AnimationBean.PropsBean.ScaleBean scaleBean = scaleList.get(i);
                float startTime = scaleBean.getFrame();
                float endTime = startTime;
                float scaleX1 = scaleBean.getValue().getX();
                float scaleY1 = scaleBean.getValue().getY();
                float scaleX2 = scaleX1;
                float scaleY2 = scaleY1;
                if (i + 1 < size) {
                    endTime = scaleList.get(i + 1).getFrame();
                    scaleX2 = scaleList.get(i + 1).getValue().getX();
                    scaleY2 = scaleList.get(i + 1).getValue().getY();
                }
                mListScale.add(new Scale(startTime, endTime, scaleX1, scaleY1, (scaleX2 - scaleX1), (scaleY2 - scaleY1)));
            }
        }
    }

    private void initColorAnimation() {
        List<AnimationBean.PropsBean.ColorBean> colorList = mAnimationBean.getProps().getColor();
        if (null != colorList && colorList.size() > 0) {
            int size = colorList.size();
            colorStartTime = colorList.get(0).getFrame();
            colorEndTime = colorList.get(size - 1).getFrame();
            for (int i = 0; i < size; i++) {
                AnimationBean.PropsBean.ColorBean colorBean = colorList.get(i);
                float startTime = colorBean.getFrame();
                float endTime = startTime;
                float r1 = colorBean.getValue().getR() / 255f;
                float g1 = colorBean.getValue().getG() / 255f;
                float b1 = colorBean.getValue().getB() / 255f;
                float a1 = colorBean.getValue().getA() / 255f;

                float r2 = r1;
                float g2 = g1;
                float b2 = b1;
                float a2 = a1;
                if (i + 1 < size) {
                    endTime = colorList.get(i + 1).getFrame();
                    r2 = colorList.get(i + 1).getValue().getR() / 255f;
                    g2 = colorList.get(i + 1).getValue().getG() / 255f;
                    b2 = colorList.get(i + 1).getValue().getB() / 255f;
                    a2 = colorList.get(i + 1).getValue().getA() / 255f;
                }
                mListTint.add(new Tint(startTime, endTime, r1, g1, b1, (r2 - r1), (g2 - g1), (b2 - b1)));
                mListFade.add(new Fade(startTime, endTime, a1, (a2 - a1)));
            }
        }
    }

    private float moveStartTime;
    private float moveEndTime;
    private float scaleStartTime;
    private float scaleEndTime;
    private float rotateStartTime;
    private float rotateEndTime;
    private float colorStartTime;
    private float colorEndTime;
    private int moveIndex;
    private int scaleIndex;
    private int rotateIndex;
    private int fadeIndex;
    private int tintIndex;
    private ArrayList<Translate> mListMove = new ArrayList<>();
    private ArrayList<Scale> mListScale = new ArrayList<>();
    private ArrayList<Angle> mListAngle = new ArrayList<>();
    private ArrayList<Fade> mListFade = new ArrayList<>();
    private ArrayList<Tint> mListTint = new ArrayList<>();

    public float[] getCurrentTranslate3f() {
        float time = (System.currentTimeMillis() - mStartTime) / 1000f;
        if (mListMove.size() != 0) {
            Translate translate = mListMove.get(moveIndex);
            if (time < moveStartTime || time > moveEndTime) {
                return null;
            }
            if (time > translate.endTime && time < moveEndTime) {
                moveIndex++;
            }
            return mListMove.get(moveIndex).getCurrent(time);
        }
        return null;
    }

    public float[] getCurrentRotation4f() {
        float time = (System.currentTimeMillis() - mStartTime) / 1000f;
        if (mListAngle.size() != 0) {
            Angle angle = mListAngle.get(rotateIndex);
            if (time < rotateStartTime || time > rotateEndTime) {
                return null;
            }
            if (time > angle.endTime && time < rotateEndTime) {
                rotateIndex++;
            }
            return mListAngle.get(rotateIndex).getCurrent(time);
        }
        return null;
    }

    public float[] getCurrentScale3f() {
        float time = (System.currentTimeMillis() - mStartTime) / 1000f;
        if (mListScale.size() != 0) {
            Scale scale = mListScale.get(scaleIndex);
            if (time < scaleStartTime || time > scaleEndTime) {
                return null;
            }
            if (time > scale.endTime && time < scaleEndTime) {
                scaleIndex++;
            }
            return mListScale.get(scaleIndex).getCurrent(time);
        }
        return null;
    }

    public float[] getCurrentTint3f() {
        float time = (System.currentTimeMillis() - mStartTime) / 1000f;
        if (mListTint.size() != 0) {
            Tint tint = mListTint.get(tintIndex);
            if (time < colorStartTime || time > colorEndTime) {
                return null;
            }
            if (time > tint.endTime && time < moveEndTime) {
                tintIndex++;
            }
            return mListTint.get(tintIndex).getCurrent(time);
        }
        return null;
    }

    public float[] getCurrentFade1f() {
        float time = (System.currentTimeMillis() - mStartTime) / 1000f;
        if (mListFade.size() != 0) {
            Fade fade = mListFade.get(fadeIndex);
            if (time < colorStartTime || time > colorEndTime) {
                return null;
            }
            if (time > fade.endTime && time < moveEndTime) {
                fadeIndex++;
            }
            return mListFade.get(fadeIndex).getCurrent(time);
        }
        return null;
    }
}