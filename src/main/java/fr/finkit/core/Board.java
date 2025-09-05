package fr.finkit.core;

import fr.finkit.Piece.*;

import java.util.List;

public class Board {
    Piece[][] terrain;
    PieceColor playerTurn = PieceColor.WHITE;

    public Board() {
        this.terrain = new Piece[8][8];
        initTerrain();
    }

    public List<Position> getLegalMovesForPiece(Position pos){
        Piece piece = getPiece(pos);
        return piece.getLegalMoves(this, pos);
    }

    public boolean move(Position piecePos, Position pos) {
        Piece piece = getPiece(piecePos);
        if (piece == null || piece.getColor() != playerTurn) return false;
        if (getLegalMovesForPiece(piecePos).contains(pos)){
            for (Piece[] pieces : terrain){
                for (Piece pieceEP : pieces){
                    if (pieceEP != null) pieceEP.setEnPassant(false);
                }
            }
            checkEnPassant(piecePos, pos, piece);
            setPiece(pos, piece);
            setPiece(piecePos, null);
            playerTurn = (playerTurn == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;

            return true;
        }
        return false;
    }

    private void checkEnPassant(Position piecePos, Position pos, Piece piece) {
        if (!(piece instanceof Pawn)) return;

        if (piece.getColor() == PieceColor.BLACK) piece.setEnPassant( piecePos.x() == 1 && pos.x() == 3);
        else piece.setEnPassant(piecePos.x() == 6 && pos.x() == 4);

        if(piece.getEnPassant()) System.out.println(piece.getColor());

        if (piecePos.y() != pos.y() && getPiece(pos) == null) {
            Position capturedPos = new Position(piecePos.x(), pos.y());
            setPiece(capturedPos, null);
        }
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

    public PieceColor getPlayerTurn() {
        return playerTurn;
    }

    public void setPiece(Position pos, Piece piece) {
        terrain[pos.x()][pos.y()] = piece;
    }

}
