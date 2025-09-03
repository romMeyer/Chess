package fr.finkit.Piece;

import fr.finkit.core.Board;

import java.util.List;

public class Rook extends Piece {
    public Rook(PieceColor pieceColor) {
        super(pieceColor);
        this.img = "Rook.png";
    }

    @Override
    public List<Position> getLegalMoves(Board board, Position position) {
        return List.of();
    }

}
