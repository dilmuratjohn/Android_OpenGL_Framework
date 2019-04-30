package com.murat.gles.common.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import android.opengl.GLES20;
import android.util.Log;

import com.murat.gles.common.data.Constants;

public class IndexBuffer {

    private static final String TAG_ERROR = "[OpenGL Error] -> ";

    private final int[] mBufferId = new int[1];

    public IndexBuffer(short[] indices) {
        create();
        bind();
        buffer(indices);
        unbind();
    }

    private void create() {
        GLES20.glGenBuffers(mBufferId.length, mBufferId, 0);
        if (mBufferId[0] == 0) {
            Log.e(TAG_ERROR, "error generating texture.");
        }
    }

    private void buffer(short[] indices) {
        ShortBuffer buffer = ByteBuffer
                .allocateDirect(indices.length * Constants.BYTES_PER_SHORT)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer()
                .put(indices);

        buffer.position(0);

        GLES20.glBufferData(
                GLES20.GL_ELEMENT_ARRAY_BUFFER,
                buffer.capacity() * Constants.BYTES_PER_SHORT,
                buffer,
                GLES20.GL_STATIC_DRAW
        );
    }

    public void bind() {
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mBufferId[0]);
    }

    public void unbind() {
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void delete() {
    }

}
