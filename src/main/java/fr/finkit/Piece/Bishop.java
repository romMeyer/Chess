package fr.finkit.Piece;

import fr.finkit.core.Board;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    public Bishop(PieceColor pieceColor) {
        super(pieceColor);
        this.img = "Bishop.png";
    }

    @Override
    public List<Position> getLegalMoves(Board board, Position pos) {
        List<Position> moves = new ArrayList<>();
        List<Position> legalMoves = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            moves.add(new Position(pos.x()+i, pos.y()+i));
            moves.add(new Position(pos.x()-i, pos.y()+i));
            moves.add(new Position(pos.x()+i, pos.y()-i));
            moves.add(new Position(pos.x()-i, pos.y()-i));
        }
        int x, y;
        for (Position p : moves) {
            x = p.x();
            y = p.y();
            if (( x <= 7 && x >= 0 ) && (y <= 7 && y >= 0))
                if (board.getPiece(p) == null || board.getPiece(p).getColor() != pieceColor)
                    legalMoves.add(p);
        }

        return legalMoves;
    }

}
