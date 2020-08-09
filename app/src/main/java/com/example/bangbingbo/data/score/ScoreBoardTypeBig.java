package com.example.bangbingbo.data.score;

public class ScoreBoardTypeBig extends Score{

    private static ScoreBoardTypeBig scoreBoardTypeBig;

    int[] twosInColIndex;
    int[] threesInColIndex;
    int[] foursInColIndex;
    int[] fivesInColIndex;
    int[] sixesInColIndex;
    int[] sevenInColIndex;

    int[] twosInRowIndex;
    int[] threesInRowIndex;
    int[] foursInRowIndex;
    int[] fivesInRowIndex;
    int[] sixesInRowIndex;
    int[] sevenInRowIndex;


    private ScoreBoardTypeBig() {
        resetScore();
    }

    public static synchronized ScoreBoardTypeBig getInstance() {
        if (scoreBoardTypeBig == null) {
            return scoreBoardTypeBig = new ScoreBoardTypeBig();
        }
        return scoreBoardTypeBig;
    }

    @Override
    public void resetScore() {
        super.resetScore();

        twosInColIndex = new int[6];
        threesInColIndex = new int[5];
        foursInColIndex = new int[4];
        fivesInColIndex = new int[3];
        sixesInColIndex = new int[2];
        sevenInColIndex = new int[1];

        twosInRowIndex = new int[6];
        threesInRowIndex = new int[5];
        foursInRowIndex = new int[4];
        fivesInRowIndex = new int[3];
        sixesInRowIndex = new int[2];
        sevenInRowIndex = new int[1];
    }

    public static ScoreBoardTypeBig getScoreBoardTypeBig() {
        return scoreBoardTypeBig;
    }

    public static void setScoreBoardTypeBig(ScoreBoardTypeBig scoreBoardTypeBig) {
        ScoreBoardTypeBig.scoreBoardTypeBig = scoreBoardTypeBig;
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

    public int[] getSixesInRowIndex() {
        return sixesInRowIndex;
    }

    public void setSixesInRowIndex(int[] sixesInRowIndex) {
        this.sixesInRowIndex = sixesInRowIndex;
    }

    public int[] getSevenInRowIndex() {
        return sevenInRowIndex;
    }

    public void setSevenInRowIndex(int[] sevenInRowIndex) {
        this.sevenInRowIndex = sevenInRowIndex;
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

    public int[] getSixesInColIndex() {
        return sixesInColIndex;
    }

    public void setSixesInColIndex(int[] sixesInColIndex) {
        this.sixesInColIndex = sixesInColIndex;
    }

    public int[] getSevenInColIndex() {
        return sevenInColIndex;
    }

    public void setSevenInColIndex(int[] sevenInColIndex) {
        this.sevenInColIndex = sevenInColIndex;
    }
}
