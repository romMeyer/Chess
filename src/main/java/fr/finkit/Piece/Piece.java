package fr.finkit.Piece;

import fr.finkit.core.Board;

import java.util.List;

public abstract class Piece {
    protected Color color;
    protected String img;

    public Piece(Color color) {
        this.color = color;
    }

    public abstract List<Position> getLegalMoves(Board board, Position position);

    public String getImage() {
        return img;
    }

    public Color getColor() {
        return color;
    }
}
