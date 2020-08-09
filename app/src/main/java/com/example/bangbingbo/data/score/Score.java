package com.example.bangbingbo.data.score;

public abstract class Score {

    private int scoreTotal;
    private int scoreRow;
    private int scoreColumn;

    public void resetScore() {
        scoreTotal = 0;
        scoreRow = 0;
        scoreColumn = 0;
    }

    public int getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(int scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    public int getScoreRow() {
        return scoreRow;
    }

    public void setScoreRow(int scoreRow) {
        this.scoreRow = scoreRow;
    }

    public int getScoreColumn() {
        return scoreColumn;
    }

    public void setScoreColumn(int scoreColumn) {
        this.scoreColumn = scoreColumn;
    }
}
