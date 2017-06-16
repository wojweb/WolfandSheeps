package sample;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Projekt - symulacja wilka i owiec
 * Tile.java
 * Klasa tworzy płytkę wykorzystaną do budowy planszy i odpowiada za jej kolor + zajętość
 *
 * @author Szymon Wojtaszek 236592
 * @version Do oddania
 *
 */
class Tile extends StackPane {
    private int x, y;
    private boolean busy;
    private Rectangle rectangle =
            new Rectangle(Main.getTitleSize() - .5, Main.getTitleSize() -.5, Color.BLACK);

    boolean isBusy() {
        return busy;
    }
    void setBusy(boolean busy) {
        this.busy = busy;
    }

    void setColour(Color color){
        rectangle.setFill(color);
    }

    Tile(int x, int y){
        getChildren().add(rectangle);
        setAlignment(Pos.CENTER);
        this.x = x;
        this.y = y;

        setTranslateX(x * Main.getTitleSize());
        setTranslateY(y * Main.getTitleSize());


    }

}
