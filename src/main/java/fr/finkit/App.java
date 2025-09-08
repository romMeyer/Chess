package fr.finkit;

import fr.finkit.core.Board;
import fr.finkit.gui.BoardView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage){
        Board board = new Board(true);
        var view = new BoardView(board, 105);

        Scene scene = new Scene(view.getRoot());
        stage.setTitle("Chess");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}
