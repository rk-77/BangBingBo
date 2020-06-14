package com.example.bangbingbo.game;

import com.example.bangbingbo.utils.Constants;

public class GameStatus {

    private final int boardSize;
    private final int boardLength;

    private int[] occupiedField;
    private int[] avail;
    private int numberOFPiecesInSack;

    private int score;
    private int targetScore;
    private int time;
    private int level;
    private int totalScore;

    GameBoardManager.BoardType boardType;

    private static GameStatus gameStatus;

    private GameStatus(GameBoardManager.BoardType boardType) {
        this.boardType = boardType;
        boardLength = boardType.getBoardLength();
        boardSize = boardType.getBoardSize();
        occupiedField = new int[boardSize];
        avail = new int[boardSize];
        numberOFPiecesInSack = boardSize;

        score = Constants.INITIAL_SCORE;
        targetScore = 0;
        time = Constants.INITIAL_TIME_REMAINING;
        level = Constants.INITIAL_LEVEL;
        totalScore = 0;
    }

    public static synchronized GameStatus getInstanceForType(GameBoardManager.BoardType boardType) {
        if (gameStatus == null) {
           return gameStatus = new GameStatus(boardType);
        }
        else {
          if (gameStatus.boardType.equals(boardType) )
              return gameStatus;
        }
        throw new RuntimeException("Invalid Boardtype");
    }

    public int[] getOccupiedField() {
        return occupiedField;
    }

    public void setOccupiedField(int[] occupiedField) {
        this.occupiedField = occupiedField;
    }

    public int[] getAvail() {
        return avail;
    }

    public void setAvail(int[] avail) {
        this.avail = avail;
    }

    public int getNumberOFPiecesInSack() {
        return numberOFPiecesInSack;
    }

    public void setNumberOFPiecesInSack(int numberOFPiecesInSack) {
        this.numberOFPiecesInSack = numberOFPiecesInSack;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTargetScore() {
        return targetScore;
    }

    public void setTargetScore(int targetScore) {
        this.targetScore = targetScore;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int getBoardLength() {
        return boardLength;
    }
}
