package de.bangbingbo.activities;

import java.util.ArrayList;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.bangbingbo.R;


public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity";


    private Typeface ttfBitwise;
    private Typeface ttfdDream;

    TextView scoreLabel;
    TextView scoreView;
    TextView timeView;
    TextView targetScoreLabel;
    TextView targetView;
    TextView totalScoreView;
    TextView bestView;
    TextView levelView;

    private static final int OFFSET_END_OF_ROW = 6;
    private static final int OFFSET_END_OF_COLUMN = 6;
    private static final int NUMBER_OF_ROWS = 7;
    private static final int NUMBER_OF_COLUMNS = 7;

    private static final int PADDING_PIECE = 7;

    int[] occupiedField = new int[50];
    int[] avail = new int[50];
    int numberOfPiecesInSack = 49;

    private int sourceField;
    private int destinationField;
    private int nextFieldInArray;

    private double dsbest;

    private int calculatedBestField;
    private int colValActualPieceInSack;

    public int tempScore;
    public int[] twos = new int[7];
    public int[] threes = new int[6];
    public int[] fours = new int[5];
    public int[] fives = new int[4];
    public int[] sixes = new int[3];

    private static final int RED = 1;
    private static final int BLACK = 2;
    private static final int GREEN = 3;
    private static final int YELLOW = 4;
    private static final int PURPLE = 5;
    private static final int BLUE = 6;
    private static final int WHITE = 7;

    public static final int[] CELLSEQ = {0, 25, 26, 33, 32, 31, 24, 17, 18,
            19, 20, 27, 34, 41, 40, 39, 38, 37, 30, 23, 16, 9, 10, 11, 12, 13,
            14, 21, 28, 35, 42, 49, 48, 47, 46, 45, 44, 43, 36, 29, 22, 15, 8,
            1, 2, 3, 4, 5, 6, 7};

    //  public int[] rowscore = new int[8];
    //  public int[] colscore = new int[8];

    private boolean isImageView1Clicked, isImageView2Clicked;
    private boolean isPlayersTurn = false;

    int[] location = new int[2];
    int offsetLeft, offsetTop, offsetBottom, offsetRight;

    Drawable redCircleDrawable;
    Drawable blackCircleDrawable;
    Drawable blueCircleDrawable;
    Drawable yellowCircleDrawable;
    Drawable greenCircleDrawable;
    Drawable purpleCircleDrawable;
    Drawable whiteCircleDrawable;
    Drawable fieldHighlightedDrawable;
    Drawable borderDrawable;
    Drawable borderTranspDrawable;

    ImageView imageView;
    ArrayList<ImageView> imageList = new ArrayList<ImageView>();

    String[] imageViewIds =

            {

                    "imageView1", "imageView2", "imageView3", "imageView4", "imageView5", "imageView6",
                    "imageView7", "imageView8", "imageView9", "imageView10", "imageView11", "imageView12",
                    "imageView13", "imageView14", "imageView15", "imageView16", "imageView17", "imageView18",
                    "imageView19", "imageView20", "imageView21", "imageView22", "imageView23", "imageView24",
                    "imageView25", "imageView26", "imageView27", "imageView28", "imageView29", "imageView30",
                    "imageView31", "imageView32", "imageView33", "imageView34", "imageView35", "imageView36",
                    "imageView37", "imageView38", "imageView39", "imageView40", "imageView41", "imageView42",
                    "imageView43", "imageView44", "imageView45", "imageView46", "imageView47", "imageView48",
                    "imageView49"

            };

    private ViewGroup gameContainer;
    private ImageView floatingPieceView;
    private ImageView imageViewNext;

    private int imageView1Clicked;
    private int imageView2Clicked;

    private ArrayList<Integer> legalfields = new ArrayList<Integer>();

    private DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
    private static final AccelerateDecelerateInterpolator acelerateInterpolator = new AccelerateDecelerateInterpolator();
    private OvershootInterpolator overshootInterpolator = new OvershootInterpolator();

    private int[] startLocationFloatingPiece = new int[2];

    private int randomFirstCell;
    private Drawable tempImgView1ClickedDrawable;
    private Drawable tempFloatingPieceDrawable;
    private int tempClickedCell2;
    private int tempClickedCell1;
    private ImageView destinationView;
    private boolean isBlocked;
    private Object horizontalChain;
    private int[] twosInColIndex;
    private int[] threesInColIndex;
    private int[] foursInColIndex;
    private int[] fivesInColIndex;
    private int[] sixesInColIndex;
    private int[] sevenInColIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ttfBitwise = Typeface.createFromAsset(getAssets(), "Bitwise.ttf");
        ttfdDream = Typeface.createFromAsset(getAssets(), "destructobeambb_bold.ttf");
        redCircleDrawable = getResources().getDrawable(R.drawable.circle_red);
        blackCircleDrawable = getResources().getDrawable(R.drawable.circle_black);
        blueCircleDrawable = getResources().getDrawable(R.drawable.circle);
        yellowCircleDrawable = getResources().getDrawable(R.drawable.circle_yellow);
        greenCircleDrawable = getResources().getDrawable(R.drawable.circle_green);
        purpleCircleDrawable = getResources().getDrawable(R.drawable.circle_purple);
        whiteCircleDrawable = getResources().getDrawable(R.drawable.circle_white);
        fieldHighlightedDrawable = getResources().getDrawable(R.drawable.field_highlighted);
        borderDrawable = getResources().getDrawable(R.drawable.border);
        borderTranspDrawable = getResources().getDrawable(R.drawable.border_transp);

        ((TextView) findViewById(R.id.scoreLabel)).setTypeface(ttfBitwise); //addding font
        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        scoreView = (TextView) findViewById(R.id.score);
        targetView = (TextView) findViewById(R.id.targetScore);

        ((TextView) findViewById(R.id.timeView)).setTypeface(ttfdDream);
        timeView = (TextView) findViewById(R.id.timeView);//add font

        ((TextView) findViewById(R.id.targetScoreLabel)).setTypeface(ttfBitwise); //adding font
        targetScoreLabel = (TextView) findViewById(R.id.targetScoreLabel);
        totalScoreView = (TextView) findViewById(R.id.totalScore);
        ((TextView) findViewById(R.id.best)).setTypeface(ttfBitwise);   //adding font
        bestView = (TextView) findViewById(R.id.best);

        levelView = (TextView) findViewById(R.id.level);

        scoreView.setText(String.valueOf(0));
        targetView.setText(String.valueOf(0));
        timeView.setText(String.valueOf(64));
        // bestView.setText(String.valueOf(0));
        levelView.setText(String.valueOf(0));
        totalScoreView.setText(String.valueOf(0));

        gameContainer = (ViewGroup) findViewById(R.id.gameContainer);
        gameContainer.getViewTreeObserver().addOnPreDrawListener(mPreDrawListener);
        imageViewNext = (ImageView) findViewById(R.id.imageViewNext);
        floatingPieceView = (ImageView) findViewById(R.id.floatingPieceView);

        setupBoard();
        initGame();

        //startDemoRound();

    }


    private void setupBoard() {

        for (int i = 0; i < 49; i++) {
            int id = getResources().getIdentifier(imageViewIds[i], "id", getPackageName());
            imageView = (ImageView) findViewById(id);
            imageView.setOnClickListener(this);
            imageList.add(imageView);
        }
    }

    @Override
    public void onClick(View v) {
        if (isPlayersTurn) {
            hideLegalMoves();
            for (int i = 0; i < imageList.size(); i++) {
                if (v.getId() == imageList.get(i).getId()) {

                    //   Toast.makeText(this, v.getId() + " clicked and  empty, " + String.valueOf(imageList.get(i).getDrawable() == null), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, " cell " + i + " clicked");

                    if (!isImageView1Clicked && imageList.get(i).getDrawable() != null) {
                        synchronized (this) {
                            isImageView1Clicked = true;
                            imageView1Clicked = i + 1;
                            Log.d(TAG, "if case : imageview1clicked true, and not null: " + imageView1Clicked);
                            setFloatingPieceParams(imageList.get(i));
                            startClickAnimation(imageView1Clicked - 1);
                            break;
                        }

                    } else if (!isBlocked) {
                        if (isImageView1Clicked && imageList.get(i).getDrawable() == null) {
                            synchronized (this) {
                                imageView2Clicked = i + 1;
                                isImageView2Clicked = true;
                                Log.d(TAG, "else if: view1 " + imageView1Clicked + " view2 " + imageView2Clicked + ", checking slideOk");
                                if (isSlideOK(imageView1Clicked, imageView2Clicked)) {

                                    startSlideAnimation(imageView1Clicked - 1, imageView2Clicked - 1);
                                    updatePiecesDataContainer(imageView1Clicked, imageView2Clicked);
                                    checkPatterns(imageView2Clicked);
                                    resetImageViewsClicked();

                                    isPlayersTurn = false;

                                    newPiece();
                                    resetFloatingPiecePostion();
                                } else {
                                    Log.d(TAG, "slide not ok, setting views to false");
                                    resetImageViewsClicked();
                                }
                                break;
                            }

                        } else if (imageView1Clicked - 1 == i) {
                            Log.d(TAG, "clicked same piece, setting views to false");
                            resetImageViewsClicked();
                            break;
                        } else if (isImageView1Clicked == true && imageList.get(i).getDrawable() != null) {
                            Log.d(TAG, "clicked different piece, reset view2, set view1 to  piece " + String.valueOf(i + 1));
                            isImageView2Clicked = false;
                            imageView1Clicked = i + 1;
                            setFloatingPieceParams(imageList.get(i));
                            imageView2Clicked = 0;
                            getLegalMoves(imageView1Clicked);
                            break;
                        }
                    }


                }
            }
        }


    }

    private void checkPatterns(int destinationField) {
        //check horizontal and vertical patterns/chains

        //horizontal check  [0]=leftIndex, [1] rightIndex, [2] chainSize
        int[] chainCoordsHor = getHorChainSize(destinationField);
        Log.d(TAG, "left Chain Index " + chainCoordsHor[0] + ", right Chain Index " + chainCoordsHor[1] + " kettenlaenge " + chainCoordsHor[2]);
        scoreRowDetail(destinationField, chainCoordsHor[0], chainCoordsHor[2]);

        //column check
        int[] chainCoordsVert = getVertChainSize(destinationField);
        Log.d(TAG, "Top Chain Index " + chainCoordsVert[0] + ", Bottomn Chain Index " + chainCoordsVert[1] + " kettenlaenge " + chainCoordsVert[2]);
        scoreColDetail(destinationField, chainCoordsVert[0], chainCoordsVert[1], chainCoordsVert[2]);


        // check magnetic moves
        // horizontalMagnetic moves
        int rowNr = calcRowForField(destinationField);
        // getRowBorderForField
        int leftBorder = getLeftBorderForRow(rowNr);
        int rightBorder = getRightBorderForRow(rowNr);
        while ( (destinationField - 1 > leftBorder) && (occupiedField[destinationField - 1] == 0) ) {

        }

    }

    private int getRightBorderForRow(int rowNr) {
        return NUMBER_OF_ROWS * rowNr;
    }

    private int getLeftBorderForRow(int rowNr) {
        return getRightBorderForRow(rowNr) - OFFSET_END_OF_ROW;
    }

    private void scoreColDetail(int destinationField, int top, int bottom, int chainSizeVert) {
        String TAG = "BLU";
        int colStartCell = top;
        int colEndCell = bottom;
        int destination = destinationField;
        int chainlengthVert = chainSizeVert;

        twosInColIndex = new int[6];
        threesInColIndex = new int[5];
        foursInColIndex = new int[4];
        fivesInColIndex = new int[3];
        sixesInColIndex = new int[2];
        sevenInColIndex = new int[1];

        if (chainlengthVert > 1) {
            int counterTwosIndex = 0;   //cellinex<chainLengthVert- 1 exchanged by destination -Number_Col
            for (int cellIndex = 0; cellIndex + colStartCell <= colEndCell - NUMBER_OF_COLUMNS; cellIndex += NUMBER_OF_COLUMNS) {
                if ((occupiedField[(colStartCell + cellIndex)] != 0)
                        && (occupiedField[(colStartCell + cellIndex)]
                        == occupiedField[(colStartCell + cellIndex + NUMBER_OF_COLUMNS)])
                        && ((destination == colStartCell + cellIndex) || (destination == colStartCell + cellIndex + NUMBER_OF_COLUMNS))) {
                    twosInColIndex[counterTwosIndex] = (colStartCell + cellIndex);
                    Log.d(TAG, "two, field " + twosInColIndex[counterTwosIndex]);
                    counterTwosIndex++;
                }
            }

            if (chainlengthVert > 2) {
                int counterThreesIndex = 0;
                for (int cellIndex = 0; cellIndex + colStartCell <= colEndCell - (2 * NUMBER_OF_COLUMNS); cellIndex += NUMBER_OF_COLUMNS) {
                    if ((occupiedField[(colStartCell + cellIndex)] != 0)
                            && (occupiedField[colStartCell + cellIndex + NUMBER_OF_COLUMNS] != 0)
                            && (occupiedField[(colStartCell + cellIndex)] == occupiedField[(colStartCell
                            + cellIndex + (2 * NUMBER_OF_COLUMNS))])
                            && (destination == (colStartCell + cellIndex) || (destination == colStartCell + cellIndex + NUMBER_OF_COLUMNS)
                            || destination == (colStartCell + cellIndex + (2 * NUMBER_OF_COLUMNS)))) {
                        threesInColIndex[counterThreesIndex] = (colStartCell + cellIndex);
                        Log.d(TAG, "three, field " + threesInColIndex[counterThreesIndex]);
                        counterThreesIndex++;
                    }
                }


                if (chainlengthVert > 3) {
                    {
                        int counterFoursIndex = 0;
                        for (int cellIndex = 0; cellIndex + colStartCell <= colEndCell - (3 * NUMBER_OF_COLUMNS); cellIndex += NUMBER_OF_COLUMNS) {
                            int startField = occupiedField[colStartCell + cellIndex];
                            int endField = occupiedField[colStartCell + cellIndex + (3 * NUMBER_OF_COLUMNS)];
                            int innerfield1 = occupiedField[colStartCell + cellIndex + NUMBER_OF_COLUMNS];
                            int innerfield2 = occupiedField[colStartCell + cellIndex + (2 * NUMBER_OF_COLUMNS)];
                            if (((destination >= colStartCell + cellIndex) && (destination <= colStartCell + cellIndex + (3 * NUMBER_OF_COLUMNS))) &&
                                    ((startField != 0) && (startField == endField)) &&
                                    ((innerfield1 != 0) && (innerfield1 == innerfield2))) {
                                foursInColIndex[counterFoursIndex] = (colStartCell + cellIndex);
                                Log.d(TAG, "four, field " + foursInColIndex[counterFoursIndex]);
                                counterFoursIndex++;
                            }
                        }
                    }


                    if (chainlengthVert > 4) {

                        {
                            int counterFivesIndex = 0;
                            for (int cellIndex = 0; cellIndex + colStartCell <= colEndCell - (4 * NUMBER_OF_COLUMNS); cellIndex += NUMBER_OF_COLUMNS) {
                                int startField = occupiedField[colStartCell + cellIndex];  //most left
                                int endField = occupiedField[colStartCell + cellIndex + (4 * NUMBER_OF_COLUMNS)];  //most right
                                int innerfield1 = occupiedField[colStartCell + cellIndex + NUMBER_OF_COLUMNS]; //inner left
                                int innerfield2 = occupiedField[colStartCell + cellIndex + (3 * NUMBER_OF_COLUMNS)];  //inner right
                                int centerfield = occupiedField[colStartCell + cellIndex + (2 * NUMBER_OF_COLUMNS)];  //most inner center

                                if (((destination >= colStartCell + cellIndex) && (destination <= colStartCell + cellIndex + (4 * NUMBER_OF_COLUMNS))) &&
                                        ((startField != 0) && (startField == endField)) &&
                                        ((innerfield1 != 0) && (innerfield1 == innerfield2)) && centerfield != 0) {
                                    fivesInColIndex[counterFivesIndex] = (colStartCell + cellIndex);
                                    Log.d(TAG, "five, field " + fivesInColIndex[counterFivesIndex]);
                                    counterFivesIndex++;
                                }
                            }
                        }


                        if (chainlengthVert > 5) {

                            {
                                int counterSixesIndex = 0;
                                for (int cellIndex = 0; cellIndex + colStartCell <= colEndCell - (5 * NUMBER_OF_COLUMNS); cellIndex += NUMBER_OF_COLUMNS) {
                                    int startField = occupiedField[colStartCell + cellIndex];  //most left
                                    int endField = occupiedField[colStartCell + cellIndex + (5 * NUMBER_OF_COLUMNS)];  //most right
                                    int innerfield1 = occupiedField[colStartCell + cellIndex + NUMBER_OF_COLUMNS]; //inner left
                                    int innerfield2 = occupiedField[colStartCell + cellIndex + (4 * NUMBER_OF_COLUMNS)];  //inner right
                                    int centerfield1 = occupiedField[colStartCell + cellIndex + (2 * NUMBER_OF_COLUMNS)];  //left inner center
                                    int centerfield2 = occupiedField[colStartCell + cellIndex + (3 * NUMBER_OF_COLUMNS)];  //right inner center

                                    if (((destination >= colStartCell + cellIndex) && (destination <= colStartCell + cellIndex + (5 * NUMBER_OF_COLUMNS))) &&
                                            ((startField != 0) && (startField == endField)) &&
                                            ((innerfield1 != 0) && (innerfield1 == innerfield2)) &&
                                            ((centerfield1 != 0) && (centerfield1 == centerfield2))
                                            ) {
                                        sixesInColIndex[counterSixesIndex] = (colStartCell + cellIndex);
                                        Log.d(TAG, "six, field " + sixesInColIndex[counterSixesIndex]);
                                    }
                                }
                            }

                            if (chainlengthVert > 6) {

                                int cellIndex = 0;
                                int startField = occupiedField[colStartCell];  //most left
                                int endField = occupiedField[colStartCell + (6 * NUMBER_OF_COLUMNS)];  //most right
                                int innerfield1 = occupiedField[colStartCell + NUMBER_OF_COLUMNS]; //inner left
                                int innerfield2 = occupiedField[colStartCell + (5 * NUMBER_OF_COLUMNS)];  //inner right
                                int mostInnerfield1 = occupiedField[colStartCell + (2 * NUMBER_OF_COLUMNS)];  //most left inner
                                int mostInnerfield2 = occupiedField[colStartCell + (4 * NUMBER_OF_COLUMNS)];  //most right inner
                                int centerfield = occupiedField[colStartCell + 3 * NUMBER_OF_COLUMNS];  //center

                                if (
                                        ((startField != 0) && (startField == endField)) &&
                                                ((innerfield1 != 0) && (innerfield1 == innerfield2)) &&
                                                ((mostInnerfield1 != 0) && (mostInnerfield1 == mostInnerfield2)) &&
                                                (centerfield != 0)
                                        ) {
                                    sevenInColIndex[cellIndex] = (colStartCell);
                                    Log.d(TAG, "seven, field " + sevenInColIndex[cellIndex]);
                                }


                            }


                        }
                    }

                }


            }
        }


    }


    private int[] getVertChainSize(int field) {
        int[] chainCoordinates = new int[3]; //[0]=topIndex, [1] bottomIndex, [2] chainsize
        int vertChainSize = 1;
        int topChainIndex = field;
        int bottomChainIndex = field;
        int colNr = calcColForField(field);
        if (colNr == -1) {
            Log.e(TAG, "Error no Column found for field");
            Toast.makeText(this, "No row found for field", Toast.LENGTH_LONG).show();
        } else {
            Log.d(TAG, "column found for field " + field + " " + colNr);
        }
        //left neighbor
        int topBorder = colNr;
        int bottomBorder = ((NUMBER_OF_ROWS * (NUMBER_OF_COLUMNS - 1)) + colNr);

        for (int cellIndex = field; cellIndex > topBorder; cellIndex -= NUMBER_OF_COLUMNS) {
            if ((occupiedField[(cellIndex - NUMBER_OF_COLUMNS)] != 0)) {
                vertChainSize++;
                topChainIndex = cellIndex - NUMBER_OF_COLUMNS;
            } else {
                break;
            }
        }

        for (int cellIndex = field; cellIndex < bottomBorder; cellIndex += NUMBER_OF_COLUMNS) {
            if ((occupiedField[(cellIndex + NUMBER_OF_COLUMNS)] != 0)) {
                vertChainSize++;
                bottomChainIndex = cellIndex + NUMBER_OF_COLUMNS;
            } else {
                break;
            }
        }

        chainCoordinates[0] = topChainIndex;
        chainCoordinates[1] = bottomChainIndex;
        chainCoordinates[2] = vertChainSize;

        return chainCoordinates;
    }

    private int calcColForField(int field) {

        if (field % NUMBER_OF_COLUMNS == 0)
            return NUMBER_OF_COLUMNS;
        else return
                (field % NUMBER_OF_COLUMNS);
    }

    private void scoreRowDetail(int destinationField, int indexLeft, int chainSizeVert) {
        String TAG = "BLI";
        int rowStartCell = indexLeft;
        int destination = destinationField;
        int chainlengthVert = chainSizeVert;

        twosInColIndex = new int[6];
        threesInColIndex = new int[5];
        foursInColIndex = new int[4];
        fivesInColIndex = new int[3];
        sixesInColIndex = new int[2];
        sevenInColIndex = new int[1];

        if (chainlengthVert > 1) {
            for (int cellIndex = 0; cellIndex < chainlengthVert - 1; cellIndex++) {
                if ((occupiedField[(rowStartCell + cellIndex)] != 0)
                        && (occupiedField[(rowStartCell + cellIndex)]
                        == occupiedField[(rowStartCell + cellIndex + 1)])
                        && ((destination == rowStartCell + cellIndex) || (destination == rowStartCell + cellIndex + 1))) {
                    twosInColIndex[cellIndex] = (rowStartCell + cellIndex);
                    Log.d(TAG, "two, field " + twosInColIndex[cellIndex]);
                }

            }

            if (chainlengthVert > 2) {
                for (int cellIndex = 0; cellIndex < chainlengthVert - 2; cellIndex++) {
                    if ((occupiedField[(rowStartCell + cellIndex)] != 0)
                            && (occupiedField[rowStartCell + cellIndex + 1] != 0)
                            && (occupiedField[(rowStartCell + cellIndex)] == occupiedField[(rowStartCell
                            + cellIndex + 2)])
                            && (destination == (rowStartCell + cellIndex) || (destination == rowStartCell + cellIndex + 1) || destination == (rowStartCell + cellIndex + 2))) {
                        threesInColIndex[cellIndex] = (rowStartCell + cellIndex);
                        Log.d(TAG, "three, field " + threesInColIndex[cellIndex]);
                    }
                }


                if (chainlengthVert > 3) {
                    {
                        for (int cellIndex = 0; cellIndex < chainlengthVert - 3; cellIndex++) {
                            int startField = occupiedField[rowStartCell + cellIndex];
                            int endField = occupiedField[rowStartCell + cellIndex + 3];
                            int innerfield1 = occupiedField[rowStartCell + cellIndex + 1];
                            int innerfield2 = occupiedField[rowStartCell + cellIndex + 2];
                            if (((destination >= rowStartCell + cellIndex) && (destination <= rowStartCell + cellIndex + 3)) &&
                                    ((startField != 0) && (startField == endField)) &&
                                    ((innerfield1 != 0) && (innerfield1 == innerfield2))) {
                                foursInColIndex[cellIndex] = (rowStartCell + cellIndex);
                                Log.d(TAG, "four, field " + foursInColIndex[cellIndex]);
                            }
                        }
                    }


                    if (chainlengthVert > 4) {

                        {
                            for (int cellIndex = 0; cellIndex < chainlengthVert - 4; cellIndex++) {
                                int startField = occupiedField[rowStartCell + cellIndex];  //most left
                                int endField = occupiedField[rowStartCell + cellIndex + 4];  //most right
                                int innerfield1 = occupiedField[rowStartCell + cellIndex + 1]; //inner left
                                int innerfield2 = occupiedField[rowStartCell + cellIndex + 3];  //inner right
                                int centerfield = occupiedField[rowStartCell + cellIndex + 2];  //most inner center

                                if (((destination >= rowStartCell + cellIndex) && (destination <= rowStartCell + cellIndex + 4)) &&
                                        ((startField != 0) && (startField == endField)) &&
                                        ((innerfield1 != 0) && (innerfield1 == innerfield2)) && centerfield != 0) {
                                    fivesInColIndex[cellIndex] = (rowStartCell + cellIndex);
                                    Log.d(TAG, "five, field " + fivesInColIndex[cellIndex]);
                                }
                            }
                        }


                        if (chainlengthVert > 5) {


                            {
                                for (int cellIndex = 0; cellIndex < chainlengthVert - 5; cellIndex++) {

                                    int startField = occupiedField[rowStartCell + cellIndex];  //most left
                                    int endField = occupiedField[rowStartCell + cellIndex + 5];  //most right
                                    int innerfield1 = occupiedField[rowStartCell + cellIndex + 1]; //inner left
                                    int innerfield2 = occupiedField[rowStartCell + cellIndex + 4];  //inner right
                                    int centerfield1 = occupiedField[rowStartCell + cellIndex + 2];  //left inner center
                                    int centerfield2 = occupiedField[rowStartCell + cellIndex + 3];  //right inner center

                                    if (((destination >= rowStartCell + cellIndex) && (destination <= rowStartCell + cellIndex + 5)) &&
                                            ((startField != 0) && (startField == endField)) &&
                                            ((innerfield1 != 0) && (innerfield1 == innerfield2)) &&
                                            ((centerfield1 != 0) && (centerfield1 == centerfield2))
                                            ) {
                                        sixesInColIndex[cellIndex] = (rowStartCell + cellIndex);
                                        Log.d(TAG, "six, field " + sixesInColIndex[cellIndex]);
                                    }
                                }
                            }


                            if (chainlengthVert > 6) {

                                int cellIndex = 0;

                                int startField = occupiedField[rowStartCell];  //most left
                                int endField = occupiedField[rowStartCell + 6];  //most right
                                int innerfield1 = occupiedField[rowStartCell + 1]; //inner left
                                int innerfield2 = occupiedField[rowStartCell + 5];  //inner right
                                int mostInnerfield1 = occupiedField[rowStartCell + 2];  //most left inner
                                int mostInnerfield2 = occupiedField[rowStartCell + 4];  //most right inner
                                int centerfield = occupiedField[rowStartCell + 3];  //center

                                if (
                                        ((startField != 0) && (startField == endField)) &&
                                                ((innerfield1 != 0) && (innerfield1 == innerfield2)) &&
                                                ((mostInnerfield1 != 0) && (mostInnerfield1 == mostInnerfield2)) &&
                                                (centerfield != 0)
                                        ) {
                                    sevenInColIndex[cellIndex] = (rowStartCell);
                                    Log.d(TAG, "seven, field " + sevenInColIndex[cellIndex]);
                                }


                            }


                        }
                    }

                }


            }
        }


    }


    private void resetFloatingPiecePostion() {
        floatingPieceView.setTranslationX(0);
        floatingPieceView.setTranslationY(0);
        floatingPieceView.setVisibility(View.VISIBLE);
    }

    private void startSlideAnimation(int sourceField, int destinationField) {

        isBlocked = true;
        imageList.get(sourceField).setImageDrawable(null);
        Log.d(TAG, "slide ok, updating pieces data, resetting flags");
        int[] destinationLocation = new int[2];
        imageList.get(destinationField).getLocationOnScreen(destinationLocation);
        floatingPieceView.setVisibility(View.VISIBLE);

        tempFloatingPieceDrawable = floatingPieceView.getDrawable();
        tempClickedCell2 = destinationField;

        floatingPieceView.animate().translationX(destinationLocation[0]).translationY(destinationLocation[1]).setInterpolator(decelerateInterpolator).withEndAction(new Runnable() {
            @Override
            public void run() {
                imageList.get(tempClickedCell2).setImageDrawable(tempFloatingPieceDrawable);
                floatingPieceView.setVisibility(View.INVISIBLE);
                floatingPieceView.setTranslationX(0);
                floatingPieceView.setTranslationY(0);
                floatingPieceView.setVisibility(View.VISIBLE);
                isBlocked = false;
                startNextPieceAnimation(getCalculatedBestField() - 1);

            }
        });
    }

    private void startClickAnimation(final int sourceField) {

        isBlocked = true;
        tempImgView1ClickedDrawable = imageList.get(sourceField).getDrawable();
        imageList.get(sourceField).setImageDrawable(null);

        Runnable endAction = new Runnable() {
            public void run() {
                floatingPieceView.animate().setInterpolator(overshootInterpolator).scaleX(1.0f).scaleY(1.0f).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        imageList.get(sourceField).setImageDrawable(tempImgView1ClickedDrawable);
                        floatingPieceView.setVisibility(View.INVISIBLE);
                        getLegalMoves(sourceField + 1);
                        isBlocked = false;
                    }
                });


            }
        };
        floatingPieceView.animate().setInterpolator(decelerateInterpolator).scaleX(0.7f).scaleY(0.7f).withEndAction(endAction);

    }


    private void resetImageViewsClicked() {
        isImageView1Clicked = false;
        isImageView2Clicked = false;
        imageView1Clicked = 0;
        imageView2Clicked = 0;
    }


    private void updatePiecesDataContainer(int sourceField, int destinationField) {
        Log.d(TAG, "update " + " view1: " + imageView1Clicked + " with value " +
                occupiedField[sourceField] + " moved to view 2: " +
                destinationField + " with value " + occupiedField[destinationField]);
        occupiedField[destinationField] = occupiedField[sourceField];
        occupiedField[sourceField] = 0;

    }


    private void setFloatingPieceParams(ImageView destination) {
        int[] locationDestination = new int[2];
        destination.getLocationOnScreen(locationDestination);
        Log.d(TAG, "//////////////");
        Log.d(TAG, "Location destination cell" + locationDestination[0] + "  " + locationDestination[1]);

        floatingPieceView.setTranslationX(locationDestination[0]);
        floatingPieceView.setTranslationY(locationDestination[1]);
        floatingPieceView.setImageDrawable(destination.getDrawable());
        Log.d(TAG, "floating Piece drawable set to drawable of destination cell");
        floatingPieceView.setVisibility(View.VISIBLE);
        int[] locationFloatingPiece = new int[2];
        floatingPieceView.getLocationOnScreen(locationFloatingPiece);
        Log.d(TAG, "Location floating piece after translation " + locationFloatingPiece[0] + "  " + locationFloatingPiece[1]);

    }


