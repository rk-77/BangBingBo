package com.example.bangbingbo.game.listeners.commandexecutors.ui;

import android.widget.ImageView;

import com.example.bangbingbo.game.GameRuleEvaluator;
import com.example.bangbingbo.views.DefaultActivity;

import java.util.List;

public class SecondClickLegalMoveUICommandExecutor implements UICommandExecutor {

    private static SecondClickLegalMoveUICommandExecutor secondClickLegalMoveUICommandExecutor;

    private SecondClickLegalMoveUICommandExecutor() {
    }

    public static synchronized UICommandExecutor getInstance() {
        if (secondClickLegalMoveUICommandExecutor == null) {
            return secondClickLegalMoveUICommandExecutor = new SecondClickLegalMoveUICommandExecutor();
        }
        return secondClickLegalMoveUICommandExecutor;
    }

    @Override
    public void executeUICommands(DefaultActivity defaultActivity) {
        List<ImageView> images = defaultActivity.getImages();
        defaultActivity.getGameAnimation().startSlideAnimation(images.get(defaultActivity.getIDClickedPiece1()), images.get(defaultActivity.getIDClickedPiece2()), defaultActivity.getFloatingPieceView());
    }
}