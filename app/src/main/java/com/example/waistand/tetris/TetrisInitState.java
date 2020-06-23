package com.example.waistand.tetris;

public class TetrisInitState extends TetrisGameState {
    /**
     * Default constructor
     */
    public TetrisInitState(Tetris tetris) {
        TetrisLog.d("TetrisInitState()");
        this.tetris = tetris;
    }

}
