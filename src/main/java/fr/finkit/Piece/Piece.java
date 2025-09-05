package fr.finkit.Piece;

import fr.finkit.core.Board;

import java.util.List;

public abstract class Piece {
    protected PieceColor pieceColor;
    protected String img;
    protected Boolean enPassant;

    public Piece(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
        enPassant = false;
    }

    public abstract List<Position> getLegalMoves(Board board, Position position);

    public String getImage() {
        return img;
    }

    public PieceColor getColor() {
        return pieceColor;
    }

    public Boolean getEnPassant() {
        return enPassant;
    }

    public void setEnPassant(Boolean enPassant) {
        this.enPassant = enPassant;
    }
}
