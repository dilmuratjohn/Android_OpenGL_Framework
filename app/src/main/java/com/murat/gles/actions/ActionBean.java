package com.murat.gles.actions;

import java.util.List;

public class ActionBean {


    private CurveDataBean curveData;

    public CurveDataBean getCurveData() {
        return curveData;
    }

    public void setCurveData(CurveDataBean curveData) {
        this.curveData = curveData;
    }

    public static class CurveDataBean {
        /**
         * paths : {"star":{"props":{"scaleX":[{"frame":0,"value":0.4},{"frame":0.26666666666666666,"value":1},{"frame":0.36666666666666664,"value":1.1},{"frame":0.4666666666666667,"value":1},{"frame":0.7333333333333333,"value":1},{"frame":0.9333333333333333,"value":0.5}],"scaleY":[{"frame":0,"value":0.4},{"frame":0.26666666666666666,"value":1},{"frame":0.36666666666666664,"value":1.1},{"frame":0.4666666666666667,"value":1},{"frame":0.7333333333333333,"value":1},{"frame":0.9333333333333333,"value":0.5}],"opacity":[{"frame":0,"value":0},{"frame":0.26666666666666666,"value":255},{"frame":1.1666666666666667,"value":255},{"frame":1.2666666666666666,"value":0}],"rotation":[{"frame":0.7333333333333333,"value":0},{"frame":0.9333333333333333,"value":32},{"frame":1.0333333333333334,"value":-8}],"anchorX":[{"frame":0,"value":0.9103}],"anchorY":[{"frame":0,"value":0.0014}]}},"light1":{"props":{"opacity":[{"frame":0.26666666666666666,"value":0},{"frame":0.36666666666666664,"value":170},{"frame":0.4666666666666667,"value":0}],"scaleX":[{"frame":0.26666666666666666,"value":1.22},{"frame":0.36666666666666664,"value":1.42},{"frame":0.4666666666666667,"value":1.62}],"scaleY":[{"frame":0.26666666666666666,"value":1.22},{"frame":0.36666666666666664,"value":1.42},{"frame":0.4666666666666667,"value":1.62}],"position":[{"frame":0.26666666666666666,"value":[-130,179]},{"frame":0.36666666666666664,"value":[-147,204]},{"frame":0.4666666666666667,"value":[-130,180.4]}]}},"particlesystem":{"props":{"opacity":[{"frame":0,"value":0},{"frame":1.0333333333333334,"value":255}]}}}
         */

        private PathsBean paths;

        public PathsBean getPaths() {
            return paths;
        }

        public void setPaths(PathsBean paths) {
            this.paths = paths;
        }

        public static class PathsBean {
            /**
             * star : {"props":{"scaleX":[{"frame":0,"value":0.4},{"frame":0.26666666666666666,"value":1},{"frame":0.36666666666666664,"value":1.1},{"frame":0.4666666666666667,"value":1},{"frame":0.7333333333333333,"value":1},{"frame":0.9333333333333333,"value":0.5}],"scaleY":[{"frame":0,"value":0.4},{"frame":0.26666666666666666,"value":1},{"frame":0.36666666666666664,"value":1.1},{"frame":0.4666666666666667,"value":1},{"frame":0.7333333333333333,"value":1},{"frame":0.9333333333333333,"value":0.5}],"opacity":[{"frame":0,"value":0},{"frame":0.26666666666666666,"value":255},{"frame":1.1666666666666667,"value":255},{"frame":1.2666666666666666,"value":0}],"rotation":[{"frame":0.7333333333333333,"value":0},{"frame":0.9333333333333333,"value":32},{"frame":1.0333333333333334,"value":-8}],"anchorX":[{"frame":0,"value":0.9103}],"anchorY":[{"frame":0,"value":0.0014}]}}
             * light1 : {"props":{"opacity":[{"frame":0.26666666666666666,"value":0},{"frame":0.36666666666666664,"value":170},{"frame":0.4666666666666667,"value":0}],"scaleX":[{"frame":0.26666666666666666,"value":1.22},{"frame":0.36666666666666664,"value":1.42},{"frame":0.4666666666666667,"value":1.62}],"scaleY":[{"frame":0.26666666666666666,"value":1.22},{"frame":0.36666666666666664,"value":1.42},{"frame":0.4666666666666667,"value":1.62}],"position":[{"frame":0.26666666666666666,"value":[-130,179]},{"frame":0.36666666666666664,"value":[-147,204]},{"frame":0.4666666666666667,"value":[-130,180.4]}]}}
             * particlesystem : {"props":{"opacity":[{"frame":0,"value":0},{"frame":1.0333333333333334,"value":255}]}}
             */

            private StarBean star;
            private Light1Bean light1;
            private ParticlesystemBean particlesystem;

            public StarBean getStar() {
                return star;
            }

            public void setStar(StarBean star) {
                this.star = star;
            }

            public Light1Bean getLight1() {
                return light1;
            }

            public void setLight1(Light1Bean light1) {
                this.light1 = light1;
            }

