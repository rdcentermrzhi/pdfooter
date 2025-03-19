package com.jinrong.pdf.comp;

import java.io.Serializable;

public class CharInfo implements Serializable {
    private static final long serialVersionUID = -4740855504718913502L;

    private boolean zhChar;

    private float width;

    private Character value;

    public boolean isZhChar() {
        return this.zhChar;
    }

    public void setZhChar(boolean zhChar) {
        this.zhChar = zhChar;
    }

    public float getWidth() {
        return this.width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public Character getValue() {
        return this.value;
    }

    public void setValue(Character value) {
        this.value = value;
    }
}
