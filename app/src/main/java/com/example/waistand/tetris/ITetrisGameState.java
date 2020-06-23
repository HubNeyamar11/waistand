package com.example.waistand.tetris;

public interface ITetrisGameState {
    void addRemoveLineCount(int line);
    void ClearBoard();
    void update();
    void updateHighScore();
}