            public ParticlesystemBean getParticlesystem() {
                return particlesystem;
            }

            public void setParticlesystem(ParticlesystemBean particlesystem) {
                this.particlesystem = particlesystem;
            }

            public static class StarBean {
                /**
                 * props : {"scaleX":[{"frame":0,"value":0.4},{"frame":0.26666666666666666,"value":1},{"frame":0.36666666666666664,"value":1.1},{"frame":0.4666666666666667,"value":1},{"frame":0.7333333333333333,"value":1},{"frame":0.9333333333333333,"value":0.5}],"scaleY":[{"frame":0,"value":0.4},{"frame":0.26666666666666666,"value":1},{"frame":0.36666666666666664,"value":1.1},{"frame":0.4666666666666667,"value":1},{"frame":0.7333333333333333,"value":1},{"frame":0.9333333333333333,"value":0.5}],"opacity":[{"frame":0,"value":0},{"frame":0.26666666666666666,"value":255},{"frame":1.1666666666666667,"value":255},{"frame":1.2666666666666666,"value":0}],"rotation":[{"frame":0.7333333333333333,"value":0},{"frame":0.9333333333333333,"value":32},{"frame":1.0333333333333334,"value":-8}],"anchorX":[{"frame":0,"value":0.9103}],"anchorY":[{"frame":0,"value":0.0014}]}
                 */

                private PropsBean props;

                public PropsBean getProps() {
                    return props;
                }

                public void setProps(PropsBean props) {
                    this.props = props;
                }

                public static class PropsBean {
                    private List<ScaleXBean> scaleX;
                    private List<ScaleYBean> scaleY;
                    private List<OpacityBean> opacity;
                    private List<RotationBean> rotation;
                    private List<AnchorXBean> anchorX;
                    private List<AnchorYBean> anchorY;

                    public List<ScaleXBean> getScaleX() {
                        return scaleX;
                    }

                    public void setScaleX(List<ScaleXBean> scaleX) {
                        this.scaleX = scaleX;
                    }

                    public List<ScaleYBean> getScaleY() {
                        return scaleY;
                    }

                    public void setScaleY(List<ScaleYBean> scaleY) {
                        this.scaleY = scaleY;
                    }

                    public List<OpacityBean> getOpacity() {
                        return opacity;
                    }

                    public void setOpacity(List<OpacityBean> opacity) {
                        this.opacity = opacity;
                    }

                    public List<RotationBean> getRotation() {
                        return rotation;
                    }

                    public void setRotation(List<RotationBean> rotation) {
                        this.rotation = rotation;
                    }

                    public List<AnchorXBean> getAnchorX() {
                        return anchorX;
                    }

                    public void setAnchorX(List<AnchorXBean> anchorX) {
                        this.anchorX = anchorX;
                    }

                    public List<AnchorYBean> getAnchorY() {
                        return anchorY;
                    }

                    public void setAnchorY(List<AnchorYBean> anchorY) {
                        this.anchorY = anchorY;
                    }

                    public static class ScaleXBean {
                        /**
                         * frame : 0
                         * value : 0.4
                         */

                        private int frame;
                        private double value;

                        public int getFrame() {
                            return frame;
                        }

                        public void setFrame(int frame) {
                            this.frame = frame;
                        }

                        public double getValue() {
                            return value;
                        }

                        public void setValue(double value) {
                            this.value = value;
                        }
                    }

                    public static class ScaleYBean {
                        /**
                         * frame : 0
                         * value : 0.4
                         */

                        private int frame;
                        private double value;

                        public int getFrame() {
                            return frame;
                        }

                        public void setFrame(int frame) {
                            this.frame = frame;
                        }

                        public double getValue() {
                            return value;
                        }

                        public void setValue(double value) {
                            this.value = value;
                        }
                    }

                    public static class OpacityBean {
                        /**
                         * frame : 0
                         * value : 0
                         */

                        private int frame;
                        private int value;

                        public int getFrame() {
                            return frame;
                        }

                        public void setFrame(int frame) {
                            this.frame = frame;
                        }

                        public int getValue() {
                            return value;
                        }

                        public void setValue(int value) {
                            this.value = value;
                        }
                    }

                    public static class RotationBean {
                        /**
                         * frame : 0.7333333333333333
                         * value : 0
                         */

                        private double frame;
                        private int value;

                        public double getFrame() {
                            return frame;
                        }

                        public void setFrame(double frame) {
                            this.frame = frame;
                        }

                        public int getValue() {
                            return value;
                        }

                        public void setValue(int value) {
                            this.value = value;
                        }
                    }

                    public static class AnchorXBean {
                        /**
                         * frame : 0
                         * value : 0.9103
                         */

                        private int frame;
                        private double value;

                        public int getFrame() {
                            return frame;
                        }

                        public void setFrame(int frame) {
                            this.frame = frame;
                        }

                        public double getValue() {
                            return value;
                        }

                        public void setValue(double value) {
                            this.value = value;
                        }
                    }

                    public static class AnchorYBean {
                        /**
                         * frame : 0
                         * value : 0.0014
                         */

