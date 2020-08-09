package com.example.bangbingbo.viewmodels;

import com.example.bangbingbo.game.GamePiece;
import com.example.bangbingbo.game.GameRuleEvaluator;
import com.example.bangbingbo.game.enums.PieceClickStatusEvaluated;
import com.example.bangbingbo.game.listeners.BoardClickListener;
import com.example.bangbingbo.game.listeners.GamePieceClickStatusEvaluatedListener;
import com.example.bangbingbo.game.listeners.GamePieceClickedListener;
import com.example.bangbingbo.game.listeners.commandexecutors.CommandExecutorHelper;
import com.example.bangbingbo.views.DefaultActivity;

import static com.example.bangbingbo.game.enums.PieceClickStatusEvaluated.FIRST_CLICK_LEGAL;
import static com.example.bangbingbo.game.enums.PieceClickStatusEvaluated.SECOND_CLICK_ILLEGAL_OCCUPIED_PIECE;
import static com.example.bangbingbo.game.enums.PieceClickStatusEvaluated.SECOND_CLICK_LEGAL_MOVE;

public class DefaultActivityViewModel implements GamePieceClickedListener {

    private boolean isBusy;
    private GameRuleEvaluator gameRuleEvaluator;
    private PieceClickStatusEvaluated pieceClickStatusEvaluated;
    private DefaultActivity defaultActivity;
    public GamePieceClickStatusEvaluatedListener boardClickListener;

    public DefaultActivityViewModel(DefaultActivity defaultActivity) {
        gameRuleEvaluator = GameRuleEvaluator.getInstanceForType(defaultActivity.getBoardType());
        boardClickListener = new BoardClickListener(defaultActivity.getImages(), this);
        this.defaultActivity = defaultActivity;
    }

    @Override
    public void onGamePieceClicked(GamePiece piece) {
        pieceClickStatusEvaluated = gameRuleEvaluator.EvaluateClickStatus(piece);
        boardClickListener.onGamePieceClickedEvaluated(pieceClickStatusEvaluated);
        if (isStartingAnimation(pieceClickStatusEvaluated)) {
            defaultActivity.onGamePieceClickedEvaluated(pieceClickStatusEvaluated); //command execution after animation finished
        } else {
            executeCommands();
        }
    }

    private boolean isStartingAnimation(PieceClickStatusEvaluated pieceClickStatusEvaluated) {
        return (pieceClickStatusEvaluated.equals(FIRST_CLICK_LEGAL) || pieceClickStatusEvaluated.equals(SECOND_CLICK_LEGAL_MOVE) || pieceClickStatusEvaluated.equals(SECOND_CLICK_ILLEGAL_OCCUPIED_PIECE));
    }

    public GameRuleEvaluator getGameRuleEvaluator() {
        return gameRuleEvaluator;
    }

    @Override
    public boolean isBusy() {
        return isBusy;
    }

    @Override
    public void setBusy(boolean busy) {
        isBusy = busy;
    }


    public PieceClickStatusEvaluated getPieceClickStatusEvaluated() {
        return pieceClickStatusEvaluated;
    }

    public void executeCommands() {
        CommandExecutorHelper.executeCommands(defaultActivity);
        setBusy(false);
    }

}
