package com.example.waistand.tetris;

public class TetrisIdleState extends TetrisGameState {

    /**
     * Default constructor
     */
    public TetrisIdleState(Tetris tetris) {
        TetrisLog.d("TetrisIdleState()");
        this.tetris = tetris;
    }
    public boolean isIdleState() {
       return true;
    }
}