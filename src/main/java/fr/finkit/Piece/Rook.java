package fr.finkit.Piece;

import fr.finkit.core.Board;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    public Rook(PieceColor pieceColor) {
        super(pieceColor);
        this.img = "Rook.png";
    }

    @Override
    public List<Position> getLegalMoves(Board board, Position pos) {
        List<Position> legalMoves = new ArrayList<>();
        int x, y;

        // Ligne bas
        for (x = pos.x()+1 ; x <= 7; x++) {
            if (vertical(board, pos, legalMoves, x)) break;
        }

        // Ligne haut
        for (x = pos.x()-1 ; x >= 0; x--) {
            if (vertical(board, pos, legalMoves, x)) break;
        }

        // Ligne droite
        for (y = pos.y()+1 ; y <= 7; y++) {
            if (horizontal(board, pos, legalMoves, y)) break;
        }

        // Ligne gauche
        for (y = pos.y()-1 ; y >= 0; y--) {
            if (horizontal(board, pos, legalMoves, y)) break;
        }

        return legalMoves;
    }

    private boolean vertical(Board board, Position pos, List<Position> legalMoves, int x) {
        Position p;
        Piece piece;
        p = new Position(x, pos.y());
        piece = board.getPiece(p);
        if (piece == null) {
            legalMoves.add(p);
        } else {
            if (piece.getColor() != pieceColor) legalMoves.add(p);
            return true;
        }
        return false;
    }

    private boolean horizontal(Board board, Position pos, List<Position> legalMoves, int y) {
        Position p;
        Piece piece;
        p = new Position(pos.x(), y);
        piece = board.getPiece(p);
        if (piece == null) {
            legalMoves.add(p);
        } else {
            if (piece.getColor() != pieceColor) legalMoves.add(p);
            return true;
        }
        return false;
    }

}
