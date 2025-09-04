package fr.finkit.Piece;

import fr.finkit.core.Board;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    public Knight(PieceColor pieceColor) {
        super(pieceColor);
        this.img = "Knight.png";
    }

    @Override
    public List<Position> getLegalMoves(Board board, Position pos) {
        List<Position> moves = new ArrayList<>();
        List<Position> legalMoves = new ArrayList<>();

        moves.add(new Position(pos.x()+2, pos.y()+1));
        moves.add(new Position(pos.x()+2, pos.y()-1));

        moves.add(new Position(pos.x()-2, pos.y()+1));
        moves.add(new Position(pos.x()-2, pos.y()-1));

        moves.add(new Position(pos.x()+1, pos.y()+2));
        moves.add(new Position(pos.x()+1, pos.y()-2));

        moves.add(new Position(pos.x()-1, pos.y()+2));
        moves.add(new Position(pos.x()-1, pos.y()-2));

        for (Position p : moves) {
            if(p.x() <= 7 &&  p.y() <= 7 && p.x() >= 0 &&  p.y() >= 0)
                if(board.getPiece(p) == null || board.getPiece(p).getColor() != pieceColor) legalMoves.add(p);

        }
        return legalMoves;
    }

}
