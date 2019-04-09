package com.murat.gles.util;

public class MathUtils {

    public static class Vector {
        public final float x, y, z;

        public Vector(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public float length() {
            return (float) java.lang.Math.sqrt((x * x) + (y * y) + (z * z));
        }

        public Vector crossProduct(Vector other) {
            return new Vector((y * other.z) - (z * other.y), (z * other.x) - (x * other.z), (x * other.y) - (y * other.x));
        }

        public float dotProduct(Vector other) {
            return x * other.x + y * other.y + z * other.z;
        }

        public Vector scale(float f) {
            return new Vector(x * f, y * f, z * f);
        }

        public Vector add(Vector other) {
            return new Vector(x + other.x, y + other.y, z + other.z);
        }
    }

    public static class Matrix {

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
