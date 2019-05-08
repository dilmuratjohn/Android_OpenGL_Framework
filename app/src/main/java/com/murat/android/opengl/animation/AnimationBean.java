package com.murat.android.opengl.animation;

import java.util.List;

public class AnimationBean {
    /**
     * props : {"scale":[{"frame":0,"value":{"__type__":"cc.Vec2","x":1,"y":1}},{"frame":1,"value":{"__type__":"cc.Vec2","x":2,"y":2}},{"frame":1.6833333333333333,"value":{"__type__":"cc.Vec2","x":1.3,"y":1.4}},{"frame":2.35,"value":{"__type__":"cc.Vec2","x":4.4,"y":5.5}}],"angle":[{"frame":0,"value":0},{"frame":1,"value":90},{"frame":1.6833333333333333,"value":45.2},{"frame":2.35,"value":180}],"color":[{"frame":0,"value":{"__type__":"cc.Color","r":221,"g":66,"b":66,"a":255}},{"frame":1,"value":{"__type__":"cc.Color","r":57,"g":154,"b":32,"a":255}},{"frame":1.6833333333333333,"value":{"__type__":"cc.Color","r":41,"g":207,"b":206,"a":255}},{"frame":2.35,"value":{"__type__":"cc.Color","r":230,"g":241,"b":240,"a":255}}],"position":[{"frame":0,"value":[-38,25]},{"frame":1,"value":[200,200]},{"frame":1.6833333333333333,"value":[79.5,23.3]},{"frame":2.35,"value":[33.3,19.5]}]}
     */

    private PropsBean props;

    public PropsBean getProps() {
        return props;
    }

    public void setProps(PropsBean props) {
        this.props = props;
    }

    public static class PropsBean {
        private List<ScaleBean> scale;
        private List<AngleBean> angle;
        private List<ColorBean> color;
        private List<PositionBean> position;

        public List<ScaleBean> getScale() {
            return scale;
        }

        public void setScale(List<ScaleBean> scale) {
            this.scale = scale;
        }

        public List<AngleBean> getAngle() {
            return angle;
        }

        public void setAngle(List<AngleBean> angle) {
            this.angle = angle;
        }

        public List<ColorBean> getColor() {
            return color;
        }

        public void setColor(List<ColorBean> color) {
            this.color = color;
        }

        public List<PositionBean> getPosition() {
            return position;
        }

        public void setPosition(List<PositionBean> position) {
            this.position = position;
        }

        public static class ScaleBean {
            /**
             * frame : 0
             * value : {"__type__":"cc.Vec2","x":1,"y":1}
             */

            private float frame;
            private ValueBean value;

            public float getFrame() {
                return frame;
            }

            public void setFrame(float frame) {
                this.frame = frame;
            }

            public ValueBean getValue() {
                return value;
            }

            public void setValue(ValueBean value) {
                this.value = value;
            }

            public static class ValueBean {
                /**
                 * __type__ : cc.Vec2
                 * x : 1
                 * y : 1
                 */

                private String __type__;
                private float x;
                private float y;

                public String get__type__() {
                    return __type__;
                }

                public void set__type__(String __type__) {
                    this.__type__ = __type__;
                }

                public float getX() {
                    return x;
                }

                public void setX(float x) {
                    this.x = x;
                }

                public float getY() {
                    return y;
                }

                public void setY(float y) {
                    this.y = y;
                }
            }
        }

        public static class AngleBean {
            /**
             * frame : 0
             * value : 0
             */

            private float frame;
            private float value;

            public float getFrame() {
                return frame;
            }

            public void setFrame(float frame) {
                this.frame = frame;
            }

            public float getValue() {
                return value;
            }

            public void setValue(float value) {
                this.value = value;
            }
        }

        public static class ColorBean {
            /**
             * frame : 0
             * value : {"__type__":"cc.Color","r":221,"g":66,"b":66,"a":255}
             */

            private float frame;
            private ValueBeanX value;

            public float getFrame() {
                return frame;
            }

            public void setFrame(float frame) {
                this.frame = frame;
            }

            public ValueBeanX getValue() {
                return value;
            }

            public void setValue(ValueBeanX value) {
                this.value = value;
            }

            public static class ValueBeanX {
                /**
                 * __type__ : cc.Color
                 * r : 221
                 * g : 66
                 * b : 66
                 * a : 255
                 */

                private String __type__;
                private float r;
                private float g;
                private float b;
                private float a;

                public String get__type__() {
                    return __type__;
                }

                public void set__type__(String __type__) {
                    this.__type__ = __type__;
                }

                public float getR() {
                    return r;
                }

                public void setR(float r) {
                    this.r = r;
                }

                public float getG() {
                    return g;
                }

                public void setG(float g) {
                    this.g = g;
                }

                public float getB() {
                    return b;
                }

                public void setB(float b) {
                    this.b = b;
                }

                public float getA() {
                    return a;
                }

                public void setA(float a) {
                    this.a = a;
                }
            }
        }

        public static class PositionBean {
            /**
             * frame : 0
             * value : [-38,25]
             */

            private float frame;
            private List<Float> value;

            public float getFrame() {
                return frame;
            }

            public void setFrame(float frame) {
                this.frame = frame;
            }

            public List<Float> getValue() {
                return value;
            }

            public void setValue(List<Float> value) {
                this.value = value;
            }
        }
    }
}
