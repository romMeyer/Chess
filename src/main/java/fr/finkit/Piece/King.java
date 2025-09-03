package fr.finkit.Piece;

import fr.finkit.core.Board;

import java.util.List;

public class King extends Piece {
    public King(PieceColor pieceColor) {
        super(pieceColor);
        this.img = "King.png";
    }

    @Override
    public List<Position> getLegalMoves(Board board, Position position) {
        return List.of();
    }

}
