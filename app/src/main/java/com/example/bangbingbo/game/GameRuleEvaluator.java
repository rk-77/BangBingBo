package com.example.bangbingbo.game;

import com.example.bangbingbo.data.chain.Chain;
import com.example.bangbingbo.data.chain.HorizontalChain;
import com.example.bangbingbo.data.score.Score;
import com.example.bangbingbo.data.score.ScoreBoardTypeBig;
import com.example.bangbingbo.data.chain.VerticalChain;
import com.example.bangbingbo.data.score.ScoreBoardTypeNormal;
import com.example.bangbingbo.game.enums.PieceClickStatusEvaluated;
import com.example.bangbingbo.game.listeners.commandexecutors.gamelogic.CommandExecutor;

import java.util.HashMap;

public class GameRuleEvaluator {

    public static int NUMBER_OF_ROWS, NUMBER_OF_COLUMNS;

    private static GameRuleEvaluator gameRuleEvaluator;
    private static GameStatus gameStatus;
    int piece1Clicked;
    int piece2Clicked;
    HashMap<PieceClickStatusEvaluated, CommandExecutor> commandExecutors;

    private GameRuleEvaluator(GameBoardManager.BoardType boardType) {
        gameStatus = GameStatus.getInstanceForType(boardType);
        NUMBER_OF_ROWS = NUMBER_OF_COLUMNS = gameStatus.getBoardLength();
    }

    public static synchronized GameRuleEvaluator getInstanceForType(GameBoardManager.BoardType boardType) {
        if (gameRuleEvaluator == null) {
            return gameRuleEvaluator = new GameRuleEvaluator(boardType);
        }
        if (gameStatus.boardType.equals(boardType)) {
            return gameRuleEvaluator;
        }
        throw new RuntimeException("Invalid Boardtype");
    }

    public int getPiece1Clicked() {
        return piece1Clicked;
    }

    public int getPiece2Clicked() {
        return piece2Clicked;
    }

    public PieceClickStatusEvaluated EvaluateClickStatus(GamePiece piece) {
        PieceClickStatusEvaluated statusEvaluated;
        switch (piece.status) {
            case CLICKED_SOURCE:
                statusEvaluated = evaluateFirstClick(piece.clickIndex);
                piece1Clicked = statusEvaluated.equals(PieceClickStatusEvaluated.FIRST_CLICK_LEGAL) ? piece.clickIndex : piece1Clicked;
                return statusEvaluated;
            case CLICKED_DESTINATION:
                statusEvaluated = evaluateSecondClick(piece1Clicked, piece.clickIndex);
                piece2Clicked = statusEvaluated.equals(PieceClickStatusEvaluated.SECOND_CLICK_LEGAL_MOVE) ? piece.clickIndex : piece2Clicked;
                return statusEvaluated;
        }
        throw new IllegalArgumentException("Illegal status " + piece.status.name());
    }

    private PieceClickStatusEvaluated evaluateFirstClick(int pieceSource) {
        if (gameStatus.getOccupiedFields()[pieceSource] == 0) {
            return PieceClickStatusEvaluated.FIRST_CLICK_ILLEGAL_EMPTY_POSITION;
        }
        return PieceClickStatusEvaluated.FIRST_CLICK_LEGAL;
    }

    private PieceClickStatusEvaluated evaluateSecondClick(int pieceSource, int pieceDestination) {
        if (pieceSource == pieceDestination) {
            return PieceClickStatusEvaluated.SECOND_CLICK_SAME_PIECE_TWICE;
        } else {
            return getSecondClickStatus(pieceSource, pieceDestination);
        }
    }

    private PieceClickStatusEvaluated getSecondClickStatus(int pieceSource, int pieceDestination) {
        if (piecesInEqualRow(pieceSource - 1, pieceDestination - 1)) {
            return isLegalHorizontalMove(pieceSource, pieceDestination) ? PieceClickStatusEvaluated.SECOND_CLICK_LEGAL_MOVE : getSecondClickIllegalStatus(pieceSource, pieceDestination);
        }
        if (piecesInEqualColumn(pieceSource, pieceDestination)) {
            return isLegalVerticalMove(pieceSource, pieceDestination) ? PieceClickStatusEvaluated.SECOND_CLICK_LEGAL_MOVE : getSecondClickIllegalStatus(pieceSource, pieceDestination);
        }
        if (gameStatus.getOccupiedFields()[pieceDestination] != 0) {
            return PieceClickStatusEvaluated.SECOND_CLICK_ILLEGAL_OCCUPIED_PIECE;
        }
        return PieceClickStatusEvaluated.SECOND_CLICK_ILLEGAL_MOVE;
    }

