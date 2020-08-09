package com.example.bangbingbo.game.listeners;

import com.example.bangbingbo.game.GamePiece;

public interface GamePieceClickedListener {

    void onGamePieceClicked(GamePiece piece);

    boolean isBusy();

    public void setBusy(boolean busy);

}
