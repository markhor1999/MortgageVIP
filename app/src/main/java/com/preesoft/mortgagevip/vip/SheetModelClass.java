package com.preesoft.mortgagevip.vip;

import android.graphics.drawable.Drawable;

public class SheetModelClass {
    private String name;
    private int resId;

    public SheetModelClass() {
    }

    public SheetModelClass(String name, int resId) {
        this.name = name;
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
