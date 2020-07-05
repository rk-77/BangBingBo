package com.example.bangbingbo.views;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bangbingbo.R;

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
    ImageView floatingPieceView;
    private ImageView imageViewNext;

    protected List<Drawable> tiles;
    protected List<ImageView> images;

    private int[] startLocationFloatingPiece = new int[2];

    public List<ImageView> getImages() {
        return images;
    }

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

    public void setFloatingPieceParams(ImageView destination) {
        int[] locationDestination = new int[2];
        destination.getLocationOnScreen(locationDestination);
        Log.d(TAG, "Location destination cell" + locationDestination[0] + "  " + locationDestination[1]);

        floatingPieceView.setTranslationX(locationDestination[0]);
        floatingPieceView.setTranslationY(locationDestination[1]);
        floatingPieceView.setImageDrawable(destination.getDrawable());
        floatingPieceView.setVisibility(View.VISIBLE);
        int[] locationFloatingPiece = new int[2];
        floatingPieceView.getLocationOnScreen(locationFloatingPiece);
        Log.d(TAG, "Location floating piece after translation " + locationFloatingPiece[0] + "  " + locationFloatingPiece[1]);
    }

    protected void resetFloatingPiecePostion() {
        floatingPieceView.setTranslationX(0);
        floatingPieceView.setTranslationY(0);
        floatingPieceView.setVisibility(View.VISIBLE);
    }

    public ImageView getFloatingPieceView() {
        return floatingPieceView;
    }
}