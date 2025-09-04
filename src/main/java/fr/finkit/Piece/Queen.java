package fr.finkit.Piece;

import fr.finkit.core.Board;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {
    public Queen(PieceColor pieceColor) {
        super(pieceColor);
        this.img = "Queen.png";
    }

    @Override
    public List<Position> getLegalMoves(Board board, Position position) {
        List<Position> legalMoves = new ArrayList<>();
        legalMoves.addAll(new Rook(pieceColor).getLegalMoves(board, position));
        legalMoves.addAll(new Bishop(pieceColor).getLegalMoves(board, position));
        return legalMoves;
    }

}
