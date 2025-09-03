package fr.finkit.Piece;

import fr.finkit.core.Board;

import java.util.List;

public class Knight extends Piece {
    public Knight(PieceColor pieceColor) {
        super(pieceColor);
        this.img = "Knight.png";
    }

    @Override
    public List<Position> getLegalMoves(Board board, Position position) {
        return List.of();
    }

}