    private PieceClickStatusEvaluated getSecondClickIllegalStatus(int pieceSource, int pieceDestination) {
        return (gameStatus.getOccupiedFields()[pieceDestination] != 0) ? PieceClickStatusEvaluated.SECOND_CLICK_ILLEGAL_OCCUPIED_PIECE : PieceClickStatusEvaluated.SECOND_CLICK_ILLEGAL_MOVE;
    }

    private boolean isLegalVerticalMove(int pieceSource, int pieceDestination) {
        if (isMovingTopToBottom(pieceSource, pieceDestination)) {
            return fieldsInbetweenTopToBottomMoveEmpty(pieceSource, pieceDestination);
        }
        return false;
    }

    private boolean fieldsInbetweenTopToBottomMoveEmpty(int pieceSource, int pieceDestination) {
        for (int i = pieceSource + gameStatus.getBoardLength(); i <= pieceDestination; i += gameStatus.getBoardLength()) {
            if (gameStatus.getOccupiedFields()[i] != 0) return false;
        }
        return true;
    }

    private boolean isMovingTopToBottom(int pieceSource, int pieceDestination) {
        return pieceSource < pieceDestination;
    }


    private boolean isLegalHorizontalMove(int pieceSource, int pieceDestination) {
        if (isMovingLeftToRight(pieceSource, pieceDestination)) {
            return fieldsInbetweenLeftToRightMoveEmpty(pieceSource, pieceDestination);
        } else {
            return fieldsInbetweenRightToLeftMoveEmpty(pieceSource, pieceDestination);
        }
    }

    private boolean fieldsInbetweenLeftToRightMoveEmpty(int pieceSource, int pieceDestination) {
        for (int i = pieceSource + 1; i <= pieceDestination; i++) {
            if (gameStatus.getOccupiedFields()[i] != 0) return false;
        }
        return true;
    }

    private boolean fieldsInbetweenRightToLeftMoveEmpty(int pieceSource, int pieceDestination) {
        for (int i = pieceSource - 1; i >= pieceDestination; i--) {
            if (gameStatus.getOccupiedFields()[i] != 0) return false;
        }
        return true;
    }

    private boolean isMovingLeftToRight(int pieceSource, int pieceDestination) {
        return pieceSource < pieceDestination;
    }

    private boolean piecesInEqualRow(int source, int destination) {
        return (source / gameStatus.getBoardLength()) == (destination / gameStatus.getBoardLength());
    }

    private boolean piecesInEqualColumn(int source, int destination) {
        return (source % gameStatus.getBoardLength()) == (destination % gameStatus.getBoardLength());
    }

    public void calcScore(Chain[] chain, int destinationField, GameBoardManager.BoardType boardType) {
        //  initScore(boardType);
        calcScoreRow((HorizontalChain) chain[0], destinationField, boardType);
        calcScoreColumn((VerticalChain) chain[1], destinationField, boardType);
    }

    private void initScore(GameBoardManager.BoardType boardType) {
        if (boardType.equals(GameBoardManager.BoardType.NORMAL)) {
            ScoreBoardTypeNormal.getInstance().resetScore();
        } else {
            ScoreBoardTypeBig.getInstance().resetScore();
        }
    }

    private void calcScoreRow(HorizontalChain chain, int destinationField, GameBoardManager.BoardType boardType) {
            calcScoreRow(chain, destinationField);
    }

    private void calcScoreRow(HorizontalChain chain, int destinationField) {
        int chainLength = chain.getChainSize();
        switch (chainLength) {
            case 2:
                calcTwosInRow(chain, destinationField);
                break;
            case 3:
                calcThreesInRowCumulated(chain, destinationField);
                break;
            case 4:
                calcFoursInRowCumulated(chain, destinationField);
                break;
            case 5:
                calcFivesInRowCumulated(chain, destinationField);
                break;
            case 6:
                calcSixesInRowCumulated(chain, destinationField);
                break;
            case 7:
                calcSevenInRowCumulated(chain, destinationField);
                break;
        }
    }

