package com.advantech.scannerwedgedemo.bean;

import java.io.Serializable;

public class TextData implements Serializable {

    private static final long serialVersionUID = 1L;

    private TextTitle txt;

    public TextTitle getTxt() {
        return txt;
    }

    public void setTxt(TextTitle txt) {
        this.txt = txt;
    }

    public static class TextTitle implements Serializable {

        private static final long serialVersionUID = 1L;

        private String data;
        private int align;
        private int font;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public int getAlign() {
            return align;
        }

        public void setAlign(int align) {
            this.align = align;
        }

        public int getFont() {
            return font;
        }

        public void setFont(int font) {
            this.font = font;
        }
    }
}