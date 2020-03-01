package com.example.bangbingbo.game;

import android.view.View;
import android.widget.ImageView;

import java.util.List;

public class BoardClickListener implements View.OnClickListener {

    private boolean isPlayersTurn = false;
    private boolean isOriginImageViewClicked, isDestinationImageViewClicked;
    private List<ImageView> images;

    public BoardClickListener(boolean isPlayersTurn, List<ImageView> images) {
        this.isPlayersTurn = isPlayersTurn;
        this.images = images;
        for (ImageView image : images) {
            image.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {

        if(!isPlayersTurn) {
            return;
        }

        for (int i = 0; i < images.size(); i++) {

        }
    }
}
