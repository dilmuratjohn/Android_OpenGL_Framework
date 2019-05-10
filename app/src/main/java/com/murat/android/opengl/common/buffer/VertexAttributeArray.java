package com.murat.android.opengl.common.buffer;

import java.util.ArrayList;

public class VertexAttributeArray {
    private ArrayList<VertexAttribute> mElements;
    private int mStride;

    class VertexAttribute {
        int location;
        int dimension;
        int type;
        int size;
        boolean normalized;

        VertexAttribute(int location, int dimension, int type, int size, boolean normalized) {
            this.location = location;
            this.dimension = dimension;
            this.type = type;
            this.size = size;
            this.normalized = normalized;
        }
    }

    public VertexAttributeArray() {
        mElements = new ArrayList<>();
    }

    public void push(int location, int dimension, int type, int size, boolean normalized) {
        mElements.add(new VertexAttribute(location, dimension, type, size, normalized));
        mStride += dimension * size;
    }

    public ArrayList<VertexAttribute> getElements() {
        return mElements;
    }

    public int getStride() {
        return mStride;
    }
}
