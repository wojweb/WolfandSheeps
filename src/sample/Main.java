package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Projekt - symulacja wilka i owiec
 * Plansza o wymiarach podanych przez użytkownika i odpowiednią liczbą owiec oraz wilk, który biega za nimi
 * po koleji i je zjada. Ruchy owiec przy ścianach mogą się wydawać trochę nieuporządkowane, lecz pisałem
 * ten program w celu uzyskania maksymalnej zgodności z poleceniem.
 *
 * Main.java
 * Klasa tworzy GUI oraz wywołuje uruchomienie symulacji
 *
 * @author Szymon Wojtaszek 236592
 * @version Do oddania
 *
 */

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }


    private static final int TITLE_SIZE = 10;
    private static int W;
    private static int H;



    static int X_TITLES;
    static int Y_TITLES;

    static int getTitleSize() {
        return TITLE_SIZE;
    }

    /**
     * Funkcja tworzy plansze pod symulacje. W pełni odpowiada za stworzenie layoutu planszy
     * na podstawie wpisanych przez użytkownika parametrów.
     * @param sheepNumber
     * @param moveTime
     * @param gridSize
     * @return layout planszy
     */
    private Parent createContent(int sheepNumber, int moveTime, int gridSize){
        X_TITLES = gridSize;
        System.out.println("Titles mają rozmiar");
        Y_TITLES = gridSize;
        Tile[][] grid = new Tile[X_TITLES][Y_TITLES];

        W = X_TITLES * TITLE_SIZE;
        H = Y_TITLES * TITLE_SIZE;

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(5, 5, 5, 5));
        Pane center = new Pane();
        root.setCenter(center);
        for(int x = 0; x < X_TITLES; x++){
            for(int y = 0; y < Y_TITLES; y++){
                Tile tile = new Tile(x, y);
                grid[x][y] = tile;
                center.getChildren().add(tile);
            }
        }
        Button button = new Button("Click me");
        HBox hBox = new HBox(button);
        hBox.setPadding(new Insets(5, 10, 5, 10));
        root.setTop(hBox);

        button.setOnAction(e -> {
            startAnimation(sheepNumber, moveTime, grid);
        });

        root.setPrefSize(W + 10,H + 50);
        return root;
    }

    /**
     * Funkcja uruchamiana przy starcie aplikacji. Tworzy ona okienko w którym możemy ustawić
     * podstawowe parametry naszej animacji czyli: ilość owiec, czas jednego ruchu czy wielkość planszy.
     * Po kliknięciu przycisku uruchamia się okno z symulacją.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(40, 10, 40, 10));
        hBox.setSpacing(15);
        hBox.setAlignment(Pos.CENTER);

        Label sheepLabel = new Label("Number of sheeps:");
        TextField sheepTextField = new TextField();

        Label timeLabel = new Label("Time for a move:");
        TextField timeTextField = new TextField();

        Label gridLabel = new Label("Size of grid:");
        TextField gridTextField = new TextField();

        Button startButton = new Button("Make a board");

        hBox.getChildren().addAll(sheepLabel, sheepTextField, timeLabel, timeTextField, gridLabel, gridTextField, startButton);

        Scene startScene = new Scene(hBox);
        primaryStage.setScene(startScene);

        startButton.setOnAction(e -> {
            primaryStage.setScene(new Scene(createContent(Integer.parseInt(sheepTextField.getText()), Integer.parseInt(timeTextField.getText()), Integer.parseInt(gridTextField.getText()))));

        });


        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e -> {
            primaryStage.close();
            System.exit(0);
        });
        primaryStage.show();


    }

    /**
     * Funkcja tworzy plansze, wilka oraz owce, które w momencie stworzenia stają się osobnymi wątkami
     * i zaczynają funkcjonować.
     * @param n ilość owiec
     * @param moveTime czas ruchu
     * @param grid tablica planszy
     */
    private void startAnimation(int n, int moveTime, Tile[][] grid){

        Game game = new Game(n, moveTime);
        Sheep sheepList[] = new Sheep[n];

        Wolf wolf = new Wolf(game, sheepList, grid);

        for(int i = 0; i < n; i++){
            sheepList[i] = new Sheep(game, wolf, i+1, grid);
        }



    }

}
