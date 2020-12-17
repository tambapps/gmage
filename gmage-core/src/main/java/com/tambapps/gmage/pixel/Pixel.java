package com.tambapps.gmage.pixel;

import lombok.Data;

@Data
public class Pixel {
    private int r;
    private int g;
    private int b;
    private int a;


    public void set(Number value) {
        // TODO
    }

    public void set(Pixel value) {
        this.r = value.r;
        this.g = value.g;
        this.b = value.b;
        this.a = value.a;
    }
}
