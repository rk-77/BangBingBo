package com.example.bangbingbo.game.listeners.commandexecutors.gamelogic;

import com.example.bangbingbo.game.GameBoardManager;
import com.example.bangbingbo.game.GameRuleEvaluator;

public class SecondClickLegalMoveCommandExecutor implements CommandExecutor {

    GameBoardManager.BoardType boardType;

    public SecondClickLegalMoveCommandExecutor(GameBoardManager.BoardType boardType) {
        this.boardType = boardType;
    }

    @Override
    public void executeCommands() {
        GameRuleEvaluator gameRuleEvaluator = GameRuleEvaluator.getInstanceForType(boardType);
        updatePiecesLocation(gameRuleEvaluator.getPiece1Clicked(), gameRuleEvaluator.getPiece2Clicked());
    }

    private void updatePiecesLocation(int sourceField, int destinationField) {

    }
}
