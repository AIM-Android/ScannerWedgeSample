package com.advantech.scannerwedgedemo.bean;

import java.io.Serializable;

public class ImageData implements Serializable {

    private static final long serialVersionUID = 1L;

    private Image img;

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public static class Image implements Serializable {

        private static final long serialVersionUID = 1L;

        private String src;
        private int offset;
        private int width = 384;
        private int height = 98;

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }
}