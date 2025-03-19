package com.jinrong.pdf.result;

import java.util.List;

public class ExcelResult extends Result {
    private static final long serialVersionUID = -3924959845884273906L;

    private List<String[]> data;

    public List<String[]> getData() {
        return this.data;
    }

    public void setData(List<String[]> data) {
        this.data = data;
    }
}
