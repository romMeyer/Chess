package fr.finkit.Piece;

import java.util.List;

abstract class Piece {
    char color;
    Position pos;
    String img;

    public Piece(char color, Position pos, String img) {
        this.color = color;
        this.pos = pos;
        this.img = img;
    }

    abstract List<Position> getLegalMoves();
}
