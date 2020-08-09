package com.example.bangbingbo.game.listeners.commandexecutors.gamelogic;

import com.example.bangbingbo.data.chain.Chain;
import com.example.bangbingbo.game.GameBoardManager;
import com.example.bangbingbo.game.GameRuleEvaluator;
import com.example.bangbingbo.game.GameStatus;
import com.example.bangbingbo.game.enums.PieceClickStatusEvaluated;
import com.example.bangbingbo.views.DefaultActivity;

public class SecondClickLegalMoveCommandExecutor implements CommandExecutor {

    private static SecondClickLegalMoveCommandExecutor secondClickFinishedCommandExecutor;
    GameBoardManager.BoardType boardType;

    public SecondClickLegalMoveCommandExecutor(GameBoardManager.BoardType boardType) {
        this.boardType = boardType;
    }

    public static synchronized SecondClickLegalMoveCommandExecutor getInstanceForType(GameBoardManager.BoardType boardType) {
        if (secondClickFinishedCommandExecutor == null) {
            return secondClickFinishedCommandExecutor = new SecondClickLegalMoveCommandExecutor(boardType);
        }
        if (secondClickFinishedCommandExecutor.boardType.equals(boardType)) {
            return secondClickFinishedCommandExecutor;
        }
        throw new RuntimeException("Invalid Boardtype");
    }

    @Override
    public void executeCommands(DefaultActivity defaultActivity) {
        updatePiecesLocation(defaultActivity.getIDClickedPiece1(), defaultActivity.getIDClickedPiece2());
        Chain[] chain = GameRuleEvaluator.getInstanceForType(boardType).checkPatterns(defaultActivity.getIDClickedPiece2());
        calcScore(chain);

        defaultActivity.getViewModel().boardClickListener.onGamePieceClickedEvaluated(PieceClickStatusEvaluated.SECOND_CLICK_LEGAL_MOVE_FINISHED);
        //newPiece()
        //resetFloatingPosition()
    }

    private void calcScore(Chain[] chain) {
    }


    private void updatePiecesLocation(int sourceField, int destinationField) {
        GameStatus gameStatus = GameStatus.getInstanceForType(boardType);
        int[] occupiedFields = gameStatus.getOccupiedFields();
        occupiedFields[destinationField] = occupiedFields[sourceField];
        occupiedFields[sourceField] = 0;
        gameStatus.setOccupiedFields(occupiedFields);
    }

}