    private void calcThreesInRowCumulated(HorizontalChain chain, int destinationField) {
        calcThreesInRow(chain, destinationField);
        calcTwosInRow(chain, destinationField);
    }

    private void calcFoursInRowCumulated(HorizontalChain chain, int destinationField) {
        calcFoursInRow(chain, destinationField);
        calcThreesInRow(chain, destinationField);
    }

    private void calcFivesInRowCumulated(HorizontalChain chain, int destinationField) {
        calcFivesInRow(chain, destinationField);
        calcFoursInRow(chain, destinationField);
    }

    private void calcSixesInRowCumulated(HorizontalChain chain, int destinationfield) {
        calcSixesInRow(chain, destinationfield);
        calcFivesInRow(chain, destinationfield);
    }

    private void calcSevenInRowCumulated(HorizontalChain chain, int destinationField) {
        calcSevenInRow(chain);
        calcSixesInRow(chain, destinationField);
    }


    private void calcTwosInRow(HorizontalChain chain, int destination) {
        int leftIndex = chain.getLeftIndex();
        for (int i = 0; i < chain.getChainSize() - 1; i++) {
            if (isFieldOccupied(leftIndex + i) && isPair(leftIndex + i)
                    && isDestinationPartOfPair(destination, leftIndex + i)) {
                ScoreBoardTypeBig.getInstance().getTwosInRowIndex()[i] = (leftIndex + i);
            }
        }
    }

    private boolean isDestinationPartOfPair(int destination, int index) {
        return (destination == index) || (destination == index + 1);
    }

    private boolean isPair(int field) {
        return gameStatus.getOccupiedFields()[(field)] == gameStatus.getOccupiedFields()[(field + 1)];
    }

    private boolean isFieldOccupied(int field) {
        return gameStatus.getOccupiedFields()[(field)] != 0;
    }

    private void calcThreesInRow(HorizontalChain chain, int destination) {
        int leftIndex = chain.getLeftIndex();
        for (int i = 0; i < chain.getChainSize() - 2; i++) {
            if (isFieldOccupied(leftIndex + i)
                    && isFieldOccupied(leftIndex + i + 1)
                    && isTriplet(leftIndex + i)
                    && isDestinationPartOfTriplet(destination, leftIndex + i)) {
                ScoreBoardTypeBig.getInstance().getThreesInRowIndex()[i] = (leftIndex + i);
            }
        }
    }

    private boolean isDestinationPartOfTriplet(int destination, int index) {
        return destination == (index) || (destination == index + 1) || destination == (index + 2);
    }

    private boolean isTriplet(int index) {
        return gameStatus.getOccupiedFields()[(index)] == gameStatus.getOccupiedFields()[(index + 2)];
    }

    private void calcFoursInRow(HorizontalChain chain, int destination) {
        int leftIndex = chain.getLeftIndex();
        for (int i = 0; i < chain.getChainSize() - 3; i++) {
            int startField = gameStatus.getOccupiedFields()[leftIndex + i];
            int endField = gameStatus.getOccupiedFields()[leftIndex + i + 3];
            int innerfield1 = gameStatus.getOccupiedFields()[leftIndex + i + 1];
            int innerfield2 = gameStatus.getOccupiedFields()[leftIndex + i + 2];
            if (isDestinationPartOfQuadruplet(destination, leftIndex, i) &&
                    isQuadruplet(startField, endField, innerfield1, innerfield2)) {
                ScoreBoardTypeBig.getInstance().getFoursInRowIndex()[i] = (leftIndex + i);
            }
        }
    }

    private boolean isQuadruplet(int startField, int endField, int innerfield1, int innerfield2) {
        return ((startField != 0) && (startField == endField)) &&
                ((innerfield1 != 0) && (innerfield1 == innerfield2));
    }

    private boolean isDestinationPartOfQuadruplet(int destination, int leftIndex, int i) {
        return (destination >= leftIndex + i) && (destination <= leftIndex + i + 3);
    }

