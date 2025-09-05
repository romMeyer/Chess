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
        Position posEnPassant;
        Piece pieceEnPassant;

        if (pieceColor == PieceColor.BLACK){
            if(pos.x() == 1 && board.getPiece(new Position(pos.x()+2, pos.y()))== null) legalMoves.add(new Position(pos.x()+2, pos.y()));
            if(pos.x() == 7) return legalMoves;
            frontPiecePos = new Position(pos.x()+1, pos.y());
            if (board.getPiece(frontPiecePos) == null) legalMoves.add(frontPiecePos);
            if (pos.y() > 0){
                adjacentPiecePos = new Position(pos.x()+1, pos.y()-1);
                adjacentPiece = board.getPiece(adjacentPiecePos);
                if (adjacentPiece != null && adjacentPiece.getColor() != getColor()) legalMoves.add(adjacentPiecePos);
                posEnPassant = new Position(adjacentPiecePos.x()-1, adjacentPiecePos.y());
                pieceEnPassant = board.getPiece(posEnPassant);
                if(adjacentPiece == null && pieceEnPassant != null && pieceEnPassant.getEnPassant()) legalMoves.add(adjacentPiecePos) ;
            }

            if (pos.y() < 7){
                adjacentPiecePos = new Position(pos.x()+1, pos.y()+1);
                adjacentPiece = board.getPiece(adjacentPiecePos);
                if (adjacentPiece != null && adjacentPiece.getColor() != getColor()) legalMoves.add(adjacentPiecePos);
                posEnPassant = new Position(adjacentPiecePos.x()-1, adjacentPiecePos.y());
                pieceEnPassant = board.getPiece(posEnPassant);
                if(adjacentPiece == null && pieceEnPassant != null && pieceEnPassant.getEnPassant()) legalMoves.add(adjacentPiecePos) ;

            }

        } else if (pieceColor == PieceColor.WHITE){
            if(pos.x() == 6 && board.getPiece(new Position(pos.x()-2, pos.y()))== null) legalMoves.add(new Position(pos.x()-2, pos.y()));
            if(pos.x() == 0) return legalMoves;

            frontPiecePos = new Position(pos.x()-1, pos.y());
            if (board.getPiece(frontPiecePos) == null) legalMoves.add(frontPiecePos);

            if (pos.y() > 0){
                adjacentPiecePos = new Position(pos.x()-1, pos.y()-1);
                adjacentPiece = board.getPiece(adjacentPiecePos);
                if (adjacentPiece != null && adjacentPiece.getColor() != getColor()) legalMoves.add(adjacentPiecePos);
                posEnPassant = new Position(adjacentPiecePos.x()+1, adjacentPiecePos.y());
                pieceEnPassant = board.getPiece(posEnPassant);
                if(adjacentPiece == null && pieceEnPassant != null && pieceEnPassant.getEnPassant()) legalMoves.add(adjacentPiecePos) ;

            };

            if (pos.y() < 7){
                adjacentPiecePos = new Position(pos.x()-1, pos.y()+1);
                adjacentPiece = board.getPiece(adjacentPiecePos);
                if (adjacentPiece != null && adjacentPiece.getColor() != getColor()) legalMoves.add(adjacentPiecePos);
                posEnPassant = new Position(adjacentPiecePos.x()+1, adjacentPiecePos.y());
                pieceEnPassant = board.getPiece(posEnPassant);
                if(adjacentPiece == null && pieceEnPassant != null && pieceEnPassant.getEnPassant()) legalMoves.add(adjacentPiecePos) ;

            }

        }

        return legalMoves;
    }
}
