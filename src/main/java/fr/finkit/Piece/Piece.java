package fr.finkit.Piece;

import fr.finkit.core.Board;

import java.util.List;

public abstract class Piece {
    protected PieceColor pieceColor;
    protected String img;
    protected Boolean enPassant;
    protected Boolean moved;


    public Piece(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
        enPassant = false;
        moved = false;
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

    public Boolean getMoved() {
        return moved;
    }

    public void setMoved(Boolean moved) {
        this.moved = moved;
    }

    public Piece copy() {
        // Constructeur identique, avec la couleur et l’état "moved" et "enPassant"
        Piece clone = switch (this.getClass().getSimpleName()) {
            case "Pawn" -> new Pawn(pieceColor);
            case "Rook" -> new Rook(pieceColor);
            case "Knight" -> new Knight(pieceColor);
            case "Bishop" -> new Bishop(pieceColor);
            case "Queen" -> new Queen(pieceColor);
            case "King" -> new King(pieceColor);
            default -> null;
        };
        if (clone != null) {
            clone.setMoved(this.moved);
            clone.setEnPassant(this.enPassant);
        }
        return clone;
    }
}
