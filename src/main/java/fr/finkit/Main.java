package fr.finkit;


import fr.finkit.Piece.Position;
import fr.finkit.core.Board;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        drawBoard(board);
        board.move(new Position(1,0), new Position(2,0));
        drawBoard(board);

    }

    private static void drawBoard(Board board){
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if(board.getPiece(new Position(row, col)) != null){
                    System.out.print(board.getPiece(new Position(row, col)).getImage() + " ");
                }
                else {
                    System.out.print(" - ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}