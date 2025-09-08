package fr.finkit.core;

import fr.finkit.Piece.*;

import java.util.List;

public class MinMax {

    private static int evaluate(Board board) {
        int score = 0;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                var piece = board.getPiece(new fr.finkit.Piece.Position(r, c));
                if (piece == null) continue;

                int val = switch (piece.getClass().getSimpleName()) {
                    case "Pawn" -> 100;
                    case "Knight", "Bishop" -> 300;
                    case "Rook" -> 500;
                    case "Queen" -> 900;
                    case "King" -> 10000;
                    default -> 0;
                };
                score += (piece.getColor() == PieceColor.WHITE) ? val : -val;
            }
        }
        return score;
    }

    public static int minimax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0) {
            return evaluate(board);
        }
        PieceColor current = maximizingPlayer ? PieceColor.WHITE : PieceColor.BLACK;
        List<Move> moves = board.getAllLegalMoves(current);
        if (moves.isEmpty()) {
            if (board.isCheck() == current) {
                return maximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
            return 0; // pat
        }
        if (maximizingPlayer) {
            int value = Integer.MIN_VALUE;
            for (Move move : moves) {
                Board clone = board.copy();
                clone.move(move.from(), move.to());
                value = Math.max(value, minimax(clone, depth - 1, alpha, beta, false));
                alpha = Math.max(alpha, value);
                if (alpha >= beta) break; // Coupe Beta
            }
            return value;
        } else {
            int value = Integer.MAX_VALUE;
            for (Move move : moves) {
                Board clone = board.copy();
                clone.move(move.from(), move.to());
                value = Math.min(value, minimax(clone, depth - 1, alpha, beta, true));
                beta = Math.min(beta, value);
                if (alpha >= beta) break; // Coupe Alpha
            }
            return value;
        }
    }

    public static Move getBestMove(Board board, int depth) {
        Move bestMove = null;
        int bestValue = Integer.MIN_VALUE;

        List<Move> moves = board.getAllLegalMoves(PieceColor.BLACK);
        for (Move move : moves) {
            Board clone = board.copy();
            clone.move(move.from(), move.to());

            int value = minimax(board, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
            if (value > bestValue) {
                bestValue = value;
                bestMove = move;
            }
        }
        return bestMove;
    }

}
