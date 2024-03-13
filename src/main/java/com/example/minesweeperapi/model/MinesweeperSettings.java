package com.example.minesweeperapi.model;

public class MinesweeperSettings {
    private int width;
    private int height;
    private int minesCount;

    public MinesweeperSettings() {
    }

    public MinesweeperSettings(int width, int height, int minesCount) {
        this.width = width;
        this.height = height;
        this.minesCount = minesCount;
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

    public int getMinesCount() {
        return minesCount;
    }

    public void setMinesCount(int minesCount) {
        this.minesCount = minesCount;
    }

}
