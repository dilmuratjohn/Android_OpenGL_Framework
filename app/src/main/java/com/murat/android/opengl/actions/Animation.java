package com.murat.android.opengl.actions;


class Translate {

    final float startTime, endTime, originX, deltaX, originY, deltaY;

    Translate(float startTime, float endTime, float originX, float originY, float deltaX, float deltaY) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.originX = originX;
        this.originY = originY;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    float[] getCurrent(float now) {
        return new float[]{
                this.originX + this.deltaX * (now - this.startTime) / (this.endTime - this.startTime),
                this.originY + this.deltaY * (now - this.startTime) / (this.endTime - this.startTime),
                0
        };
    }
}

class Angle {

    final float startTime, endTime, originAngle, deltaAngle;

    Angle(float startTime, float endTime, float originAngle, float deltaAngle) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.originAngle = originAngle;
        this.deltaAngle = deltaAngle;
    }

    float[] getCurrent(float time) {
        return new float[]{
                this.originAngle + this.deltaAngle * (time - this.startTime) / (this.endTime - this.startTime),
                0,
                0,
                -1f
        };
    }
}

class Scale {


    final float startTime, endTime, originX, deltaX, originY, deltaY;

    Scale(float startTime, float endTime, float originX, float originY, float deltaX, float deltaY) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.originX = originX;
        this.originY = originY;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    float[] getCurrent(float now) {
        return new float[]{
                this.originX + this.deltaX * (now - this.startTime) / (this.endTime - this.startTime),
                this.originY + this.deltaY * (now - this.startTime) / (this.endTime - this.startTime),
                0
        };
    }
}

class Tint {

    final float startTime, endTime, originRed, originGreed, originBlue, deltaRed, deltaGreen, deltaBlue;

    Tint(float startTime, float endTime, float originRed, float originGreed, float originBlue, float deltaRed, float deltaGreen, float deltaBlue) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.originRed = originRed;
        this.originGreed = originGreed;
        this.originBlue = originBlue;
        this.deltaRed = deltaRed;
        this.deltaGreen = deltaGreen;
        this.deltaBlue = deltaBlue;
    }

    float[] getCurrent(float time) {
        return new float[]{
                this.originRed + deltaRed * (time - this.startTime) / (this.endTime - this.startTime),
                this.originGreed + deltaGreen * (time - this.startTime) / (this.endTime - this.startTime),
                this.originBlue + deltaBlue * (time - this.startTime) / (this.endTime - this.startTime),
        };
    }
}

class Fade {

    final float startTime, endTime, originAlpha, deltaAlpha;

    Fade(float startTime, float endTime, float originAlpha, float deltaAlpha) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.originAlpha = originAlpha;
        this.deltaAlpha = deltaAlpha;
    }

    float[] getCurrent(float time) {
        return new float[]{
                this.originAlpha + this.deltaAlpha * (time - this.startTime) / (this.endTime - this.startTime)
        };
    }
}

