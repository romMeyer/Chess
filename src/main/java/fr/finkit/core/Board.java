package fr.finkit.core;

import fr.finkit.Piece.Color;
import fr.finkit.Piece.Pawn;
import fr.finkit.Piece.Piece;
import fr.finkit.Piece.Position;

import java.util.List;

public class Board {
    Piece[][] terrain;

    public Board() {
        this.terrain = new Piece[8][8];
        initTerrain();
    }

    public List<Position> getLegalMovesForPiece(Position pos){
        Piece piece = getPiece(pos);
        return piece.getLegalMoves(this, pos);
    }

    public void move(Position piecePos, Position pos) {
        if (getLegalMovesForPiece(piecePos).contains(pos)){
            Piece piece = getPiece(piecePos);
            setPiece(pos, piece);
            setPiece(piecePos, null);
        }
    }

    private void initTerrain() {
//        terrain[0][0] = new
//        terrain[0][1] = new
//        terrain[0][2] = new
//        terrain[0][3] = new
//        terrain[0][4] = new
//        terrain[0][5] = new
//        terrain[0][6] = new
//        terrain[0][7] = new

        for (int col = 0; col < 8; col++) {
            terrain[1][col] = new Pawn(Color.BLACK);
        }

        // Cases vides (rangées 2 à 5)
        for (int row = 2; row <= 5; row++) {
            for (int col = 0; col < 8; col++) {
                terrain[row][col] = null;
            }
        }

        // Pièces blanches (rangée 6 et 7)
        for (int col = 0; col < 8; col++) {
            terrain[6][col] = new Pawn(Color.WHITE);
        }
//
//        terrain[7][0] = new
//        terrain[7][1] = new
//        terrain[7][2] = new
//        terrain[7][3] = new
//        terrain[7][4] = new
//        terrain[7][5] = new
//        terrain[7][6] = new
//        terrain[7][7] = new
    }

    public Piece getPiece(Position pos) {
        return terrain[pos.x()][pos.y()];
    }

    public void setPiece(Position pos, Piece piece) {
        terrain[pos.x()][pos.y()] = piece;
    }

}
