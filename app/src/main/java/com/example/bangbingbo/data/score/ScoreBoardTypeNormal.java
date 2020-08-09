package com.example.bangbingbo.data.score;

public class ScoreBoardTypeNormal extends Score{

    private static ScoreBoardTypeNormal scoreBoardTypeNormal;

    int[] twosInColIndex;
    int[] threesInColIndex;
    int[] foursInColIndex;
    int[] fivesInColIndex;

    int[] twosInRowIndex;
    int[] threesInRowIndex;
    int[] foursInRowIndex;
    int[] fivesInRowIndex;

    private ScoreBoardTypeNormal() {
        resetScore();
    }

    public static synchronized ScoreBoardTypeNormal getInstance() {
        if (scoreBoardTypeNormal == null) {
            return scoreBoardTypeNormal = new ScoreBoardTypeNormal();
        }
        return scoreBoardTypeNormal;
    }

    @Override
    public void resetScore() {
        super.resetScore();

        twosInColIndex = new int[4];
        threesInColIndex = new int[3];
        foursInColIndex = new int[2];
        fivesInColIndex = new int[1];

        twosInRowIndex = new int[4];
        threesInRowIndex = new int[3];
        foursInRowIndex = new int[2];
        fivesInRowIndex = new int[1];
    }

    public int[] getTwosInRowIndex() {
        return twosInRowIndex;
    }

    public void setTwosInRowIndex(int[] twosInRowIndex) {
        this.twosInRowIndex = twosInRowIndex;
    }

    public int[] getThreesInRowIndex() {
        return threesInRowIndex;
    }

    public void setThreesInRowIndex(int[] threesInRowIndex) {
        this.threesInRowIndex = threesInRowIndex;
    }

    public int[] getFoursInRowIndex() {
        return foursInRowIndex;
    }

    public void setFoursInRowIndex(int[] foursInRowIndex) {
        this.foursInRowIndex = foursInRowIndex;
    }

    public int[] getFivesInRowIndex() {
        return fivesInRowIndex;
    }

    public void setFivesInRowIndex(int[] fivesInRowIndex) {
        this.fivesInRowIndex = fivesInRowIndex;
    }

    public int[] getTwosInColIndex() {
        return twosInColIndex;
    }

    public void setTwosInColIndex(int[] twosInColIndex) {
        this.twosInColIndex = twosInColIndex;
    }

    public int[] getThreesInColIndex() {
        return threesInColIndex;
    }

    public void setThreesInColIndex(int[] threesInColIndex) {
        this.threesInColIndex = threesInColIndex;
    }

    public int[] getFoursInColIndex() {
        return foursInColIndex;
    }

    public void setFoursInColIndex(int[] foursInColIndex) {
        this.foursInColIndex = foursInColIndex;
    }

    public int[] getFivesInColIndex() {
        return fivesInColIndex;
    }

    public void setFivesInColIndex(int[] fivesInColIndex) {
        this.fivesInColIndex = fivesInColIndex;
    }
}
