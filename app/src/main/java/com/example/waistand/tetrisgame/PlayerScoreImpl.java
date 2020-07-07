package com.example.waistand.tetrisgame;

import com.example.waistand.tetris.Score;

public class PlayerScoreImpl extends Score {
    private int additionalScore;

    protected void  calculatorScore(int removedLineCount) {
        if (removedLineCount == 0) {
            this.additionalScore = 100;
        }

        int lineScore[] = { 0, 50, 100, 150, 200 };

        for (int i = 0 ; i < removedLineCount ; i++) {
            additionalScore += 100;
        }

        if (additionalScore > 500) {
            additionalScore = 500;
        }

        addScore(removedLineCount * additionalScore + lineScore[removedLineCount]);
    }
    protected void ClearBoard() {
        addScore(100000);
    }
}
