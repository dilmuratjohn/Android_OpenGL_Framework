package com.murat.android.opengl.common.buffer;

import java.util.ArrayList;

public class VertexBufferLayout {
    private ArrayList<VertexBufferElement> mElements;
    private int mStride;

    class VertexBufferElement {
        int location;
        int dimension;
        int type;
        int size;
        boolean normalized;

        VertexBufferElement(int location, int dimension, int type, int size, boolean normalized) {
            this.location = location;
            this.dimension = dimension;
            this.type = type;
            this.size = size;
            this.normalized = normalized;
        }
    }

    public VertexBufferLayout() {
        mElements = new ArrayList<>();
    }

    public void push(int location, int dimension, int type, int size, boolean normalized) {
        mElements.add(new VertexBufferElement(location, dimension, type, size, normalized));
        mStride += dimension * size;
    }

    public ArrayList<VertexBufferElement> getElements() {
        return mElements;
    }

    public int getStride() {
        return mStride;
    }
}
