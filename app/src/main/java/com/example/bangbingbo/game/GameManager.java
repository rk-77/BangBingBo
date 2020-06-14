package com.example.bangbingbo.game;

public interface GameManager {

    void initFields(int[] occupiedFields, int[] availableFields, int[] nAvailableFields);

    void shuffleBoard();

    void initScore();

    void initStartingPlayer();

    void initClock();

    void setGameStatus();

}
