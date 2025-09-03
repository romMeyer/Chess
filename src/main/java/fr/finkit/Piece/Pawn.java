package fr.finkit.Piece;

import fr.finkit.core.Board;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    public Pawn(PieceColor pieceColor) {
        super(pieceColor);
        this.img = "Pawn.png";
    }

    @Override
    public List<Position> getLegalMoves(Board board, Position pos) {
        List<Position> legalMoves = new ArrayList<>();
        Position frontPiecePos;
        Position adjacentPiecePos;
        Piece adjacentPiece;

        if (pieceColor == PieceColor.BLACK){
            frontPiecePos = new Position(pos.x()+1, pos.y());
            if (board.getPiece(frontPiecePos) == null) legalMoves.add(frontPiecePos);

            adjacentPiecePos = new Position(pos.x()+1, pos.y()-1);
            adjacentPiece = board.getPiece(adjacentPiecePos);
            if (adjacentPiece != null && adjacentPiece.getColor() != getColor()) legalMoves.add(adjacentPiecePos);

            adjacentPiecePos = new Position(pos.x()+1, pos.y()+1);
            adjacentPiece = board.getPiece(adjacentPiecePos);
            if (adjacentPiece != null && adjacentPiece.getColor() != getColor()) legalMoves.add(adjacentPiecePos);

        } else if (pieceColor == PieceColor.WHITE){
            frontPiecePos = new Position(pos.x()-1, pos.y());
            if (board.getPiece(frontPiecePos) == null) legalMoves.add(frontPiecePos);

            adjacentPiecePos = new Position(pos.x()-1, pos.y()-1);
            adjacentPiece = board.getPiece(adjacentPiecePos);
            if (adjacentPiece != null && adjacentPiece.getColor() != getColor()) legalMoves.add(adjacentPiecePos);

            adjacentPiecePos = new Position(pos.x()-1, pos.y()+1);
            adjacentPiece = board.getPiece(adjacentPiecePos);
            if (adjacentPiece != null && adjacentPiece.getColor() != getColor()) legalMoves.add(adjacentPiecePos);
        }

        return legalMoves;
    }
}
