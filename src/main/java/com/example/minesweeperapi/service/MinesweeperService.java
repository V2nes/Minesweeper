package com.example.minesweeperapi.service;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class MinesweeperService {
    private int[][] board;
    private boolean[][] opened;
    private int width;
    private int height;
    private int minesCount;
    private boolean gameOver;

    public void createNewGame(int width, int height, int minesCount) {
        if (width <= 0 || height <= 0 || minesCount <= 0 || minesCount >= width * height) {
            throw new IllegalArgumentException("Invalid game parameters");
        }

        this.width = width;
        this.height = height;
        this.minesCount = minesCount;
        board = new int[height][width];
        opened = new boolean[height][width];

        // Place mines
        Random random = new Random();
        int minesToPlace = minesCount;
        while (minesToPlace > 0) {
            int row = random.nextInt(height);
            int col = random.nextInt(width);
            if (board[row][col] != -1) {
                board[row][col] = -1;
                minesToPlace--;
            }
        }

        // Fill in numbers
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (board[row][col] != -1) {
                    int count = 0;
                    for (int r = -1; r <= 1; r++) {
                        for (int c = -1; c <= 1; c++) {
                            if (row + r >= 0 && row + r < height && col + c >= 0 && col + c < width) {
                                if (board[row + r][col + c] == -1) {
                                    count++;
                                }
                            }
                        }
                    }
                    board[row][col] = count;
                }
            }
        }
        gameOver = false;
    }

    public void processMove(int row, int col) {
        if (!gameOver && isValidCell(row, col) && !opened[row][col]) {
            opened[row][col] = true;
            if (board[row][col] == -1) {
                gameOver = true;
                // Handle game over logic
            } else if (board[row][col] == 0) {
                openAdjacentCells(row, col);
            }
            // Check for game win
            if (isGameWon()) {
                gameOver = true;
                // Handle game win logic
            }
        }
    }

    public String getGameId() {
        return UUID.randomUUID().toString();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMinesCount() {
        return minesCount;
    }

    public boolean isGameCompleted() {
        return gameOver;
    }

    public String[][] getField() {
        String[][] visibleField = new String[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (opened[i][j]) {
                    if (board[i][j] == -1) {
                        visibleField[i][j] = "X";  // Ячейка с миной
                    } else if (board[i][j] == 0) {
                        visibleField[i][j] = " ";  // Пустая ячейка
                    } else {
                        visibleField[i][j] = String.valueOf(board[i][j]);  // Ячейка с цифрой
                    }
                } else {
                    visibleField[i][j] = " ";  // Неоткрытая ячейка
                }
            }
        }
        return visibleField;
    }

    private void openAdjacentCells(int row, int col) {
        // Recursive function to open adjacent cells with 0 value
        for (int r = -1; r <= 1; r++) {
            for (int c = -1; c <= 1; c++) {
                if (row + r >= 0 && row + r < height && col + c >= 0 && col + c < width) {
                    if (!opened[row + r][col + c]) {
                        opened[row + r][col + c] = true;
                        if (board[row + r][col + c] == 0) {
                            openAdjacentCells(row + r, col + c);
                        }
                    }
                }
            }
        }
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }

    private boolean isGameWon() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (!opened[i][j] && board[i][j] != -1) {
                    return false;
                }
            }
        }
        return true;
    }
}

