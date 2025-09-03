package fr.finkit.Piece;

import fr.finkit.core.Board;

import java.util.List;

public class Bishop extends Piece {
    public Bishop(PieceColor pieceColor) {
        super(pieceColor);
        this.img = "Bishop.png";
    }

    @Override
    public List<Position> getLegalMoves(Board board, Position position) {
        return List.of();
    }

}
