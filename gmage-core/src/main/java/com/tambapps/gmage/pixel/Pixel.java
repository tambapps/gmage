package com.tambapps.gmage.pixel;

import lombok.Data;

@Data
public class Pixel {
    private int r;
    private int g;
    private int b;
    private int a = 0xff;


    public void set(Number value) {
        // TODO
    }

    public void setRGB(Number red, Number green, Number blue) {
        this.r = red.intValue();
        this.g = green.intValue();
        this.b = blue.intValue();
    }

    public void setRGBA(Number red, Number green, Number blue, Number alpha) {
        this.r = red.intValue();
        this.g = green.intValue();
        this.b = blue.intValue();
        this.a = alpha.intValue();
    }

    public void set(Pixel value) {
        this.r = value.r;
        this.g = value.g;
        this.b = value.b;
        this.a = value.a;
    }
}
