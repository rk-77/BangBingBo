package com.example.bangbingbo.game;

import android.widget.ImageView;

import com.example.bangbingbo.game.enums.PieceClickStatus;

public class GamePiece {
    ImageView clickedImg;
    PieceClickStatus status;
    int clickIndex;

    public GamePiece(PieceClickStatus status, ImageView clickedImg, int clickIndex) {
        this.status = status;
        this.clickedImg = clickedImg;
        this.clickIndex = clickIndex;
    }
}

