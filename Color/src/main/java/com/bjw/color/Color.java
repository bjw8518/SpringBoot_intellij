package com.bjw.color;


public class Color {
    int idx;
    String code;
    String created;

    Color() {
    }

    Color(int idx, String code, String created) {
        this.idx = idx;
        this.code = code;
        this.created = created;
    }

    public int getIdx() {
        return this.idx;
    }

    public String getCode() {
        return this.code;
    }

    public String getCreated() {
        return this.created;
    }
}
