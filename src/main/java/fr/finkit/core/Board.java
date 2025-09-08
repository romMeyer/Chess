package fr.finkit.core;

import fr.finkit.Piece.*;

import java.util.ArrayList;
import java.util.List;

public class Board {
    Piece[][] terrain;
    PieceColor playerTurn = PieceColor.WHITE;
    Boolean computer;

    public Board(Boolean computer) {
        this.terrain = new Piece[8][8];
        this.computer = computer;
        initTerrain();
    }

    public boolean play(Position piecePos, Position pos) {
        if (!computer) {
            return move(piecePos, pos);
        }

        if (playerTurn == PieceColor.WHITE) {
            boolean ok = move(piecePos, pos);
            if (ok) {
                Move aiMove = MinMax.getBestMove(this, 4);
                if (aiMove != null) {
                    move(aiMove.from(), aiMove.to());
                }
            }
            return ok;
        } else {
            // Si jamais on appelle play() quand c’est au tour de l’IA
            Move aiMove = MinMax.getBestMove(this, 4);
            if (aiMove != null) {
                return move(aiMove.from(), aiMove.to());
            }
        }
        return false;
    }

    public List<Position> getLegalMovesForPiece(Position pos){
        Piece piece = getPiece(pos);
        List<Position> moves = piece.getLegalMoves(this, pos);
        List<Position> legalMoves = new ArrayList<>();

        for (Position move : moves) {
            Piece captured = getPiece(move);

            setPiece(move, piece);
            setPiece(pos, null);

            boolean safe = (isCheck() != playerTurn);

            setPiece(pos, piece);
            setPiece(move, captured);

            if(safe){
                legalMoves.add(move);
            }
        }
        return legalMoves;
    }

    public boolean move(Position piecePos, Position pos) {
        Piece piece = getPiece(piecePos);
        if (piece == null || piece.getColor() != playerTurn) return false;
        if (getLegalMovesForPiece(piecePos).contains(pos)){

            checkEnPassant(piecePos, pos, piece);
            checkCastling(piecePos, pos, piece);
            setPiece(pos, piece);
            setPiece(piecePos, null);
            piece.setMoved(true);
            playerTurn = (playerTurn == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;

            return true;
        }
        return false;
    }

    private void checkEnPassant(Position piecePos, Position pos, Piece piece) {
        if (!(piece instanceof Pawn)) return;

        for (Piece[] pieces : terrain){
            for (Piece pieceEP : pieces){
                if (pieceEP != null) pieceEP.setEnPassant(false);
            }
        }
        if (piece.getColor() == PieceColor.BLACK) piece.setEnPassant( piecePos.x() == 1 && pos.x() == 3);
        else piece.setEnPassant(piecePos.x() == 6 && pos.x() == 4);

        if (piecePos.y() != pos.y() && getPiece(pos) == null) {
            Position capturedPos = new Position(piecePos.x(), pos.y());
            setPiece(capturedPos, null);
        }
    }

    private void checkCastling(Position piecePos, Position pos, Piece piece) {
        if (!(piece instanceof King) || piece.getMoved()) return;

        Piece rook;
        if (Math.abs(piecePos.y() - pos.y()) == 2){
            rook = getPiece(new Position(pos.x(), pos.y()+1));
            setPiece(new Position(pos.x(), pos.y()+1), null);
            setPiece(new Position(pos.x(), pos.y()-1), rook);
        } else if (Math.abs(piecePos.y() - pos.y()) == 3) {
            rook = getPiece(new Position(pos.x(), pos.y()-1));
            setPiece(new Position(pos.x(), pos.y()-1), null);
            setPiece(new Position(pos.x(), pos.y()+1), rook);

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

    public PieceColor isCheck(){
        List<Position> playableMoves = new ArrayList<>();
        for (int r=0; r < 8; r++) {
            for (int y =  0; y < 8; y++) {
                Piece piece = getPiece(new Position(r, y));
                if (piece == null) continue;
                if (piece.getColor() != playerTurn) {
                    playableMoves = piece.getLegalMoves(this, new Position(r, y));
                    for (Position pos: playableMoves){
                        if (getPiece(pos) instanceof King) return (playerTurn == PieceColor.WHITE) ? PieceColor.WHITE : PieceColor.BLACK;
                    }
                }
            }
        }
        return null;
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

    public Board copy() {
        Board board = new Board(computer);
        board.playerTurn = this.playerTurn;
        board.terrain = new Piece[8][8];

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = this.terrain[r][c];
                if (p != null) {
                    board.terrain[r][c] = p.copy();
                }
            }
        }
        return board;
    }

    public List<Move> getAllLegalMoves(PieceColor color) {
        List<Move> allMoves = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Position pos = new Position(row, col);
                Piece piece = getPiece(pos);
                if (piece != null && piece.getColor() == color) {
                    for (Position target : getLegalMovesForPiece(pos)) {
                        allMoves.add(new Move(pos, target));
                    }
                }
            }
        }
        return allMoves;
    }

}
