package fr.finkit.Piece;

import java.util.List;

class Pawn extends Piece {
    public Pawn(char color, Position pos, String img) {
        super(color, pos, img);
    }

    @Override
    List<Position> getLegalMoves() {
        return null;
    }
}
