package com.jinrong.pdf.entity;

public class FooterParam {
    private int page;

    private String text;

    private int textRise;

    private int pageWidth;

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTextRise() {
        return this.textRise;
    }

    public void setTextRise(int textRise) {
        this.textRise = textRise;
    }

    public int getPageWidth() {
        return this.pageWidth;
    }

    public void setPageWidth(int pageWidth) {
        this.pageWidth = pageWidth;
    }

    public String toString() {
        return getClass().getSimpleName() +
                ":{page=" + this.page +
                ",text=" + this.text +
                ",textRise=" + this.textRise +
                ",pageWidth=" + this.pageWidth +
                "}";
    }
}
