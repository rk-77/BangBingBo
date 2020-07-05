package com.example.bangbingbo.game.listeners.commandexecutors;

import com.example.bangbingbo.game.enums.PieceClickStatusEvaluated;
import com.example.bangbingbo.game.listeners.commandexecutors.ui.FirstClickLegalUICommandExecutor;
import com.example.bangbingbo.game.listeners.commandexecutors.ui.SecondClickIllegalOccupiedPieceUICommandExecutor;
import com.example.bangbingbo.game.listeners.commandexecutors.ui.SecondClickLegalMoveUICommandExecutor;
import com.example.bangbingbo.game.listeners.commandexecutors.ui.UICommandExecutor;
import com.example.bangbingbo.views.DefaultActivity;

public class CommandExecutorHelper {

    public static void executeUICommands(DefaultActivity defaultActivity, PieceClickStatusEvaluated statusEvaluated) {
        UICommandExecutor uiCommandExecutor = getUICommandExecutorForStatus(defaultActivity, statusEvaluated);
        if (uiCommandExecutor != null) {
            uiCommandExecutor.executeUICommands(defaultActivity);
        }
    }

    private static UICommandExecutor getUICommandExecutorForStatus(DefaultActivity defaultActivity, PieceClickStatusEvaluated statusEvaluated) {
        switch (statusEvaluated) {
            case FIRST_CLICK_LEGAL:
                return FirstClickLegalUICommandExecutor.getInstance();
            case SECOND_CLICK_LEGAL_MOVE:
                return SecondClickLegalMoveUICommandExecutor.getInstance();
            case SECOND_CLICK_ILLEGAL_OCCUPIED_PIECE:
                return SecondClickIllegalOccupiedPieceUICommandExecutor.getInstance();
            default:
                return null;
        }
    }

}
