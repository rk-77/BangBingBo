package com.example.bangbingbo.game.listeners;

import android.view.View;
import android.widget.ImageView;

import com.example.bangbingbo.game.GamePiece;
import com.example.bangbingbo.game.enums.PieceClickStatus;
import com.example.bangbingbo.game.enums.PieceClickStatusEvaluated;

import java.util.List;

public class BoardClickListener implements View.OnClickListener, GamePieceClickStatusEvaluatedListener {

    private boolean isOriginImageViewClicked;
    private boolean isDestinationImageViewClicked;
    private List<ImageView> images;
    private GamePieceClickedListener gamePieceClickedListener;

    public BoardClickListener(List<ImageView> images, GamePieceClickedListener gamePieceClickedListener) {
        this.gamePieceClickedListener = gamePieceClickedListener;
        this.images = images;
        for (ImageView image : images) {
            image.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        if (gamePieceClickedListener.isBusy())
            return;

        for (int i = 0; i < images.size(); i++) {
            if (!isViewFound(view, i)) continue;
            if (isFirstClick(i)) {
                triggerPieceClickEvent(PieceClickStatus.CLICKED_SOURCE, i);
                break;
            } else if (isSecondClick()) {
                triggerPieceClickEvent(PieceClickStatus.CLICKED_DESTINATION, i);
                break;
            }
        }
    }

    private boolean isSecondClick() {
        return isOriginImageViewClicked;
    }

    synchronized private void triggerPieceClickEvent(PieceClickStatus status, int i) {
        //TODO: set busy
        gamePieceClickedListener.onGamePieceClicked(new GamePiece(status, images.get(i), i));
    }

    private boolean isFirstClick(int i) {
        return !isOriginImageViewClicked && images.get(i).getDrawable() != null;
    }

    private boolean isEmptyDestinationClick(int i) {
        return isOriginImageViewClicked && images.get(i).getDrawable() == null;
    }

    private boolean isViewFound(View view, int i) {
        return view.getId() == images.get(i).getId();
    }

    private void resetImageViewsClicked() {
       isOriginImageViewClicked = false;
       isDestinationImageViewClicked = false;
    }

    @Override
    public void onGamePieceClickedEvaluated(PieceClickStatusEvaluated status) {
        switch (status) {
            case FIRST_CLICK_LEGAL:
                isOriginImageViewClicked = true;
                break;
            case SECOND_CLICK_LEGAL_MOVE:
                isDestinationImageViewClicked = true;
                break;
            case SECOND_CLICK_ILLEGAL_OCCUPIED_PIECE:
                isDestinationImageViewClicked = false;
                break;
            case SECOND_CLICK_FINISHED:
                resetImageViewsClicked();
                break;
        }
        throw new IllegalArgumentException("Ungültiger Status " + status.name());
    }
}
