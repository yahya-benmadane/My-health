package com.example.test1;

public class MuscleItem {
    private String name;
    private int iconResId;

    public MuscleItem(String name, int iconResId) {
        this.name = name;
        this.iconResId = iconResId;
    }

    public String getName() {
        return name;
    }

    public int getIconResId() {
        return iconResId;
    }
}
