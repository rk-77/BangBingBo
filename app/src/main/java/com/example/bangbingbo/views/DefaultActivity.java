package com.example.bangbingbo.views;

import android.os.Bundle;
import android.view.View;

import com.example.bangbingbo.R;
import com.example.bangbingbo.game.listeners.StatusAndPiecePositionListener;
import com.example.bangbingbo.game.listeners.AnimationStartStopListener;
import com.example.bangbingbo.game.listeners.commandexecutors.CommandExecutorHelper;
import com.example.bangbingbo.viewmodels.DefaultActivityViewModel;
import com.example.bangbingbo.animations.GameAnimation;
import com.example.bangbingbo.game.enums.PieceClickStatusEvaluated;

import static com.example.bangbingbo.game.GameBoardManager.BoardType;
import static com.example.bangbingbo.game.GameBoardManager.getTileDrawablesForBoardType;
import static com.example.bangbingbo.game.GameBoardManager.getTileViews;

public class DefaultActivity extends BaseActivity implements StatusAndPiecePositionListener, AnimationStartStopListener {

    private BoardType boardType = BoardType.NORMAL;
    private DefaultActivityViewModel viewModel;
    private GameAnimation gameAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new DefaultActivityViewModel(this);
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
        CommandExecutorHelper.executeUICommands(this, status);
    }


    public int getIDClickedPiece1() {
        return viewModel.getGameRuleEvaluator().getPiece1Clicked();
    }

    public int getIDClickedPiece2() {
        return viewModel.getGameRuleEvaluator().getPiece2Clicked();
    }


    @Override
    public void onAnimationStarted() {
      //  viewModel.setBusy(true);
    }

    @Override
    public void onAnimationStopped() {
        viewModel.executeCommands();
    }

    @Override
    public void onResetFloatingPiecePosition() {
        floatingPieceView.setTranslationX(0);
        floatingPieceView.setTranslationY(0);
        floatingPieceView.setVisibility(View.VISIBLE);
    }

    public GameAnimation getGameAnimation() {
        return gameAnimation;
    }

    public BoardType getBoardType() {
        return boardType;
    }

    public DefaultActivityViewModel getViewModel() {
        return viewModel;
    }

}
