package com.example.bangbingbo.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bangbingbo.R;
import com.example.bangbingbo.game.GameStatus;

import java.util.List;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    protected Typeface ttfBitwise;
    protected Typeface ttfDream;

    TextView scoreLabel;
    TextView scoreView;
    TextView timeView;
    TextView targetScoreLabel;
    TextView targetView;
    TextView totalScoreView;
    TextView bestView;
    TextView levelView;

    private ViewGroup gameContainer;
    private ImageView floatingPieceView;
    private ImageView imageViewNext;

    protected List<Drawable> tiles;
    protected List<ImageView> images;

    protected GameStatus gameStatus;

    private int[] startLocationFloatingPiece = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        initFonts();
        initViews();
        setInitialValuesForViews();
    }

    protected abstract void setInitialValuesForViews();

    protected abstract int getLayoutResourceId();

    protected void initViews() {
        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        scoreView = (TextView) findViewById(R.id.score);
        targetView = (TextView) findViewById(R.id.targetScore);
        timeView = (TextView) findViewById(R.id.timeView);
        targetScoreLabel = (TextView) findViewById(R.id.targetScoreLabel);
        totalScoreView = (TextView) findViewById(R.id.totalScore);
        bestView = (TextView) findViewById(R.id.best);
        levelView = (TextView) findViewById(R.id.level);
        imageViewNext = (ImageView) findViewById(R.id.imageViewNext);
        floatingPieceView = (ImageView) findViewById(R.id.floatingPieceView);

        gameContainer = (ViewGroup) findViewById(R.id.gameContainer);
        gameContainer.getViewTreeObserver().addOnPreDrawListener(mPreDrawListener);
    }

    ViewTreeObserver.OnPreDrawListener mPreDrawListener =
            new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    gameContainer.getViewTreeObserver().removeOnPreDrawListener(this);

                    floatingPieceView.getLocationOnScreen(startLocationFloatingPiece);
                    Log.d(TAG, "onPreDraw called, startLocationFloatingPiece " + startLocationFloatingPiece[0] + " , " + startLocationFloatingPiece[1]);
                  //  Log.d(TAG, "randomFirstCell, start animation to cell " + getRandomFirstCell());
                  //  startNextPieceAnimation(getRandomFirstCell() - 1);
                    return false;
                }
            };


    private void initFonts() {
        ttfBitwise = Typeface.createFromAsset(getAssets(), "Bitwise.ttf");
        ttfDream = Typeface.createFromAsset(getAssets(), "destructobeambb_bold.ttf");

        ((TextView) findViewById(R.id.scoreLabel)).setTypeface(ttfBitwise);
        ((TextView) findViewById(R.id.timeView)).setTypeface(ttfDream);
        ((TextView) findViewById(R.id.targetScoreLabel)).setTypeface(ttfBitwise);
        ((TextView) findViewById(R.id.best)).setTypeface(ttfBitwise);
    }
}