package com.example.bangbingbo.game.listeners.commandexecutors.ui;

import android.widget.ImageView;

import com.example.bangbingbo.game.GameBoardManager;
import com.example.bangbingbo.game.GameRuleEvaluator;
import com.example.bangbingbo.views.DefaultActivity;

public class FirstClickLegalUICommandExecutor implements UICommandExecutor {

    private static FirstClickLegalUICommandExecutor firstClickLegalUICommandExecutor;

    private FirstClickLegalUICommandExecutor() {
    }

    public static synchronized UICommandExecutor getInstance() {
        if (firstClickLegalUICommandExecutor == null) {
            return firstClickLegalUICommandExecutor = new FirstClickLegalUICommandExecutor();
        }
        return firstClickLegalUICommandExecutor;
    }

    @Override
    public void executeUICommands(DefaultActivity defaultActivity) {
        int piece1Clicked = GameRuleEvaluator.getInstanceForType(defaultActivity.getBoardType()).getPiece1Clicked();
        ImageView piece1ClickedImageView = defaultActivity.getImages().get(piece1Clicked);
        defaultActivity.setFloatingPieceParams(piece1ClickedImageView);
        defaultActivity.getGameAnimation().startClickAnimation(piece1ClickedImageView, defaultActivity.getFloatingPieceView());
    }
}
