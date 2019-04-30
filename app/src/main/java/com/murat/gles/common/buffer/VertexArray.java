package com.murat.gles.common.buffer;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import android.opengl.GLES20;

import static com.murat.gles.common.data.Constants.BYTES_PER_FLOAT;

public class VertexArray {

    private final FloatBuffer mFloatBuffer;

    public VertexArray(float[] vertices) {
        mFloatBuffer = ByteBuffer
                .allocateDirect(vertices.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertices);
        mFloatBuffer.position(0);
    }

    public void setVertexAttributePointer(final VertexBufferLayout layout) {
        ArrayList<VertexBufferLayout.VertexBufferElement> elements = layout.getElements();
        int offset = 0;
        for (VertexBufferLayout.VertexBufferElement element : elements) {
            mFloatBuffer.position(offset);
            setVertexAttributePointer(element.location, element.dimension, element.type, element.normalized, layout.getStride(), mFloatBuffer);
            offset += element.dimension;
        }
        mFloatBuffer.position(0);
    }

    private void setVertexAttributePointer(int location, int count, int type, boolean normalized, int stride, Buffer buffer) {
        GLES20.glVertexAttribPointer(location, count, type, normalized, stride, buffer);
        GLES20.glEnableVertexAttribArray(location);
    }

    public void updateVertex(float[] vertex, int offset, int length) {
        mFloatBuffer.position(offset);
        mFloatBuffer.put(vertex, offset, length);
        mFloatBuffer.position(0);
    }

}
