package fr.finkit.gui;

import fr.finkit.Piece.PieceColor;
import fr.finkit.Piece.Piece;
import fr.finkit.Piece.Position;
import fr.finkit.core.Board;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

public class BoardView {
    private final GridPane grid = new GridPane();
    private final Board board;
    private final int tileSize;

    private Position selected; // case sélectionnée
    private final HashSet<Position> legalHighlights = new HashSet<>();

    // cache images (pieceName -> Image)
    private final Map<String, Image> imageCache = new HashMap<>();

    public BoardView(Board board, int tileSize) {
        this.board = board;
        this.tileSize = tileSize;

        grid.setAlignment(Pos.CENTER);
        grid.setGridLinesVisible(false);
        grid.setStyle("-fx-background-color: #0B132B;"); // fond sombre discret
        grid.setHgap(0); grid.setVgap(0);

        buildBoard();
        render();
    }

    public Pane getRoot() { return grid; }

    private void buildBoard() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                var square = new StackPane();
                square.setMinSize(tileSize, tileSize);
                square.setPrefSize(tileSize, tileSize);
                square.setMaxSize(tileSize, tileSize);

                // damier : blanc / bleu canard
                boolean light = (r + c) % 2 == 0;
                square.setBackground(new Background(new BackgroundFill(
                        light ? Color.web("#F5F7FA") : Color.web("#0E7C86"), // blanc / bleu canard
                        null, null
                )));

                final int fr = r, fc = c;
                square.setOnMouseClicked(e -> onSquareClick(fr, fc));

                grid.add(square, c, r);
            }
        }
    }

    private void onSquareClick(int r, int c) {
        Position clicked = new Position(r, c);
        Piece p = board.getPiece(new Position(r, c));

        // Si une pièce est déjà sélectionnée et (r,c) est un coup légal → on joue
        if (selected != null) {
            if (legalHighlights.contains(clicked)) {
                if (board.move(selected, clicked)) { // utilise ta méthode move(Position, Position)
                    clearSelection();
                    render();
                    return;
                }
            }
        }

        // Sinon on (re)sélectionne si une pièce amicale est présente
        if (p != null) {
            selected = clicked;
            var list = board.getLegalMovesForPiece(selected); // ← ta méthode
            legalHighlights.clear();
            if (list != null) legalHighlights.addAll(list);
        } else {
            clearSelection();
        }
        render();
    }

    private void clearSelection() {
        selected = null;
        legalHighlights.clear();
    }

    public void render() {
        // efface contenu et re-dessine
        for (var node : grid.getChildren()) {
            if (node instanceof StackPane sp) sp.getChildren().clear();
        }

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                var sp = getSquare(r, c);

                // surbrillance sélection
                if (selected != null && selected.x() == r && selected.y() == c) {
                    sp.setBorder(new Border(new BorderStroke(
                            Color.web("#FFD166"),
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4)
                    )));
                } else {
                    sp.setBorder(Border.EMPTY);
                }

                // pièces
                Piece piece = board.getPiece(new Position(r, c));
                if (piece != null) {
                    sp.getChildren().add(pieceNode(piece));
                }

                // highlights des coups
                if (legalHighlights.contains(new Position(r, c))) {
                    Circle dot = new Circle(tileSize * 0.12);
                    dot.setFill(Color.rgb(0, 0, 0, 0.35));
                    sp.getChildren().add(dot);
                }
            }
        }
    }

    private StackPane getSquare(int r, int c) {
        for (var n : grid.getChildren()) {
            Integer rr = GridPane.getRowIndex(n), cc = GridPane.getColumnIndex(n);
            if (rr != null && cc != null && rr == r && cc == c) {
                return (StackPane) n;
            }
        }
        throw new IllegalStateException("Square not found: " + r + "," + c);
    }

    private Group pieceNode(Piece piece) {
        // clé de cache : "Pawn_White" etc.
        Image img;
        try {
            // Couleur → "White" ou "Black"
            String colorDir = (piece.getColor() == PieceColor.WHITE) ? "White" : "Black";
            String name = piece.getClass().getSimpleName(); // Pawn, Rook, etc.
            String path = "/assets/" + colorDir + "/" + name + ".png";
            img = new Image((path));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }

        ImageView iv = new ImageView(img);
        iv.setPreserveRatio(false);
        iv.setFitWidth(tileSize * 0.7);
        iv.setFitHeight(tileSize * 0.8);
        return new Group(iv);
    }
}
