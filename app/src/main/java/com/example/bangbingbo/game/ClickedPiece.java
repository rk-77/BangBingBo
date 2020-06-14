package com.example.bangbingbo.game;

import android.widget.ImageView;

public class ClickedPiece {
    ImageView clickedImg;
    PieceClickStatus status;
    int clickIndex;

    public ClickedPiece(PieceClickStatus status, ImageView clickedImg, int clickIndex) {
        this.status = status;
        this.clickedImg = clickedImg;
        this.clickIndex = clickIndex;
    }
}

enum PieceClickStatus {CLICKED_SOURCE, CLICKED_DESTINATION}

enum PieceClickStatusEvaluated {
    FIRST_CLICK_ILLEGAL_EMPTY_POSITION, FIRST_CLICK_LEGAL,
    SECOND_CLICK_LEGAL_MOVE, SECOND_CLICK_SAME_PIECE_TWICE,
    SECOND_CLICK_ILLEGAL_DIAGONAL_MOVE_NON_OCCUPIED_POSITION,
    SECOND_CLICK_ILLEGAL_DIAGONAL_MOVE_OCCUPIED_POSITION,
    SECOND_CLICK_ILLEGAL_ORTHOGONAL_MOVE_END_OCCUPIED,
    SECOND_CLICK_ILLEGAL_ORTHOGONAL_MOVE_INBETWEEN_OCCUPIED
}

