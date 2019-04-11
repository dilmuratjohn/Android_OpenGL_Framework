package com.murat.gles.util;

public class MathUtils {

    public static class Vec2 {
        public float x, y;

        public Vec2(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public void add(Vec2 source) {
            this.x += source.x;
            this.y += source.y;
        }
    }

    public static class Vec3 {
        public float x, y, z;

        public Vec3(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public void add(Vec3 source) {
            this.x += source.x;
            this.y += source.y;
            this.z += source.z;
        }
    }

    public static class Vec4 {
        public float x, y, z, w;

        public Vec4(float x, float y, float z, float w) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.w = w;
        }

        public void add(Vec4 source) {
            this.x += source.x;
            this.y += source.y;
            this.z += source.z;
            this.w += source.w;
        }

    }

    public static class Mat4 {

        /**
         * @param matrix target matrix
         * @param fov    field of view
         * @param ratio  aspect ratio
         * @param near   near bounds
         * @param far    far bounds
         */
        public static void perspectiveProjection(float[] matrix, float fov, float ratio, float near, float far) {
            //focal length
            final float angleInRadians = (float) (fov * Math.PI / 180.0);
            final float a = (float) (1.0 / Math.tan(angleInRadians / 2.0));

            //projection matrix
            matrix[0] = a / ratio;
            matrix[1] = 0f;
            matrix[2] = 0f;
            matrix[3] = 0f;

            matrix[4] = 0f;
            matrix[5] = a;
            matrix[6] = 0f;
            matrix[7] = 0f;

            matrix[8] = 0f;
            matrix[9] = 0f;
            matrix[10] = -((far + near) / (far - near));
            matrix[11] = -1f;

            matrix[12] = 0f;
            matrix[13] = 0f;
            matrix[14] = -((2 * far * near) / (far - near));
            matrix[15] = 0f;
        }
    }

}
