package com.example.bangbingbo.game;

import android.view.View;
import android.widget.ImageView;

import java.util.List;

public class BoardClickListener implements View.OnClickListener, GamePieceClickStatusEvaluatedListener {

    private boolean isPlayersTurn = false;
    private boolean isOriginImageViewClicked, isDestinationImageViewClicked;
    private List<ImageView> images;
    private boolean isBlockedByAnimation;
    private GamePieceClickedListener gamePieceClickedListener;

    public BoardClickListener(List<ImageView> images, GamePieceClickedListener gamePieceClickedListener) {
        this.isPlayersTurn = false;
        this.isBlockedByAnimation = false;
        this.gamePieceClickedListener = gamePieceClickedListener;
        this.images = images;
        for (ImageView image : images) {
            image.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        if (isBlockedByAnimation)
            return;
        if (!isPlayersTurn) {
            return;
        }

        for (int i = 0; i < images.size(); i++) {
            if (!isViewFound(view, i)) continue;
            if (isFirstClick(i)) {
                isOriginImageViewClicked = true;
                triggerPieceClickEvent(PieceClickStatus.CLICKED_SOURCE, i);
                break;
            } else if (isSecondClick()) {
                isDestinationImageViewClicked = true;
                triggerPieceClickEvent(PieceClickStatus.CLICKED_DESTINATION, i);
                break;
            }
        }
    }

    private boolean isSecondClick() {
        return isOriginImageViewClicked;
    }

    synchronized private void triggerPieceClickEvent(PieceClickStatus status, int i) {
        gamePieceClickedListener.onGamePieceClicked(new ClickedPiece(status, images.get(i), i));
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

    @Override
    public void onGamePieceClickedEvaluated(PieceClickStatusEvaluated status) {
        //TODO: reset views/flags
    }
}
