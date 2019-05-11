package com.murat.android.opengl.actions;

import java.util.List;

public class AnimationBean {

    private PropsBean props;

    public PropsBean getProps() {
        return props;
    }

    public static class PropsBean {
        private List<ScaleBean> scale;
        private List<AngleBean> angle;
        private List<ColorBean> color;
        private List<PositionBean> position;

        public List<ScaleBean> getScale() {
            return scale;
        }

        public List<AngleBean> getAngle() {
            return angle;
        }

        public List<ColorBean> getColor() {
            return color;
        }

        public List<PositionBean> getPosition() {
            return position;
        }

        public static class ScaleBean {

            private float frame;
            private ValueBean value;

            public float getFrame() {
                return frame;
            }

            public ValueBean getValue() {
                return value;
            }

            public static class ValueBean {

                private float x;
                private float y;

                public float getX() {
                    return x;
                }

                public float getY() {
                    return y;
                }

            }
        }

        public static class AngleBean {

            private float frame;
            private float value;

            public float getFrame() {
                return frame;
            }

            public float getValue() {
                return value;
            }

        }

        public static class ColorBean {

            private float frame;
            private ValueBeanX value;

            public float getFrame() {
                return frame;
            }

            public ValueBeanX getValue() {
                return value;
            }

            public static class ValueBeanX {

                private float r;
                private float g;
                private float b;
                private float a;

                public float getR() {
                    return r;
                }

                public float getG() {
                    return g;
                }

                public float getB() {
                    return b;
                }

                public float getA() {
                    return a;
                }
            }
        }

        public static class PositionBean {
            private float frame;
            private List<Float> value;

            public float getFrame() {
                return frame;
            }

            public List<Float> getValue() {
                return value;
            }
        }
    }
}
