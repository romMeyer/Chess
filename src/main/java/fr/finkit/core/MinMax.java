package fr.finkit.core;

import fr.finkit.Piece.*;

import java.util.List;

public class MinMax {

    private static int evaluate(Board board) {
        int score = 0;
        int whiteKingPos = -1, blackKingPos = -1;
        int whiteMaterial = 0, blackMaterial = 0;
        int whiteMobility = 0, blackMobility = 0;
        int whiteCenter = 0, blackCenter = 0;

        // Tables de position pour les pièces (valeurs centipawn)
        int[] pawnTable = {
                0,  0,  0,  0,  0,  0,  0,  0,
                50, 50, 50, 50, 50, 50, 50, 50,
                10, 10, 20, 30, 30, 20, 10, 10,
                5,  5, 10, 25, 25, 10,  5,  5,
                0,  0,  0, 20, 20,  0,  0,  0,
                5, -5,-10,  0,  0,-10, -5,  5,
                5, 10, 10,-20,-20, 10, 10,  5,
                0,  0,  0,  0,  0,  0,  0,  0
        };

        int[] knightTable = {
                -50,-40,-30,-30,-30,-30,-40,-50,
                -40,-20,  0,  0,  0,  0,-20,-40,
                -30,  0, 10, 15, 15, 10,  0,-30,
                -30,  5, 15, 20, 20, 15,  5,-30,
                -30,  0, 15, 20, 20, 15,  0,-30,
                -30,  5, 10, 15, 15, 10,  5,-30,
                -40,-20,  0,  5,  5,  0,-20,-40,
                -50,-40,-30,-30,-30,-30,-40,-50
        };

        int[] bishopTable = {
                -20,-10,-10,-10,-10,-10,-10,-20,
                -10,  0,  0,  0,  0,  0,  0,-10,
                -10,  0,  5, 10, 10,  5,  0,-10,
                -10,  5,  5, 10, 10,  5,  5,-10,
                -10,  0, 10, 10, 10, 10,  0,-10,
                -10, 10, 10, 10, 10, 10, 10,-10,
                -10,  5,  0,  0,  0,  0,  5,-10,
                -20,-10,-10,-10,-10,-10,-10,-20
        };

        int[] rookTable = {
                0,  0,  0,  0,  0,  0,  0,  0,
                5, 10, 10, 10, 10, 10, 10,  5,
                -5,  0,  0,  0,  0,  0,  0, -5,
                -5,  0,  0,  0,  0,  0,  0, -5,
                -5,  0,  0,  0,  0,  0,  0, -5,
                -5,  0,  0,  0,  0,  0,  0, -5,
                -5,  0,  0,  0,  0,  0,  0, -5,
                0,  0,  0,  5,  5,  0,  0,  0
        };

        int[] kingMiddleTable = {
                -30,-40,-40,-50,-50,-40,-40,-30,
                -30,-40,-40,-50,-50,-40,-40,-30,
                -30,-40,-40,-50,-50,-40,-40,-30,
                -30,-40,-40,-50,-50,-40,-40,-30,
                -20,-30,-30,-40,-40,-30,-30,-20,
                -10,-20,-20,-20,-20,-20,-20,-10,
                20, 20,  0,  0,  0,  0, 20, 20,
                20, 30, 10,  0,  0, 10, 30, 20
        };

        // Parcourir toutes les cases
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                var piece = board.getPiece(new Position(r, c));
                if (piece == null) continue;

                int square = r * 8 + c;
                int mirrorSquare = (7 - r) * 8 + c; // Pour les pièces noires
                boolean isWhite = piece.getColor() == PieceColor.WHITE;

                // 1. VALEUR MATÉRIELLE DE BASE
                int pieceValue = switch (piece.getClass().getSimpleName()) {
                    case "Pawn" -> 100;
                    case "Knight" -> 320;
                    case "Bishop" -> 330;
                    case "Rook" -> 500;
                    case "Queen" -> 900;
                    case "King" -> 20000;
                    default -> 0;
                };

                // 2. BONUS POSITIONNEL (tables de position)
                int positionBonus = switch (piece.getClass().getSimpleName()) {
                    case "Pawn" -> pawnTable[isWhite ? square : mirrorSquare];
                    case "Knight" -> knightTable[isWhite ? square : mirrorSquare];
                    case "Bishop" -> bishopTable[isWhite ? square : mirrorSquare];
                    case "Rook" -> rookTable[isWhite ? square : mirrorSquare];
                    case "King" -> kingMiddleTable[isWhite ? square : mirrorSquare];
                    default -> 0;
                };

                // 3. CONTRÔLE DU CENTRE (cases d4, d5, e4, e5)
                int centerBonus = 0;
                if ((r >= 3 && r <= 4) && (c >= 3 && c <= 4)) {
                    centerBonus = 10; // Bonus pour occuper le centre
                }

                // 4. MOBILITÉ (nombre de coups légaux)
                int mobilityBonus = 0;
                try {
                    List<Position> moves = board.getLegalMovesForPiece(new Position(r, c));
                    mobilityBonus = moves.size() * 2; // 2 points par coup possible
                } catch (Exception e) {
                    mobilityBonus = 0;
                }

                // Totaliser pour chaque couleur
                int totalPieceValue = pieceValue + positionBonus + centerBonus + mobilityBonus;

                if (isWhite) {
                    whiteMaterial += totalPieceValue;
                    if (piece.getClass().getSimpleName().equals("King")) {
                        whiteKingPos = square;
                    }
                } else {
                    blackMaterial += totalPieceValue;
                    if (piece.getClass().getSimpleName().equals("King")) {
                        blackKingPos = square;
                    }
                }
            }
        }

        // 5. BONUS POUR LES CAPTURES POSSIBLES (CRITIQUE!)
        int captureBonus = 0;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                var piece = board.getPiece(new Position(r, c));
                if (piece == null) continue;

                try {
                    List<Position> moves = board.getLegalMovesForPiece(new Position(r, c));
                    for (Position position : moves) {
                        var target = board.getPiece(position);
                        if (target != null && target.getColor() != piece.getColor()) {
                            // BONUS MASSIF pour pouvoir capturer!
                            int targetValue = switch (target.getClass().getSimpleName()) {
                                case "Pawn" -> 100;
                                case "Knight", "Bishop" -> 300;
                                case "Rook" -> 500;
                                case "Queen" -> 900;
                                default -> 0;
                            };

                            int bonus = targetValue / 4; // 25% de la valeur de la cible
                            if (piece.getColor() == PieceColor.WHITE) {
                                captureBonus += bonus;
                            } else {
                                captureBonus -= bonus;
                            }
                        }
                    }
                } catch (Exception e) {
                    // Ignorer les erreurs
                }
            }
        }

        // 6. SÉCURITÉ DU ROI (basique)
        int kingSafety = 0;
        if (board.isCheck() == PieceColor.WHITE) {
            kingSafety -= 50; // Pénalité pour être en échec
        } else if (board.isCheck() == PieceColor.BLACK) {
            kingSafety += 50;
        }

        // SCORE FINAL
        score = whiteMaterial - blackMaterial + captureBonus + kingSafety;

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

            int value = minimax(clone, depth-1, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            if (value > bestValue) {
                bestValue = value;
                bestMove = move;
            }
        }
        return bestMove;
    }

}
