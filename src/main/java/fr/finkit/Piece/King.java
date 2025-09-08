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
        List<Position> legalMoves;

        moves.add(new Position(pos.x()+1, pos.y()));
        moves.add(new Position(pos.x(), pos.y()+1));
        moves.add(new Position(pos.x()+1, pos.y()+1));

        moves.add(new Position(pos.x()-1, pos.y()));
        moves.add(new Position(pos.x(), pos.y()-1));
        moves.add(new Position(pos.x()-1, pos.y()-1));

        moves.add(new Position(pos.x()-1, pos.y()+1));
        moves.add(new Position(pos.x()+1, pos.y()-1));

        legalMoves = removeOccupedCase(board, moves);

        if(!moved){
            if (board.getPiece(new Position(pos.x(), pos.y()+1)) == null &&
                board.getPiece(new Position(pos.x(), pos.y()+2)) == null) {

                Piece piece = board.getPiece(new Position(pos.x(), pos.y()+3));
                if (piece instanceof Rook && !piece.getMoved()) {
                    legalMoves.add(new Position(pos.x(), pos.y()+2));
                }
            }
            if (board.getPiece(new Position(pos.x(), pos.y()-1)) == null &&
                board.getPiece(new Position(pos.x(), pos.y()-2)) == null &&
                board.getPiece(new Position(pos.x(), pos.y()-3)) == null) {

                Piece piece = board.getPiece(new Position(pos.x(), pos.y()-4));
                if (piece instanceof Rook && !piece.getMoved()) {
                    legalMoves.add(new Position(pos.x(), pos.y()-3));
                }
            }

        }

        return legalMoves;
    }

    private List<Position> removeOccupedCase(Board board, List<Position> moves) {
        int x, y;
        List<Position> legalMoves = new ArrayList<>();
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
