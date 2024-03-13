package com.example.minesweeperapi.model;

public class MinesweeperGame {
    private String gameId;
    private int width;
    private int height;
    private int minesCount;
    private boolean completed;
    private char[][] field;

    public MinesweeperGame() {
    }

    public MinesweeperGame(String gameId, int width, int height, int minesCount, boolean completed, char[][] field) {
        this.gameId = gameId;
        this.width = width;
        this.height = height;
        this.minesCount = minesCount;
        this.completed = completed;
        this.field = field;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public char[][] getField() {
        return field;
    }

    public void setField(char[][] field) {
        this.field = field;
    }
}
