package com.example.bangbingbo.ViewModels;

import android.widget.ImageView;

import com.example.bangbingbo.game.BoardClickListener;
import com.example.bangbingbo.game.ClickedPiece;
import com.example.bangbingbo.game.GameBoardManager;
import com.example.bangbingbo.game.GamePieceClickedListener;
import com.example.bangbingbo.game.GameRuleEvaluator;
import com.example.bangbingbo.game.GameStatus;

import java.util.List;

public class DefaultActvitiyViewModel implements GamePieceClickedListener {

    private BoardClickListener boardClickListener;
    private GameRuleEvaluator gameRuleEvaluator;

    public DefaultActvitiyViewModel(List<ImageView> images) {
        boardClickListener = new BoardClickListener(images, this);
        gameRuleEvaluator = GameRuleEvaluator.getInstanceForType(GameBoardManager.BoardType.NORMAL);
    }

    @Override
    public void onGamePieceClicked(ClickedPiece piece) {
       gameRuleEvaluator.EvaluateClickStatus(piece);
       // call Clicklistener to reset
       //start animation
    }
}
