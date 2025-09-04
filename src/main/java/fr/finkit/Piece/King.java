package fr.finkit.Piece;

import fr.finkit.core.Board;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King(PieceColor pieceColor) {
        super(pieceColor);
        this.img = "King.png";
    }

    @Override
    public List<Position> getLegalMoves(Board board, Position pos) {
        List<Position> moves = new ArrayList<>();
        List<Position> legalMoves = new ArrayList<>();

        moves.add(new Position(pos.x()+1, pos.y()));
        moves.add(new Position(pos.x(), pos.y()+1));
        moves.add(new Position(pos.x()+1, pos.y()+1));

        moves.add(new Position(pos.x()-1, pos.y()));
        moves.add(new Position(pos.x(), pos.y()-1));
        moves.add(new Position(pos.x()-1, pos.y()-1));

        moves.add(new Position(pos.x()-1, pos.y()+1));
        moves.add(new Position(pos.x()+1, pos.y()-1));

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
