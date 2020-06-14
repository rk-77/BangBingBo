package com.example.bangbingbo.Views;

import android.os.Bundle;

import com.example.bangbingbo.R;
import com.example.bangbingbo.ViewModels.DefaultActvitiyViewModel;
import com.example.bangbingbo.game.BoardClickListener;
import com.example.bangbingbo.game.GameStatus;

import static com.example.bangbingbo.game.GameBoardManager.BoardType;
import static com.example.bangbingbo.game.GameBoardManager.getTileDrawablesForBoardType;
import static com.example.bangbingbo.game.GameBoardManager.getTileViews;

public class DefaultActivity extends BaseActivity {

    private BoardType boardType = BoardType.NORMAL;
    private DefaultActvitiyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new DefaultActvitiyViewModel(images);
    }

    @Override
    protected void setInitialValuesForViews() {
        scoreView.setText(String.valueOf(gameStatus.getScore()));
        targetView.setText(String.valueOf(gameStatus.getTargetScore()));
        timeView.setText(String.valueOf(gameStatus.getTime()));
        levelView.setText(String.valueOf(gameStatus.getLevel()));
        totalScoreView.setText(String.valueOf(gameStatus.getTotalScore()));
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

}
