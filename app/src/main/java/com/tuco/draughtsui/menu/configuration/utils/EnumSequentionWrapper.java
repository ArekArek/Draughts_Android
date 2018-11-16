package com.tuco.draughtsui.menu.configuration.utils;

public class EnumSequentionWrapper {
    private Enum enumValue;
    private final Enum[] vals;

    public EnumSequentionWrapper(Enum defaultEnumValue) {
        this.vals = defaultEnumValue.getClass().getEnumConstants();
        this.enumValue = defaultEnumValue;
    }

    public void next() {
        enumValue = vals[(enumValue.ordinal() + 1) % vals.length];
    }

    public Enum getValue() {
        return enumValue;
    }

    @Override
    public String toString() {
        return enumValue.toString();
    }
}
