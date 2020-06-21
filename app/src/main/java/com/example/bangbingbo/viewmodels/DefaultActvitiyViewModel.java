package com.example.bangbingbo.viewmodels;

import android.widget.ImageView;

import com.example.bangbingbo.game.listeners.BoardClickListener;
import com.example.bangbingbo.game.GamePiece;
import com.example.bangbingbo.game.GameBoardManager;
import com.example.bangbingbo.game.listeners.GamePieceClickStatusEvaluatedListener;
import com.example.bangbingbo.game.listeners.GamePieceClickedListener;
import com.example.bangbingbo.game.GameRuleEvaluator;
import com.example.bangbingbo.game.enums.PieceClickStatusEvaluated;

import java.util.ArrayList;
import java.util.List;

public class DefaultActvitiyViewModel implements GamePieceClickedListener {

    private boolean isBusy;
    private BoardClickListener boardClickListener;

    private GameRuleEvaluator gameRuleEvaluator;
    List<GamePieceClickStatusEvaluatedListener> statusEvaluatedListeners = new ArrayList<>();

    public DefaultActvitiyViewModel(List<ImageView> images, GamePieceClickStatusEvaluatedListener statusEvaluatedListener) {
        gameRuleEvaluator = GameRuleEvaluator.getInstanceForType(GameBoardManager.BoardType.NORMAL);
        boardClickListener = new BoardClickListener(images, this);
        statusEvaluatedListeners.add(boardClickListener);
        statusEvaluatedListeners.add(statusEvaluatedListener);
    }

    @Override
    public void onGamePieceClicked(GamePiece piece) {
        PieceClickStatusEvaluated pieceClickStatusEvaluated = gameRuleEvaluator.EvaluateClickStatus(piece);
        for (GamePieceClickStatusEvaluatedListener listener : statusEvaluatedListeners) {
            listener.onGamePieceClickedEvaluated(pieceClickStatusEvaluated);
        }
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
}