                        private int frame;
                        private double value;

                        public int getFrame() {
                            return frame;
                        }

                        public void setFrame(int frame) {
                            this.frame = frame;
                        }

                        public double getValue() {
                            return value;
                        }

                        public void setValue(double value) {
                            this.value = value;
                        }
                    }
                }
            }

            public static class Light1Bean {
                /**
                 * props : {"opacity":[{"frame":0.26666666666666666,"value":0},{"frame":0.36666666666666664,"value":170},{"frame":0.4666666666666667,"value":0}],"scaleX":[{"frame":0.26666666666666666,"value":1.22},{"frame":0.36666666666666664,"value":1.42},{"frame":0.4666666666666667,"value":1.62}],"scaleY":[{"frame":0.26666666666666666,"value":1.22},{"frame":0.36666666666666664,"value":1.42},{"frame":0.4666666666666667,"value":1.62}],"position":[{"frame":0.26666666666666666,"value":[-130,179]},{"frame":0.36666666666666664,"value":[-147,204]},{"frame":0.4666666666666667,"value":[-130,180.4]}]}
                 */

                private PropsBeanX props;

                public PropsBeanX getProps() {
                    return props;
                }

                public void setProps(PropsBeanX props) {
                    this.props = props;
                }

                public static class PropsBeanX {
                    private List<OpacityBeanX> opacity;
                    private List<ScaleXBeanX> scaleX;
                    private List<ScaleYBeanX> scaleY;
                    private List<PositionBean> position;

                    public List<OpacityBeanX> getOpacity() {
                        return opacity;
                    }

                    public void setOpacity(List<OpacityBeanX> opacity) {
                        this.opacity = opacity;
                    }

                    public List<ScaleXBeanX> getScaleX() {
                        return scaleX;
                    }

                    public void setScaleX(List<ScaleXBeanX> scaleX) {
                        this.scaleX = scaleX;
                    }

                    public List<ScaleYBeanX> getScaleY() {
                        return scaleY;
                    }

                    public void setScaleY(List<ScaleYBeanX> scaleY) {
                        this.scaleY = scaleY;
                    }

                    public List<PositionBean> getPosition() {
                        return position;
                    }

                    public void setPosition(List<PositionBean> position) {
                        this.position = position;
                    }

                    public static class OpacityBeanX {
                        /**
                         * frame : 0.26666666666666666
                         * value : 0
                         */

                        private double frame;
                        private int value;

                        public double getFrame() {
                            return frame;
                        }

                        public void setFrame(double frame) {
                            this.frame = frame;
                        }

                        public int getValue() {
                            return value;
                        }

                        public void setValue(int value) {
                            this.value = value;
                        }
                    }

                    public static class ScaleXBeanX {
                        /**
                         * frame : 0.26666666666666666
                         * value : 1.22
                         */

                        private double frame;
                        private double value;

                        public double getFrame() {
                            return frame;
                        }

                        public void setFrame(double frame) {
                            this.frame = frame;
                        }

                        public double getValue() {
                            return value;
                        }

                        public void setValue(double value) {
                            this.value = value;
                        }
                    }

                    public static class ScaleYBeanX {
                        /**
                         * frame : 0.26666666666666666
                         * value : 1.22
                         */

                        private double frame;
                        private double value;

                        public double getFrame() {
                            return frame;
                        }

                        public void setFrame(double frame) {
                            this.frame = frame;
                        }

                        public double getValue() {
                            return value;
                        }

                        public void setValue(double value) {
                            this.value = value;
                        }
                    }

                    public static class PositionBean {
                        /**
                         * frame : 0.26666666666666666
                         * value : [-130,179]
                         */

                        private double frame;
                        private List<Integer> value;

                        public double getFrame() {
                            return frame;
                        }

                        public void setFrame(double frame) {
                            this.frame = frame;
                        }

                        public List<Integer> getValue() {
                            return value;
                        }

                        public void setValue(List<Integer> value) {
                            this.value = value;
                        }
                    }
                }
            }

            public static class ParticlesystemBean {
                /**
                 * props : {"opacity":[{"frame":0,"value":0},{"frame":1.0333333333333334,"value":255}]}
                 */

                private PropsBeanXX props;

                public PropsBeanXX getProps() {
                    return props;
                }

                public void setProps(PropsBeanXX props) {
                    this.props = props;
                }

                public static class PropsBeanXX {
                    private List<OpacityBeanXX> opacity;

                    public List<OpacityBeanXX> getOpacity() {
                        return opacity;
                    }

                    public void setOpacity(List<OpacityBeanXX> opacity) {
                        this.opacity = opacity;
                    }

                    public static class OpacityBeanXX {
                        /**
                         * frame : 0
                         * value : 0
                         */

                        private int frame;
                        private int value;

                        public int getFrame() {
                            return frame;
                        }

                        public void setFrame(int frame) {
                            this.frame = frame;
                        }

                        public int getValue() {
                            return value;
                        }

                        public void setValue(int value) {
                            this.value = value;
                        }
                    }
                }
            }
        }
    }
}
