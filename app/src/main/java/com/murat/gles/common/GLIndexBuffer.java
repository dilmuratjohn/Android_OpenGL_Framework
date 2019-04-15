package com.murat.gles.common;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import android.opengl.GLES20;

import static com.murat.gles.common.GLConstants.BYTES_PER_SHORT;


public class GLIndexBuffer {

    private final int bufferId;

    public GLIndexBuffer(short[] vertexData) {
        final int[] buffers = new int[1];
        GLES20.glGenBuffers(buffers.length, buffers, 0);
        if (buffers[0] == 0) {
            throw new RuntimeException("error generate index buffers.");
        }
        bufferId = buffers[0];

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, bufferId);

        ShortBuffer vertexArray = ByteBuffer
                .allocateDirect(vertexData.length * BYTES_PER_SHORT)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer()
                .put(vertexData);

        vertexArray.position(0);

        GLES20.glBufferData(
                GLES20.GL_ELEMENT_ARRAY_BUFFER,
                vertexArray.capacity() * BYTES_PER_SHORT,
                vertexArray,
                GLES20.GL_STATIC_DRAW
        );

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void setVertexAttribPointer(int dataOffset, int attributeLocation, int componentCount, int stride) {
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, bufferId);
        GLES20.glVertexAttribPointer(attributeLocation, componentCount, GLES20.GL_SHORT, false, stride, dataOffset);
        GLES20.glEnableVertexAttribArray(attributeLocation);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public int getBufferId() {
        return bufferId;
    }
}
