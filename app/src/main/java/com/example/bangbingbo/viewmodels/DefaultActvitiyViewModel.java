package com.example.bangbingbo.viewmodels;

import android.widget.ImageView;

import com.example.bangbingbo.game.listeners.GamePieceClickStatusEvaluatedListener;
import com.example.bangbingbo.game.listeners.StatusAndPiecePositionListener;
import com.example.bangbingbo.game.listeners.BoardClickListener;
import com.example.bangbingbo.game.GamePiece;
import com.example.bangbingbo.game.GameBoardManager;
import com.example.bangbingbo.game.listeners.GamePieceClickedListener;
import com.example.bangbingbo.game.GameRuleEvaluator;
import com.example.bangbingbo.game.enums.PieceClickStatusEvaluated;

import java.util.List;

public class DefaultActvitiyViewModel implements GamePieceClickedListener {

    private boolean isBusy;
    private GameRuleEvaluator gameRuleEvaluator;
    private PieceClickStatusEvaluated pieceClickStatusEvaluated;
    StatusAndPiecePositionListener statusAndPiecePositionListener;
    public GamePieceClickStatusEvaluatedListener boardClickListener;

    public DefaultActvitiyViewModel(List<ImageView> images, StatusAndPiecePositionListener statusAndPiecePositionListener) {
        gameRuleEvaluator = GameRuleEvaluator.getInstanceForType(GameBoardManager.BoardType.NORMAL);
        boardClickListener = new BoardClickListener(images, this);
        this.statusAndPiecePositionListener = statusAndPiecePositionListener;
    }

    @Override
    public void onGamePieceClicked(GamePiece piece) {
        pieceClickStatusEvaluated = gameRuleEvaluator.EvaluateClickStatus(piece);
        boardClickListener.onGamePieceClickedEvaluated(pieceClickStatusEvaluated);
        statusAndPiecePositionListener.onGamePieceClickedEvaluated(pieceClickStatusEvaluated);
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
            statusAndPiecePositionListener.onResetFloatingPiecePosition();
        }
    }
}
