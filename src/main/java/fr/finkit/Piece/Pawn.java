package fr.finkit.Piece;

import fr.finkit.core.Board;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    public Pawn(Color color) {
        super(color);
        this.img = "Pawn.png";
    }

    @Override
    public List<Position> getLegalMoves(Board board, Position pos) {
        List<Position> legalMoves = new ArrayList<>();
        if(color == Color.BLACK){
            legalMoves.add(new Position(pos.x()+1, 0));

        } else if(color == Color.WHITE){
            legalMoves.add(new Position(pos.y()-1, 0));
        }

        return legalMoves;
    }
}
