package fr.finkit.Piece;

import fr.finkit.core.Board;

import java.util.List;

public class Queen extends Piece {
    public Queen(PieceColor pieceColor) {
        super(pieceColor);
        this.img = "Queen.png";
    }

    @Override
    public List<Position> getLegalMoves(Board board, Position position) {
        return List.of();
    }

}