    private void calcFivesInRow(HorizontalChain chain, int destination) {
        int leftIndex = chain.getLeftIndex();
        for (int i = 0; i < chain.getChainSize() - 4; i++) {
            if (isDestinationPartOfQuintuplet(destination, leftIndex + i) &&
                    isQuintuplet(leftIndex, i)) {
                ScoreBoardTypeBig.getInstance().getFivesInRowIndex()[i] = (leftIndex + i);
            }
        }
    }

    private boolean isQuintuplet(int leftIndex, int i) {
        int startField = gameStatus.getOccupiedFields()[leftIndex + i];  //most left
        int endField = gameStatus.getOccupiedFields()[leftIndex + i + 4];  //most right
        int innerfield1 = gameStatus.getOccupiedFields()[leftIndex + i + 1]; //inner left
        int innerfield2 = gameStatus.getOccupiedFields()[leftIndex + i + 3];  //inner right
        int centerfield = gameStatus.getOccupiedFields()[leftIndex + i + 2];  //center

        return ((startField != 0) && (startField == endField)) &&
                ((innerfield1 != 0) && (innerfield1 == innerfield2)) && centerfield != 0;
    }

    //Quintuplet = a chain of five pieces
    private boolean isDestinationPartOfQuintuplet(int destination, int leftIndex) {
        return (destination >= leftIndex) && (destination <= leftIndex + 4);
    }

    private void calcSixesInRow(HorizontalChain chain, int destination) {
        int leftIndex = chain.getLeftIndex();
        for (int i = 0; i < chain.getChainSize() - 5; i++) {
            if (isDestinationPartOfSextuplet(destination, leftIndex + i) && isSextuplet(leftIndex, i)) {
                ScoreBoardTypeBig.getInstance().getSixesInRowIndex()[i] = (leftIndex + i);
            }
        }
    }

    private boolean isSextuplet(int leftIndex, int i) {
        int startField = gameStatus.getOccupiedFields()[leftIndex + i];  //most left
        int endField = gameStatus.getOccupiedFields()[leftIndex + i + 5];  //most right
        int innerfield1 = gameStatus.getOccupiedFields()[leftIndex + i + 1]; //inner left
        int innerfield2 = gameStatus.getOccupiedFields()[leftIndex + i + 4];  //inner right
        int centerfield1 = gameStatus.getOccupiedFields()[leftIndex + i + 2];  //left inner center
        int centerfield2 = gameStatus.getOccupiedFields()[leftIndex + i + 3];  //right inner center

        return ((startField != 0) && (startField == endField)) &&
                ((innerfield1 != 0) && (innerfield1 == innerfield2)) &&
                ((centerfield1 != 0) && (centerfield1 == centerfield2));
    }

    private boolean isDestinationPartOfSextuplet(int destination, int leftIndex) {
        return (destination >= leftIndex) && (destination <= leftIndex + 5);
    }

    private void calcSevenInRow(HorizontalChain chain) {
        int leftIndex = chain.getLeftIndex();
        if (isSeptuplet(leftIndex))
            ScoreBoardTypeBig.getInstance().getSevenInRowIndex()[0] = (leftIndex);
    }

    //sept = seven
    private boolean isSeptuplet(int leftIndex) {
        int startField = gameStatus.getOccupiedFields()[leftIndex];  //most left
        int endField = gameStatus.getOccupiedFields()[leftIndex + 6];  //most right
        int innerField1 = gameStatus.getOccupiedFields()[leftIndex + 1]; //inner left
        int innerField2 = gameStatus.getOccupiedFields()[leftIndex + 5];  //inner right
        int mostInnerField1 = gameStatus.getOccupiedFields()[leftIndex + 2];  //most left inner
        int mostInnerField2 = gameStatus.getOccupiedFields()[leftIndex + 4];  //most right inner
        int centerField = gameStatus.getOccupiedFields()[leftIndex + 3];  //center

        return ((startField != 0) && (startField == endField)) &&
                ((innerField1 != 0) && (innerField1 == innerField2)) &&
                ((mostInnerField1 != 0) && (mostInnerField1 == mostInnerField2)) &&
                (centerField != 0);
    }

    private void calcScoreColumn(VerticalChain chain, int destinationField, GameBoardManager.BoardType boardType) {
        if (boardType.equals(GameBoardManager.BoardType.NORMAL)) {
            calcScoreColumnForBoardTypeNormal(chain, destinationField);
        } else {
            calcScoreColumn(chain, destinationField);
        }
    }

