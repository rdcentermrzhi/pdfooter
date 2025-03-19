package com.jinrong.pdf.result;

import com.jinrong.pdf.comp.CharInfo;
import java.util.ArrayList;
import java.util.List;

public class CharResult {
    private float totalWidth = 0;

    private List<CharInfo> charInfoList = new ArrayList<CharInfo>();

    public float getTotalWidth() {
        return this.totalWidth;
    }

    public void setTotalWidth(float totalWidth) {
        this.totalWidth = totalWidth;
    }

    public List<CharInfo> getCharInfoList() {
        return this.charInfoList;
    }

    public void setCharInfoList(List<CharInfo> charInfoList) {
        this.charInfoList = charInfoList;
    }

    public String toString() {
        return getClass().getSimpleName() +
                ":{totalWidth=" + this.totalWidth +
                ",charInfoList.size=" + this.charInfoList.size() +
                "}";
    }
}
