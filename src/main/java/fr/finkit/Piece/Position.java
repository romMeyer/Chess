package fr.finkit.Piece;


public record Position(int x, int y) {
    @Override
    public int x() {
        return x;
    }

    @Override
    public int y() {
        return y;
    }
}