/*    private void setFloatingPieceParams(ImageView destinationCell) {
        int[]destinationCellLocation = new int[2];
        destinationCell.getLocationOnScreen(destinationCellLocation);
        Log.d(TAG, "Clicked cell imageViewclicked1: x-Location " + destinationCellLocation[0] + " y-location " + destinationCellLocation[1]);


        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) floatingPieceView.getLayoutParams();
        int[]floatingPieceLocation = new int[2];
        floatingPieceView.getLocationOnScreen(floatingPieceLocation);
        Log.d(TAG, "Before setting: Location floating piece: x-Location " + floatingPieceLocation[0] + " y-location " + floatingPieceLocation[1]);

   *//*     Rect drawablePadding = new Rect();
        destinationCell.getBackground().getPadding(drawablePadding);
        offsetLeft = drawablePadding.left;
        offsetRight = drawablePadding.right;
        offsetTop = drawablePadding.top;
        offsetBottom = drawablePadding.bottom;*//*



     //   Log.d(TAG, "offsetLeft " + offsetLeft + " offsetTop " + offsetTop);
        params.leftMargin = destinationCellLocation[0]; //+ offsetLeft;
        params.topMargin = destinationCellLocation[1];// + offsetTop;
        params.gravity = Gravity.LEFT + Gravity.TOP;

        int width = destinationCell.getWidth() ;    //- (offsetLeft + offsetRight);
        int height = destinationCell.getHeight();  // - (offsetTop + offsetBottom);

        params.width = width;
        params.height = height;
        floatingPieceView.setLayoutParams(params);
      //  destinationCell.setVisibility(View.INVISIBLE);
        floatingPieceView.setVisibility(View.VISIBLE);
        int[]newFloatingPieceLocation = new int[2];
        floatingPieceView.getLocationOnScreen(newFloatingPieceLocation);
        Log.d(TAG, "after setting: Location floating piece: x-Location " + newFloatingPieceLocation[0] + " y-location " + newFloatingPieceLocation[1]);
        floatingPieceView.setImageDrawable(fieldHighlightedDrawable);
    }*/


    private void initGame() {
        for (int i = 1; i <= 49; ++i) {
            occupiedField[i] = 0;
            avail[i] = (i % 7 + 1); // sortierte liste von steinen
            // 2,3,4,5,6,7,1,2,3,4 etc. bis 49
        }
        occupiedField[0] = 0; // naechster Stein im sack
        numberOfPiecesInSack = 49;
        testSetup();
        //   shuffle();
        //       for (int j = 1; j <= 7; j++) {
        //           rowscore[j] = 0;
        //           colscore[j] = 0;
        //       }

        resetImageViewsClicked();
        initializeFirstPiece();

    }

    private void testSetup() {
        for (int i = 1; i <= 49; ++i) {
            occupiedField[i] = 0;
            avail[i] = (2); // sortierte liste von steinen
            // 2,3,4,5,6,7,1,2,3,4 etc. bis 49
        }
    }

    private void initializeFirstPiece() {
        int firstCell = getRandomFirstCell();
        numberOfPiecesInSack -= 1;
        occupiedField[0] = avail[(numberOfPiecesInSack + 1)];
        colValActualPieceInSack = occupiedField[firstCell] = occupiedField[0];


    }


    public void shuffle() {
        for (int l = 1; l < 10000; ++l) {
            int i = iRand(1, numberOfPiecesInSack);
            int j = iRand(1, numberOfPiecesInSack);
            int k = avail[i];
            avail[i] = avail[j];
            avail[j] = k;
        }
    }

    public int iRand(int paramInt1, int paramInt2) {
        double d = Math.random() * (paramInt2 - paramInt1 + 1) + paramInt1;
        int i;
        if (d < 0.0D)
            i = (int) Math.ceil(d);
        else
            i = (int) Math.floor(d);
        return i;
    }

    public void newPiece() {
        numberOfPiecesInSack -= 1;
        colValActualPieceInSack = occupiedField[0] = avail[(numberOfPiecesInSack + 1)];

        if (numberOfPiecesInSack == 0) {
            Log.d("No piece available", " round ends");
            Toast.makeText(this, "no pieces anymore", Toast.LENGTH_SHORT).show();
            //endRound();
        }

        //setting occ[0] to calculated best field
        int bestField = calculateBestField();
        //
        //  occupiedField[calculatedBestField - 1] = temp_occ0_var;
        // Log.d(TAG, "next piece " + occupiedField[0]);

        Log.d(TAG, "BestField " + bestField);

        //  setPieceDrawable(bestField - 1);

        scoreView.setText(String.valueOf(getTotalScore()));


    }

    private int calculateBestField() {

        colValActualPieceInSack = occupiedField[0];// occupiedField[0] is current piece in
        // Sack

        dsbest = 1000.0D;
        tempScore = getTotalScore();

        boolean bestFieldFound = false;
        for (int j = 1; j <= 49; j++) {

            nextFieldInArray = CELLSEQ[j];
            if (occupiedField[nextFieldInArray] == 0) {
                occupiedField[nextFieldInArray] = occupiedField[0];
                boolean isMiniMaxable = miniMax();
                if (isMiniMaxable == true) {
                    bestFieldFound = true;
                    calculatedBestField = nextFieldInArray;
                }
                occupiedField[nextFieldInArray] = 0;
            }
            if (dsbest < 1.0E-006D) {
                break;
            }
        }
        occupiedField[0] = 0;
        // bestfieldfound =true
        if (bestFieldFound) {
            occupiedField[calculatedBestField] = colValActualPieceInSack;
        }
        // no best field found, select any empty field
        else {
            for (int j = 1; j <= 49; j++) {
                calculatedBestField = CELLSEQ[j];
                if (occupiedField[calculatedBestField] == 0) {
                    occupiedField[calculatedBestField] = colValActualPieceInSack;
                    break;
                }
            }
        }

        return calculatedBestField;
    }


    private Drawable getPieceDrawable(int cellColor) {

        Log.d(TAG, "getting floatingpiece color " + cellColor);
        switch (cellColor) {
            case RED:
                return redCircleDrawable;
            case BLACK:
                return blackCircleDrawable;
            case BLUE:
                return blueCircleDrawable;
            case GREEN:
                return greenCircleDrawable;
            case YELLOW:
                return yellowCircleDrawable;
            case WHITE:
                return whiteCircleDrawable;
            case PURPLE:
                return purpleCircleDrawable;
            default:
                Log.e(TAG, "NO PIECE DRAWABLE FOUND , returning default circle");
                return blueCircleDrawable;
        }

    }


    public int getTotalScore() {
        int j = 0;
        for (int i = 1; i <= 7; i++) {
            j = j + scoreRow(i) + scoreCol(i);
        }
        return j;
    }

    public int scoreRow(int rowNr) {

        int rowStartCell = getLeftBorderForRow(rowNr);

        int twosCount = 0;
        int threesCount = 0;
        int foursCount = 0;
        int fivesCount = 0;
        int sixesCount = 0;
        int sevensCount = 0;

        int twosIndex = 0;
        int threesIndex = 0;
        int foursIndex = 0;
        int fivesIndex = 0;
        int sixesIndex = 0;

        for (int cellIndex = 1; cellIndex <= OFFSET_END_OF_ROW; cellIndex++) {
            if ((occupiedField[(rowStartCell + cellIndex - 1)] != 0)
                    && (occupiedField[(rowStartCell + cellIndex - 1)] == occupiedField[(rowStartCell + cellIndex)])) {
                twosCount++;
                twosIndex = twosCount;
                twos[twosIndex] = (rowStartCell + cellIndex - 1);
            }

        }

        for (int cellIndex = 1; cellIndex <= 5; cellIndex++)
            if ((occupiedField[(rowStartCell + cellIndex - 1)] != 0)
                    && (occupiedField[rowStartCell + cellIndex] != 0) // cell
                    // inbetween
                    // must
                    // not
                    // be
                    // empty
                    && (occupiedField[(rowStartCell + cellIndex - 1)] == occupiedField[(rowStartCell
                    + cellIndex + 1)])) {
                threesCount++;
                threesIndex = threesCount;
                threes[threesIndex] = (rowStartCell + cellIndex - 1);
            }

        int j; // startfield of a pattern
        for (int cellIndex = 1; cellIndex <= twosCount; cellIndex++) {
            j = twos[cellIndex];
            if ((j > rowStartCell) && (j < rowStartCell + 5)
                    && (occupiedField[(j - 1)] != 0)
                    && (occupiedField[(j - 1)] == occupiedField[(j + 2)])) {
                foursCount++;
                foursIndex = foursCount;
                fours[foursIndex] = (j - 1);
            }

        }

        for (int cellIndex = 1; cellIndex <= threesCount; cellIndex++) {
            j = threes[cellIndex];
            if ((j > rowStartCell) && (j < rowStartCell + 4)
                    && (occupiedField[(j - 1)] != 0)
                    && (occupiedField[(j - 1)] == occupiedField[(j + 3)])) {
                fivesCount++;
                fivesIndex = fivesCount;
                fives[fivesIndex] = (j - 1);
            }

        }

        for (int cellIndex = 1; cellIndex <= foursCount; cellIndex++) {
            j = fours[cellIndex];
            if ((j > rowStartCell) && (j < rowStartCell + 3)
                    && (occupiedField[(j - 1)] != 0)
                    && (occupiedField[(j - 1)] == occupiedField[(j + 4)])) {
                sixesCount++;
                sixesIndex = sixesCount;
                sixes[sixesIndex] = (j - 1);
            }

        }

        for (int cellIndex = 1; cellIndex <= fivesCount; cellIndex++) {
            j = fives[cellIndex];
            if ((j > rowStartCell) && (j < rowStartCell + 2)
                    && (occupiedField[(j - 1)] != 0)
                    && (occupiedField[(j - 1)] == occupiedField[(j + 5)])) {
                sevensCount++;
            }
        }

        return 2 * twosCount + 3 * threesCount + 4 * foursCount + 5
                * fivesCount + 6 * sixesCount + 7 * sevensCount;
    }

    public int scoreCol(int colNr) {

        int cellIndex = 0;

        int twosCount = 0;
        int threesCount = 0;
        int foursCount = 0;
        int fivesCount = 0;
        int sixesCount = 0;
        int sevensCount = 0;

        int twosIndex = 0;
        int threesIndex = 0;
        int foursIndex = 0;
        int fivesIndex = 0;
        int sixesIndex = 0;

        for (cellIndex = 7; cellIndex <= 42; cellIndex += 7) {
            if ((occupiedField[(colNr + cellIndex - 7)] != 0)
                    && (occupiedField[(colNr + cellIndex - 7)] == occupiedField[(colNr + cellIndex)])) {
                twosCount++;
                twosIndex = twosCount;
                twos[twosIndex] = (colNr + cellIndex - 7);
            }

        }

        for (cellIndex = 7; cellIndex <= 35; cellIndex += 7) {
            if ((occupiedField[(colNr + cellIndex - 7)] != 0)
                    && (occupiedField[(colNr + cellIndex)] != 0)
                    && (occupiedField[(colNr + cellIndex - 7)] == occupiedField[(colNr
                    + cellIndex + 7)])) {
                threesCount++;
                threesIndex = threesCount;
                threes[threesIndex] = (colNr + cellIndex - 7);
            }
        }

        int j;

        for (cellIndex = 1; cellIndex <= twosCount; cellIndex++) {
            j = twos[cellIndex];
            if ((j > colNr) && (j < colNr + 35)
                    && (occupiedField[(j - 7)] != 0)
                    && (occupiedField[(j - 7)] == occupiedField[(j + 14)])) {
                foursCount++;
                foursIndex = foursCount;
                fours[foursIndex] = (j - 7);
            }

        }

        for (cellIndex = 1; cellIndex <= threesCount; cellIndex++) {
            j = threes[cellIndex];
            if ((j > colNr) && (j < colNr + 28)
                    && (occupiedField[(j - 7)] != 0)
                    && (occupiedField[(j - 7)] == occupiedField[(j + 21)])) {
                fivesCount++;
                fivesIndex = fivesCount;
                fives[fivesIndex] = (j - 7);
            }

        }

        for (cellIndex = 1; cellIndex <= foursCount; cellIndex++) {
            j = fours[cellIndex];
            if ((j > colNr) && (j < colNr + 21)
                    && (occupiedField[(j - 7)] != 0)
                    && (occupiedField[(j - 7)] == occupiedField[(j + 28)])) {
                sixesCount++;
                sixesIndex = sixesCount;
                sixes[sixesIndex] = (j - 7);
            }

        }

        for (cellIndex = 1; cellIndex <= fivesCount; cellIndex++) {
            j = fives[cellIndex];
            if ((j > colNr) && (j < colNr + 14)
                    && (occupiedField[(j - 7)] != 0)
                    && (occupiedField[(j - 7)] == occupiedField[(j + 35)])) {
                sevensCount++;
            }
        }

        return 2 * twosCount + 3 * threesCount + 4 * foursCount + 5
                * fivesCount + 6 * sixesCount + 7 * sevensCount;
    }

    public boolean miniMax() {
        boolean isBest = false;
        double oldBest = 0.0D;
        sourceField = nextFieldInArray;
        destinationField = sourceField;

        double currentBest = calculateScore();
        if (currentBest > dsbest) // dsbest starts at 1000.0d
            return false;
        if (currentBest > oldBest) //&& uperLimit<dsBest
            oldBest = currentBest;

        // move pieces on board and check their score
        for (int i = 1; i <= 49; i++) {
            sourceField = CELLSEQ[i];

            // is field not empty
            // check north movement
            if (occupiedField[sourceField] != 0) {
                if (sourceField > 7) {
                    for (destinationField = (sourceField - 7); destinationField > 0; destinationField -= 7) {
                        if (occupiedField[destinationField] != 0)
                            break;
                        currentBest = calculateScore();
                        if (currentBest > dsbest)
                            return false;
                        if (currentBest > oldBest)
                            oldBest = currentBest;

                    }

                }
                // check east
                if (sourceField % 7 != 0) {
                    for (destinationField = (sourceField + 1); destinationField % 7 != 1; destinationField += 1) {
                        if (occupiedField[destinationField] != 0)
                            break;
                        currentBest = calculateScore();
                        if (currentBest > dsbest)
                            return false;
                        if (currentBest > oldBest)
                            oldBest = currentBest;

                    }

                }
                // check south
                if (sourceField <= 42) {
                    for (destinationField = (sourceField + 7); destinationField < 50; destinationField += 7) {
                        if (occupiedField[destinationField] != 0)
                            break;
                        currentBest = calculateScore();
                        if (currentBest > dsbest)
                            return false;
                        if (currentBest > oldBest)
                            oldBest = currentBest;

                    }

                }
                // check west
                if (sourceField % 7 != 1) {
                    for (destinationField = (sourceField - 1); destinationField % 7 != 0; destinationField -= 1) {
                        if (occupiedField[destinationField] != 0)
                            break;
                        currentBest = calculateScore();
                        if (currentBest > dsbest)
                            return false;
                        if (currentBest > oldBest)
                            oldBest = currentBest;
                    }

                }

            }

        }

        //overwrite dsbest
        if (oldBest < dsbest) {
            isBest = true;
            dsbest = oldBest;
        }

        return isBest;
    }

    public double calculateScore() {

        copyPieceColor();

        double dScore = getTotalScore() - tempScore;
        int columnNr;
        int firstCellInRow;
        int upperCellLimit;
        int sameColorCount;
        int cellIndex;

        // horizontal move
        // source and destination are in same row
        // traverse column of destination field

        if (Math.abs(destinationField - sourceField) < 7) {
            columnNr = (destinationField - 1) % 7 + 1;
            upperCellLimit = columnNr + 42;
            sameColorCount = 0;
            // check for identical colors in column of destination field
            for (cellIndex = columnNr; cellIndex <= upperCellLimit; cellIndex += 7) {
                if ((cellIndex != destinationField)
                        && (occupiedField[cellIndex] == occupiedField[destinationField]))
                    sameColorCount++;
            }

            if (sameColorCount > 0)
                dScore = dScore + 0.25D * sameColorCount - 0.001D;
        }
        // vertical move source and destination are in the same column
        // traverse row of destination field
        else {
            firstCellInRow = 7 * ((destinationField - 1) / 7) + 1;
            upperCellLimit = firstCellInRow + 6;
            sameColorCount = 0;
            for (cellIndex = firstCellInRow; cellIndex <= upperCellLimit; cellIndex += 1) {
                if ((cellIndex != destinationField)
                        && (occupiedField[cellIndex] == occupiedField[destinationField]))
                    sameColorCount++;
            }

            if (sameColorCount > 0)
                dScore = dScore + 0.25D * sameColorCount - 0.001D;

        }

        undoCopyPieceColor();

        return dScore;
    }

    private void undoCopyPieceColor() {
        if (sourceField != destinationField) {
            occupiedField[sourceField] = occupiedField[destinationField];
            occupiedField[destinationField] = 0;
        }
    }

    private void copyPieceColor() {
        // copy piece color from original field to destination field
        if (sourceField != destinationField) {
            occupiedField[destinationField] = occupiedField[sourceField];
            occupiedField[sourceField] = 0;
        }
    }

    private void startDemoRound() {
        occupiedField[3] = RED; // 1=red
        occupiedField[5] = RED;
        occupiedField[9] = BLACK; // 4=black
        occupiedField[10] = GREEN; // 2 green
        occupiedField[12] = YELLOW; // 5=yellow
        occupiedField[14] = PURPLE; // 6= orange
        occupiedField[15] = BLUE; // 3=blue
        occupiedField[21] = GREEN;
        occupiedField[29] = BLUE;
        occupiedField[30] = BLACK;
        occupiedField[31] = BLACK;
        occupiedField[34] = BLUE;
        occupiedField[35] = GREEN;
        occupiedField[42] = PURPLE;
        occupiedField[45] = YELLOW;
        occupiedField[47] = YELLOW;
        occupiedField[49] = YELLOW;

        numberOfPiecesInSack = 32;

        newPiece();

        colValActualPieceInSack = occupiedField[0];// occupiedField[0] is current piece in
        // Sack

        dsbest = 1000.0D;
        tempScore = getTotalScore();

        boolean bestFieldFound = false;
        for (int j = 1; j <= 49; j++) {
            nextFieldInArray = CELLSEQ[j];
            if (occupiedField[nextFieldInArray] == 0) {
                occupiedField[nextFieldInArray] = occupiedField[0];
                boolean isMiniMaxable = miniMax();
                if (isMiniMaxable == true) {
                    bestFieldFound = true;
                    calculatedBestField = nextFieldInArray;
                }
                occupiedField[nextFieldInArray] = 0;
            }
            if (dsbest < 1.0E-006D) {
                break;
            }
        }
        occupiedField[0] = 0;
        // bestfieldfound =true
        if (bestFieldFound) {
            occupiedField[calculatedBestField] = colValActualPieceInSack;
        }
        // no best field found, select any empty field
        else {
            for (int j = 1; j <= 49; j++) {
                calculatedBestField = CELLSEQ[j];
                if (occupiedField[calculatedBestField] == 0) {
                    occupiedField[calculatedBestField] = colValActualPieceInSack;
                    break;
                }
            }
        }
    }

    private void getLegalMoves(int clickedCell) {
        int field = clickedCell;
        legalfields = new ArrayList<Integer>();
        int boardSize = NUMBER_OF_COLUMNS * NUMBER_OF_ROWS;

        if (occupiedField[field] != 0) {
            //check North
            if (field > NUMBER_OF_COLUMNS) {
                for (int i = field - NUMBER_OF_COLUMNS; i > 0; i -= NUMBER_OF_COLUMNS) {
                    if (occupiedField[i] == 0) {
                        legalfields.add(i);
                    } else break;
                }
            }
            //check east
            if (field % NUMBER_OF_COLUMNS != 0) {
                for (int i = field + 1; i % 7 != 1; i++) {
                    if (occupiedField[i] == 0) {
                        legalfields.add(i);
                    } else break;
                }
            }

            //check south
            if (field <= boardSize - NUMBER_OF_COLUMNS) {
                for (int i = field + NUMBER_OF_ROWS; i <= boardSize; i += 7) {
                    if (occupiedField[i] == 0) {
                        legalfields.add(i);
                    } else break;
                }
            }

            //check west
            if (field % 7 != 1) {
                for (int i = field - 1; i % 7 != 0; i -= 1) {
                    if (occupiedField[i] == 0) {
                        legalfields.add(i);
                    } else break;
                }
            }
        }
        for (Integer i : legalfields) {
            imageList.get(i - 1).setBackground(fieldHighlightedDrawable);
            // Log.d(TAG, "legal Fields: " + i.toString());
        }
    }

    private void hideLegalMoves() {
        if (legalfields.size() > 0) {
            for (Integer i : legalfields) {
                imageList.get(i - 1).setBackground(borderDrawable
                );
                Log.d(TAG, "legal Fields: " + i.toString());
            }
        }

    }

    public boolean isSlideOK(int paramInt1, int paramInt2) {
        if (paramInt1 == paramInt2) {
            if (occupiedField[paramInt1] != 0) return true;
        } else {
            int i;
            if ((paramInt1 - 1) / 7 == (paramInt2 - 1) / 7) {
                if (paramInt1 < paramInt2) {
                    for (i = paramInt1 + 1; i <= paramInt2; i++) {
                        if (occupiedField[i] != 0) return false;
                    }
                    return true;
                }

                for (i = paramInt1 - 1; i >= paramInt2; i--) {
                    if (occupiedField[i] != 0) return false;
                }
                return true;
            }

            if (paramInt1 % 7 == paramInt2 % 7) {
                if (paramInt1 < paramInt2) {
                    for (i = paramInt1 + 7; i <= paramInt2; i += 7) {
                        if (occupiedField[i] != 0) return false;
                    }
                    return true;
                }

                for (i = paramInt1 - 7; i >= paramInt2; i -= 7) {
                    if (occupiedField[i] != 0) return false;
                }
                return true;
            }

        }

        return false;
    }


    ViewTreeObserver.OnPreDrawListener mPreDrawListener =
            new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    gameContainer.getViewTreeObserver().removeOnPreDrawListener(this);
                    // int[] location = new int[2];
                    // imageViewNext.getLocationOnScreen(location);
                    // Log.d(TAG, "onPreDraw called, imageViewNextLocation " + location[0] + " , " + location[1]);
                /*    locationImageViewNext[0] = location[0];
                    locationImageViewNext[1] = location[1];*/
                    //Log.d(TAG, "onPreDraw called, imageViewNextLocation " + location[0] + " , " + location[1]);
                    //Log.d(TAG, "onPreDraw called, locationArray filled with values " + locationImageViewNext[0] + " , " + locationImageViewNext[1]);

                    floatingPieceView.getLocationOnScreen(startLocationFloatingPiece);
                    Log.d(TAG, "onPreDraw called, startLocationFloatingPiece " + startLocationFloatingPiece[0] + " , " + startLocationFloatingPiece[1]);
                    Log.d(TAG, "randomFirstCell, start animation to cell " + getRandomFirstCell());
                    startNextPieceAnimation(getRandomFirstCell() - 1);

                    return false;
                }
            };

    public int getCalculatedBestField() {
        return calculatedBestField;
    }

    private void startNextPieceAnimation(int destinationField) {
        int[] locationDestinationCell = new int[2];
        destinationView = imageList.get(destinationField);

        destinationView.getLocationOnScreen(locationDestinationCell);
        Log.d(TAG, "Location of destination field " + locationDestinationCell[0] + ", " + locationDestinationCell[1]);
        int deltaX = startLocationFloatingPiece[0] - locationDestinationCell[0];
        int deltaY = startLocationFloatingPiece[1] - locationDestinationCell[1];
        Log.d(TAG, "delta between start and destination field " + deltaX + " , " + deltaY);

        floatingPieceView.setScaleX(1);
        floatingPieceView.setScaleY(1);
        floatingPieceView.setImageDrawable(getPieceDrawable(getColValActualPieceInSack()));
        floatingPieceView.setVisibility(View.VISIBLE);

        PropertyValuesHolder pvhSX =
                PropertyValuesHolder.ofFloat(View.SCALE_X, 3);
        PropertyValuesHolder.ofFloat(View.SCALE_X, 3);
        PropertyValuesHolder pvhSY =
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 3);
        ObjectAnimator bounceAnim = ObjectAnimator.ofPropertyValuesHolder(
                floatingPieceView, pvhSX, pvhSY);
        bounceAnim.setRepeatCount(1);
        bounceAnim.setRepeatMode(ValueAnimator.REVERSE);
        bounceAnim.setInterpolator(decelerateInterpolator);
        bounceAnim.setDuration(2000);
        bounceAnim.setStartDelay(1000);

        Log.d(TAG, "moving x by" + "-" + deltaX + "an y by " + deltaY);
        float scale = this.getResources().getDisplayMetrics().density;
        PropertyValuesHolder pvhTX =
                PropertyValuesHolder.ofFloat(View.TRANSLATION_X, -deltaX);
        PropertyValuesHolder pvhTY =
                PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, -deltaY);
        ObjectAnimator moveAnim = ObjectAnimator.ofPropertyValuesHolder(
                floatingPieceView, pvhTX, pvhTY);
        moveAnim.setDuration(2000);
        moveAnim.setStartDelay(1000);
        moveAnim.start();
        bounceAnim.start();


        bounceAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                floatingPieceView.animate().setDuration(300).scaleX(.7f).scaleY(.7f).
                        setInterpolator(decelerateInterpolator).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        floatingPieceView.animate().setDuration(300).scaleX(1).scaleY(1).setInterpolator(overshootInterpolator).withEndAction(new Runnable() {
                            @Override
                            public void run() {

                                floatingPieceView.setVisibility(View.INVISIBLE);
                                Log.d(TAG, " floatingpieceview set invisible ");
                                destinationView.setImageDrawable(getPieceDrawable(getColValActualPieceInSack()));
                                Log.d(TAG, " destinationcell drawable is now set ");
                                isPlayersTurn = true;
                                Log.d(TAG, " flag isPlayersturn set to " + isPlayersTurn);
                            }
                        });

                    }
                });
            }
        });

    }

    public int getRandomFirstCell() {
        if (randomFirstCell == 0) {
            randomFirstCell = iRand(1, 49);
        }
        return randomFirstCell;
    }

    public int[] getStartLocationFloatingPiece() {
        return startLocationFloatingPiece;
    }


    public int getColValActualPieceInSack() {
        return colValActualPieceInSack;
    }


    public int[] getHorChainSize(int field) {
        int[] chainCoordinates = new int[3]; //[0]=leftIndex, [1] rightIndex, [2] chainsize
        int horChainSize = 1;
        int leftChainIndex = field;
        int rightChainIndex = field;
        int rowNr = calcRowForField(field);
        if (rowNr == -1) {
            Log.e(TAG, "Error no ROW found for field");
            //  Toast.makeText(this, "No row found for field", Toast.LENGTH_LONG).show();
        } else {
            Log.d(TAG, "row found for field " + field + " " + rowNr);
        }
        // neighbor
        int leftBorder = getLeftBorderForRow(rowNr);
        int rightBorder = getRightBorderForRow(rowNr);

        for (int cellIndex = field; cellIndex > leftBorder; cellIndex--) {
            if ((occupiedField[(cellIndex - 1)] != 0)) {
                horChainSize++;
                leftChainIndex = cellIndex - 1;
            } else {
                break;
            }
        }

        for (int cellIndex = field; cellIndex < rightBorder; cellIndex++) {
            if ((occupiedField[(cellIndex + 1)] != 0)) {
                horChainSize++;
                rightChainIndex = cellIndex + 1;
            } else {
                break;
            }
        }

        chainCoordinates[0] = leftChainIndex;
        chainCoordinates[1] = rightChainIndex;
        chainCoordinates[2] = horChainSize;


        return chainCoordinates;
    }

    private int calcRowForField(int field) {
        if (field % NUMBER_OF_ROWS == 0)
            return field / NUMBER_OF_ROWS;
        else return
                (field / NUMBER_OF_ROWS) + 1;
    }


}

