package com.example.bangbingbo.viewmodels;

import com.example.bangbingbo.game.listeners.GamePieceClickStatusEvaluatedListener;
import com.example.bangbingbo.game.listeners.BoardClickListener;
import com.example.bangbingbo.game.GamePiece;
import com.example.bangbingbo.game.listeners.GamePieceClickedListener;
import com.example.bangbingbo.game.GameRuleEvaluator;
import com.example.bangbingbo.game.enums.PieceClickStatusEvaluated;
import com.example.bangbingbo.views.DefaultActivity;

public class DefaultActivityViewModel implements GamePieceClickedListener {

    private boolean isBusy;
    private GameRuleEvaluator gameRuleEvaluator;
    private PieceClickStatusEvaluated pieceClickStatusEvaluated;
    DefaultActivity defaultActivity;
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
        defaultActivity.onGamePieceClickedEvaluated(pieceClickStatusEvaluated);
    }

    public GameRuleEvaluator getGameRuleEvaluator() {
        return gameRuleEvaluator;
    }

    @Override
    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public void executeCommands() {
        processGameLogic();
        resetClickedViewsForFinishedSecondClick();
        resetFloatingPiecePositionForLegalMove();
        setBusy(false);
    }

    private void processGameLogic() {

    }

    private void resetClickedViewsForFinishedSecondClick() {
        if(isSecondClickFinished())
            boardClickListener.onGamePieceClickedEvaluated(PieceClickStatusEvaluated.SECOND_CLICK_FINISHED);
    }

    private boolean isSecondClickFinished() {
        return pieceClickStatusEvaluated.equals(PieceClickStatusEvaluated.SECOND_CLICK_LEGAL_MOVE) || pieceClickStatusEvaluated.equals(PieceClickStatusEvaluated.SECOND_CLICK_ILLEGAL_MOVE) || pieceClickStatusEvaluated.equals(PieceClickStatusEvaluated.SECOND_CLICK_SAME_PIECE_TWICE);
    }

    private void resetFloatingPiecePositionForLegalMove() {
        if (pieceClickStatusEvaluated.equals(PieceClickStatusEvaluated.SECOND_CLICK_LEGAL_MOVE)) {
            defaultActivity.onResetFloatingPiecePosition();
        }
    }
}
