package com.example.bangbingbo.game;

import com.example.bangbingbo.game.enums.PieceClickStatusEvaluated;

public class GameRuleEvaluator {

    private static GameRuleEvaluator gameRuleEvaluator;
    private static GameStatus gameStatus;
    int piece1Clicked;
    int piece2Clicked;

    private GameRuleEvaluator(GameBoardManager.BoardType boardType) {
        gameStatus = GameStatus.getInstanceForType(boardType);
    }

    public static synchronized GameRuleEvaluator getInstanceForType(GameBoardManager.BoardType boardType) {
        if (gameRuleEvaluator == null) {
            return gameRuleEvaluator = new GameRuleEvaluator(boardType);
        } else {
            if (gameStatus.boardType.equals(boardType))
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
            default:
                return null;

        }
    }

    private PieceClickStatusEvaluated evaluateFirstClick(int pieceSource) {
        if (gameStatus.getOccupiedField()[pieceSource] == 0) {
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
        if (gameStatus.getOccupiedField()[pieceDestination] != 0) {
            return PieceClickStatusEvaluated.SECOND_CLICK_ILLEGAL_OCCUPIED_PIECE;
        }
        return PieceClickStatusEvaluated.SECOND_CLICK_ILLEGAL_MOVE;
    }

    private PieceClickStatusEvaluated getSecondClickIllegalStatus(int pieceSource, int pieceDestination) {
        return (gameStatus.getOccupiedField()[pieceDestination] != 0) ? PieceClickStatusEvaluated.SECOND_CLICK_ILLEGAL_OCCUPIED_PIECE : PieceClickStatusEvaluated.SECOND_CLICK_ILLEGAL_MOVE;
    }

    private boolean isLegalVerticalMove(int pieceSource, int pieceDestination) {
        if (isMovingTopToBottom(pieceSource, pieceDestination)) {
            return fieldsInbetweenTopToBottomMoveEmpty(pieceSource, pieceDestination);
        }
        return false;
    }

    private boolean fieldsInbetweenTopToBottomMoveEmpty(int pieceSource, int pieceDestination) {
        for (int i = pieceSource + gameStatus.getBoardLength(); i <= pieceDestination; i += gameStatus.getBoardLength()) {
            if (gameStatus.getOccupiedField()[i] != 0) return false;
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
            if (gameStatus.getOccupiedField()[i] != 0) return false;
        }
        return true;
    }

    private boolean fieldsInbetweenRightToLeftMoveEmpty(int pieceSource, int pieceDestination) {
        for (int i = pieceSource - 1; i >= pieceDestination; i--) {
            if (gameStatus.getOccupiedField()[i] != 0) return false;
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
}