    private void setRowScore(int tuple) {
        if (gameStatus.getBoardType().equals(GameBoardManager.BoardType.NORMAL)) {
            setRowScoreForTypeNormal(tuple);
        } else {
            setRowScoreForTypeBig(tuple);
        }
    }

    private void setRowScoreForTypeNormal(int tuple) {
        ScoreBoardTypeNormal scoreBoardTypeNormal = ScoreBoardTypeNormal.getInstance();
    }

    private void setRowScoreForTypeBig(int tuple) {
        ScoreBoardTypeBig scoreBoardTypeBig = ScoreBoardTypeBig.getInstance();
    }

    private void calcScoreColumnForBoardTypeNormal(VerticalChain chain, int destinationField) {
    }

    private void calcScoreColumn(VerticalChain chain, int destinationField) {
    }

    public Chain[] checkPatterns(int destinationField) {
        HorizontalChain horizontalChain = getHorChainSize(destinationField);
        VerticalChain verticalChain = getVertChainSize(destinationField);
        return new Chain[]{horizontalChain, verticalChain};
    }

    private HorizontalChain getHorChainSize(int field) {
        int leftChainIndex = getLeftChainIndex(field);
        int rightChainIndex = getRightChainIndex(field);
        int chainSize = (Math.abs(rightChainIndex - leftChainIndex)) + 1;
        return new HorizontalChain(leftChainIndex, rightChainIndex, chainSize);
    }

    private int getLeftChainIndex(int field) {
        int leftBorder = getLeftBorderForRow(calcRowForField(field));
        int leftChainIndex = field;
        for (int i = field; i > leftBorder; i--) {
            if ((isFieldOccupied(i - 1))) {
                leftChainIndex = i - 1;
            } else {
                break;
            }
        }
        return leftChainIndex;
    }

    private int getRightChainIndex(int field) {
        int rightBorder = getRightBorderForRow(calcRowForField(field));
        int rightChainIndex = field;
        for (int i = field; i < rightBorder; i++) {
            if ((isFieldOccupied(i + 1))) {
                rightChainIndex = i + 1;
            } else {
                break;
            }
        }
        return rightChainIndex;
    }

    private VerticalChain getVertChainSize(int field) {
        int topChainIndex = getTopChainIndex(field);
        int bottomChainIndex = getBottomChainIndex(field);
        int chainSize = (Math.abs(topChainIndex - bottomChainIndex)) + 1;
        return new VerticalChain(topChainIndex, bottomChainIndex, chainSize);
    }

    private int getTopChainIndex(int field) {
        int topBorder = calcColForField(field);
        int topChainIndex = field;

        for (int i = field; i > topBorder; i -= NUMBER_OF_COLUMNS) {
            if ((isFieldOccupied(i - NUMBER_OF_COLUMNS))) {
                topChainIndex = i - NUMBER_OF_COLUMNS;
            } else {
                break;
            }
        }
        return topChainIndex;
    }

    private int getBottomChainIndex(int field) {
        int bottomBorder = (NUMBER_OF_COLUMNS * (NUMBER_OF_COLUMNS - 1)) + calcColForField(field);
        int bottomChainIndex = field;

        for (int i = field; i < bottomBorder; i += NUMBER_OF_COLUMNS) {
            if ((isFieldOccupied(i + NUMBER_OF_COLUMNS))) {
                bottomChainIndex = i + NUMBER_OF_COLUMNS;
            } else {
                break;
            }
        }
        return bottomChainIndex;
    }

    private int calcRowForField(int field) {
        return (field % NUMBER_OF_ROWS == 0) ?
                field / NUMBER_OF_ROWS : (field / NUMBER_OF_ROWS) + 1;
    }

    private int calcColForField(int field) {
        return (field % NUMBER_OF_COLUMNS == 0) ?
                NUMBER_OF_COLUMNS : (field % NUMBER_OF_COLUMNS);
    }

    private int getRightBorderForRow(int rowNr) {
        return NUMBER_OF_ROWS * rowNr;
    }

    private int getLeftBorderForRow(int rowNr) {
        return getRightBorderForRow(rowNr) - (NUMBER_OF_ROWS - 1);
    }
}
