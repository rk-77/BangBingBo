package com.example.bangbingbo.views;

import android.os.Bundle;

import com.example.bangbingbo.R;
import com.example.bangbingbo.game.listeners.AnimationStartStopListener;
import com.example.bangbingbo.viewmodels.DefaultActvitiyViewModel;
import com.example.bangbingbo.animations.GameAnimation;
import com.example.bangbingbo.game.listeners.GamePieceClickStatusEvaluatedListener;
import com.example.bangbingbo.game.enums.PieceClickStatusEvaluated;

import static com.example.bangbingbo.game.GameBoardManager.BoardType;
import static com.example.bangbingbo.game.GameBoardManager.getTileDrawablesForBoardType;
import static com.example.bangbingbo.game.GameBoardManager.getTileViews;

public class DefaultActivity extends BaseActivity implements GamePieceClickStatusEvaluatedListener, AnimationStartStopListener {

    private BoardType boardType = BoardType.NORMAL;
    private DefaultActvitiyViewModel viewModel;
    private GameAnimation gameAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new DefaultActvitiyViewModel(images, this);
        gameAnimation = new GameAnimation(this);
    }

    @Override
    protected void setInitialValuesForViews() {
        //TODO use VIEWMODEL
        scoreView.setText("0");
        targetView.setText("0");
        timeView.setText("0");
        levelView.setText("0");
        totalScoreView.setText("0");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_default;
    }

    @Override
    protected void initViews() {
        super.initViews();
        setupBoard();
    }

    private void setupBoard() {
        tiles = getTileDrawablesForBoardType(this, boardType);
        images = getTileViews(this, boardType);
    }

    @Override
    public void onGamePieceClickedEvaluated(PieceClickStatusEvaluated status) {
        switch (status) {
            case FIRST_CLICK_LEGAL:
                int id = getIDClickedPiece1();
                setFloatingPieceParams(images.get(id));
                startClickAnimation(id);
                break;
            case SECOND_CLICK_LEGAL_MOVE:
                gameAnimation.startSlideAnimation(images.get(getIDClickedPiece1()), images.get(getIDClickedPiece2()), floatingPieceView);
        }
    }


    private int getIDClickedPiece1() {
        return viewModel.getGameRuleEvaluator().getPiece1Clicked();
    }

    private int getIDClickedPiece2() {
        return viewModel.getGameRuleEvaluator().getPiece2Clicked();
    }

    private void startClickAnimation(int pieceClicked) {
        gameAnimation.startClickAnimation(images.get(pieceClicked), floatingPieceView);
    }

    @Override
    public void onAnimationStarted() {
        viewModel.setBusy(true);
    }

    @Override
    public void onAnimationStopped() {
        viewModel.setBusy(false);
    }
}
