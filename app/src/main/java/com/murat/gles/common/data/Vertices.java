package com.murat.gles.common.data;

public class Vertices {

    public static final float[] Position4f_Color4f_TextureCoord2f = new float[]{
            -0.5f, -0.5f, 0.0f, 1.0f,      0.0f, 0.0f, 1.0f, 1,0f,      0.0f, 0.0f, // bottom left
             0.5f, -0.5f, 0.0f, 1.0f,      0.0f, 1.0f, 0.0f, 1,0f,      1.0f, 0.0f, // bottom right
             0.5f,  0.5f, 0.0f, 1.0f,      1.0f, 0.0f, 0.0f, 1,0f,      1.0f, 1.0f, // top right
             0.5f,  0.5f, 0.0f, 1.0f,      1.0f, 0.0f, 0.0f, 1,0f,      1.0f, 1.0f, // top right
            -0.5f,  0.5f, 0.0f, 1.0f,      1.0f, 1.0f, 0.0f, 1,0f,      0.0f, 1.0f, // top left
            -0.5f, -0.5f, 0.0f, 1.0f,      0.0f, 0.0f, 1.0f, 1,0f,      0.0f, 0.0f, // bottom left
    };

    public static final float[] Position4f_TexCoord2f = new float[]{
            -0.5f, -0.5f, 0.0f, 1.0f,       0.0f, 0.0f, // bottom left
             0.5f, -0.5f, 0.0f, 1.0f,       1.0f, 0.0f, // bottom right
             0.5f,  0.5f, 0.0f, 1.0f,       1.0f, 1.0f, // top right

             0.5f,  0.5f, 0.0f, 1.0f,       1.0f, 1.0f, // top right
            -0.5f,  0.5f, 0.0f, 1.0f,       0.0f, 1.0f, // top left
            -0.5f, -0.5f, 0.0f, 1.0f,       0.0f, 0.0f, // bottom left
    };
}

