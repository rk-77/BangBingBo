package com.example.bangbingbo.game.listeners.commandexecutors.ui;

import android.widget.ImageView;

import com.example.bangbingbo.game.GameRuleEvaluator;
import com.example.bangbingbo.views.DefaultActivity;


public class SecondClickIllegalOccupiedPieceUICommandExecutor implements UICommandExecutor {

    private static SecondClickIllegalOccupiedPieceUICommandExecutor secondClickIllegalOccupiedPieceUICommandExecutor;

    private SecondClickIllegalOccupiedPieceUICommandExecutor() {
    }

    public static synchronized UICommandExecutor getInstance() {
        if (secondClickIllegalOccupiedPieceUICommandExecutor == null) {
            return secondClickIllegalOccupiedPieceUICommandExecutor = new SecondClickIllegalOccupiedPieceUICommandExecutor();
        }
        return secondClickIllegalOccupiedPieceUICommandExecutor;
    }

    @Override
    public void executeUICommands(DefaultActivity defaultActivity) {
        ImageView piece2ClickedImageView = defaultActivity.getImages().get(defaultActivity.getIDClickedPiece2());
        defaultActivity.setFloatingPieceParams(piece2ClickedImageView);
    }
}