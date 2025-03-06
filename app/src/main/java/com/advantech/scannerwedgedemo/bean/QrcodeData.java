package com.advantech.scannerwedgedemo.bean;

import java.io.Serializable;

public class QrcodeData implements Serializable  {

    private static final long serialVersionUID = 1L;

    private Qrcode qrCode;

    public Qrcode getQrCode() {
        return qrCode;
    }

    public void setQrCode(Qrcode qrCode) {
        this.qrCode = qrCode;
    }

    public static class Qrcode {

        private static final long serialVersionUID = 1L;

        private String data;
        private int offset;
        private int length = 350;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }
    }
}