package com.example.bangbingbo.game.listeners.commandexecutors;

import com.example.bangbingbo.game.GameBoardManager;
import com.example.bangbingbo.game.enums.PieceClickStatusEvaluated;
import com.example.bangbingbo.game.listeners.commandexecutors.gamelogic.CommandExecutor;
import com.example.bangbingbo.game.listeners.commandexecutors.gamelogic.SecondClickLegalMoveCommandExecutor;
import com.example.bangbingbo.game.listeners.commandexecutors.gamelogic.SecondClickIllegalMoveCommandExecutor;
import com.example.bangbingbo.game.listeners.commandexecutors.ui.FirstClickLegalUICommandExecutor;
import com.example.bangbingbo.game.listeners.commandexecutors.ui.SecondClickIllegalOccupiedPieceUICommandExecutor;
import com.example.bangbingbo.game.listeners.commandexecutors.ui.SecondClickLegalMoveUICommandExecutor;
import com.example.bangbingbo.game.listeners.commandexecutors.ui.UICommandExecutor;
import com.example.bangbingbo.views.DefaultActivity;

public class CommandExecutorHelper {

    public static void executeUICommands(DefaultActivity defaultActivity, PieceClickStatusEvaluated statusEvaluated) {
        UICommandExecutor uiCommandExecutor = getUICommandExecutorForStatus(statusEvaluated);
        if (uiCommandExecutor != null) {
            uiCommandExecutor.executeUICommands(defaultActivity);
        }
    }

    private static UICommandExecutor getUICommandExecutorForStatus(PieceClickStatusEvaluated statusEvaluated) {
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

    public static void executeCommands(DefaultActivity defaultActivity) {
        CommandExecutor commandExecutorForStatusAndBoardType = getCommandExecutorForStatusAndBoardType(defaultActivity.getViewModel().getPieceClickStatusEvaluated(), defaultActivity.getBoardType());
        if (commandExecutorForStatusAndBoardType != null) {
            commandExecutorForStatusAndBoardType.executeCommands(defaultActivity);
        }
    }

    private static CommandExecutor getCommandExecutorForStatusAndBoardType(PieceClickStatusEvaluated statusEvaluated, GameBoardManager.BoardType boardType) {
        switch (statusEvaluated) {
            case SECOND_CLICK_LEGAL_MOVE:
                return SecondClickLegalMoveCommandExecutor.getInstanceForType(boardType);
            case SECOND_CLICK_ILLEGAL_OCCUPIED_PIECE:
                return SecondClickIllegalMoveCommandExecutor.getInstance();
            default:
                return null;
        }
    }

}
