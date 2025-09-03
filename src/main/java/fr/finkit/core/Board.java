package fr.finkit.core;

import fr.finkit.Piece.*;

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

    public boolean move(Position piecePos, Position pos) {
        if (getLegalMovesForPiece(piecePos).contains(pos)){
            Piece piece = getPiece(piecePos);
            setPiece(pos, piece);
            setPiece(piecePos, null);
            return true;
        }
        return false;
    }

    private void initTerrain() {
        terrain[0][0] = new Rook(PieceColor.BLACK);
        terrain[0][1] = new Knight(PieceColor.BLACK);
        terrain[0][2] = new Bishop(PieceColor.BLACK);
        terrain[0][3] = new Queen(PieceColor.BLACK);
        terrain[0][4] = new King(PieceColor.BLACK);
        terrain[0][5] = new Bishop(PieceColor.BLACK);
        terrain[0][6] = new Knight(PieceColor.BLACK);
        terrain[0][7] = new Rook(PieceColor.BLACK);

        for (int col = 0; col < 8; col++) {
            terrain[1][col] = new Pawn(PieceColor.BLACK);
        }

        // Cases vides (rangées 2 à 5)
        for (int row = 2; row <= 5; row++) {
            for (int col = 0; col < 8; col++) {
                terrain[row][col] = null;
            }
        }

        // Pièces blanches (rangée 6 et 7)
        for (int col = 0; col < 8; col++) {
            terrain[6][col] = new Pawn(PieceColor.WHITE);
        }

        terrain[7][0] = new Rook(PieceColor.WHITE);
        terrain[7][1] = new Knight(PieceColor.WHITE);
        terrain[7][2] = new Bishop(PieceColor.WHITE);
        terrain[7][3] = new Queen(PieceColor.WHITE);
        terrain[7][4] = new King(PieceColor.WHITE);
        terrain[7][5] = new Bishop(PieceColor.WHITE);
        terrain[7][6] = new Knight(PieceColor.WHITE);
        terrain[7][7] = new Rook(PieceColor.WHITE);
    }

    public Piece getPiece(Position pos) {
        return terrain[pos.x()][pos.y()];
    }

    public void setPiece(Position pos, Piece piece) {
        terrain[pos.x()][pos.y()] = piece;
    }

}
