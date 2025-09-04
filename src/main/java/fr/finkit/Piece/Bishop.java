package fr.finkit.Piece;

import fr.finkit.core.Board;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    public Bishop(PieceColor pieceColor) {
        super(pieceColor);
        this.img = "Bishop.png";
    }

    @Override
    public List<Position> getLegalMoves(Board board, Position pos) {
        List<Position> moves = new ArrayList<>();
        List<Position> legalMoves = new ArrayList<>();
        int x, y;

        // Diagonale bas-droite
        for (x = pos.x() + 1, y = pos.y() + 1; x <= 7 && y <= 7; x++, y++) {
            Position p = new Position(x, y);
            Piece piece = board.getPiece(p);
            if (piece == null) {
                legalMoves.add(p);
            } else {
                if (piece.getColor() != this.pieceColor) legalMoves.add(p);
                break;
            }
        }

        // Diagonale haut-droite
        for (x = pos.x() + 1, y = pos.y() - 1; x <= 7 && y >= 0; x++, y--) {
            Position p = new Position(x, y);
            Piece piece = board.getPiece(p);
            if (piece == null) {
                legalMoves.add(p);
            } else {
                if (piece.getColor() != this.pieceColor) legalMoves.add(p);
                break;
            }
        }

        // Diagonale bas-gauche
        for (x = pos.x() - 1, y = pos.y() + 1; x >= 0 && y <= 7; x--, y++) {
            Position p = new Position(x, y);
            Piece piece = board.getPiece(p);
            if (piece == null) {
                legalMoves.add(p);
            } else {
                if (piece.getColor() != this.pieceColor) legalMoves.add(p);
                break;
            }
        }

        // Diagonale haut-gauche
        for (x = pos.x() - 1, y = pos.y() - 1; x >= 0 && y >= 0; x--, y--) {
            Position p = new Position(x, y);
            Piece piece = board.getPiece(p);
            if (piece == null) {
                legalMoves.add(p);
            } else {
                if (piece.getColor() != this.pieceColor) legalMoves.add(p);
                break;
            }
        }
        return legalMoves;
    }

}
