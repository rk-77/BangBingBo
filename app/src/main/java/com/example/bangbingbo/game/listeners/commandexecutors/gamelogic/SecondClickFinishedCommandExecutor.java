package com.example.bangbingbo.game.listeners.commandexecutors.gamelogic;

import com.example.bangbingbo.game.GameBoardManager;
import com.example.bangbingbo.game.GameRuleEvaluator;
import com.example.bangbingbo.game.GameStatus;

public class SecondClickFinishedCommandExecutor implements CommandExecutor {

    GameBoardManager.BoardType boardType;

    public SecondClickFinishedCommandExecutor(GameBoardManager.BoardType boardType) {
        this.boardType = boardType;
    }

    @Override
    public void executeCommands() {
        GameRuleEvaluator gameRuleEvaluator = GameRuleEvaluator.getInstanceForType(boardType);
        updatePiecesLocation(gameRuleEvaluator.getPiece1Clicked(), gameRuleEvaluator.getPiece2Clicked());
    }

    private void updatePiecesLocation(int sourceField, int destinationField) {
        GameStatus gameStatus = GameStatus.getInstanceForType(boardType);
        int[] occupiedFields = gameStatus.getOccupiedFields();
        occupiedFields[destinationField] = occupiedFields[sourceField];
        occupiedFields[sourceField] = 0;
        gameStatus.setOccupiedFields(occupiedFields);
    }
}
