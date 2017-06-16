package sample;

import javafx.scene.paint.Color;

import static sample.Main.*;
import java.util.Random;

/**
 * Projekt - symulacja wilka i owiec
 * Sheep.java
 * Klasa tworzy obiek owcy, jego wątek i odpowiada za jego ruchy
 *
 * @author Szymon Wojtaszek 236592
 * @version Do oddania
 *
 */

public class Sheep implements Runnable{
    private int sheepX;
    private int sheepY;
    private final int sheepNumber;
    private Thread thread;
    private boolean alive;
    Tile[][] sheepGrid;

    Game game;

    Wolf wolf;

    public int getSheepX() {
        return sheepX;
    }
    public int getSheepY() {
        return sheepY;
    }

    boolean isAlive(){
        return alive;
    }
    void kill(){
        alive = false;
    }

    /**
     * Odpowiada za wątek owcy
     */
    public void run(){
        try{
            Thread.sleep(500);

        }catch (InterruptedException e){
            System.out.println("ups");
        }
        while (wolf.isAlive())
            game.moveSheep(this, sheepNumber);


    }

    /**
     * Porusza owcą
     */
    public void move(){
        getOut();
        findNext();
        getIn();
    }

    /**
     * Wejscie owcy w kratkę
     */
    private void getIn(){
        sheepGrid[sheepX][sheepY].setColour(Color.BLUE);
        sheepGrid[sheepX][sheepY].setBusy(true);
    }

    /**
     * Wyjscie owcy
     */
    private void getOut(){
        sheepGrid[sheepX][sheepY].setColour(Color.BLACK);
        sheepGrid[sheepX][sheepY].setBusy(false);

    }

    /**
     * Znajduje kolejne pole na ucieczke owcy, na granicach planszy porusza się ona w sposób losowy,
     * a jeśli miała by najść na jakąś inną owcę wstrzymuje ruch.
     */
    private void findNext(){
        int dX = getSheepX() - wolf.getWolfX();
        int dY = getSheepY() - wolf.getWolfY();

        Random rand = new Random();

        int newSheepX;
        int newSheepY;

        do{
            newSheepX = sheepX;
            newSheepY = sheepY;

            if(dX > 0 && dY > 0){
                switch(rand.nextInt(3)) {
                    case 0:
                        newSheepX++;
                        break;
                    case 1:
                        newSheepX++;
                        newSheepY++;
                        break;
                    case 2:
                        newSheepY++;
                        break;
                    default:
                        System.out.println("There is something wrong with sheep move");
                }
            }
            if(dX > 0 && dY == 0){
                switch(rand.nextInt(3)) {
                    case 0:
                        newSheepX++;
                        newSheepY--;
                        break;
                    case 1:
                        newSheepX++;
                        break;
                    case 2:
                        newSheepX++;
                        newSheepY++;
                        break;
                    default:
                        System.out.println("There is something wrong with sheep move");
                }
            }
            if(dX > 0 && dY < 0){
                switch(rand.nextInt(3)) {
                    case 0:
                        newSheepY--;
                        break;
                    case 1:
                        newSheepX++;
                        newSheepY--;
                        break;
                    case 2:
                        newSheepX++;
                        break;
                    default:
                        System.out.println("There is something wrong with sheep move");
                }
            }
            if (dX == 0 && dY < 0) {
                switch(rand.nextInt(3)) {
                    case 0:
                        newSheepX--;
                        newSheepY--;
                        break;
                    case 1:
                        newSheepY--;
                        break;
                    case 2:
                        newSheepX++;
                        newSheepY--;
                        break;
                    default:
                        System.out.println("There is something wrong with sheep move");
                }
            }
            if(dX < 0 && dY < 0){
                switch(rand.nextInt(3)) {
                    case 0:
                        newSheepX--;
                        break;
                    case 1:
                        newSheepX--;
                        newSheepY--;
                        break;
                    case 2:
                        newSheepY--;
                        break;
                    default:
                        System.out.println("There is something wrong with sheep move");
                }
            }
            if(dX < 0 && dY == 0){
                switch(rand.nextInt(3)) {
                    case 0:
                        newSheepX--;
                        newSheepY++;
                        break;
                    case 1:
                        newSheepX--;
                        break;
                    case 2:
                        newSheepX--;
                        newSheepY--;
                        break;
                    default:
                        System.out.println("There is something wrong with sheep move");
                }
            }
            if(dX < 0 && dY > 0){
                switch(rand.nextInt(3)) {
                    case 0:
                        newSheepY++;
                        break;
                    case 1:
                        newSheepX--;
                        newSheepY++;
                        break;
                    case 2:
                        newSheepX--;
                        break;
                    default:
                        System.out.println("There is something wrong with sheep move");
                }
            }
            if(dX == 0 && dY > 0){
                switch(rand.nextInt(3)) {
                    case 0:
                        newSheepX++;
                        newSheepY++;
                        break;
                    case 1:
                        newSheepY++;
                        break;
                    case 2:
                        newSheepX--;
                        newSheepY++;
                        break;
                    default:
                        System.out.println("There is something wrong with sheep move");
                }
            }
            if(newSheepX == X_TITLES || newSheepX == -1){
                switch (rand.nextInt(2)){
                    case 0:
                        newSheepX = sheepX;
                        newSheepY = sheepY + 1;
                        break;
                    case 1:
                        newSheepX = sheepX;
                        newSheepY = sheepY - 1;
                }
            }
            if(newSheepY == Y_TITLES || newSheepY == -1){
                switch (rand.nextInt(2)){
                    case 0:
                        newSheepX = sheepX + 1;
                        newSheepY = sheepY;
                        break;
                    case 1:
                        newSheepX = sheepX - 1;
                        newSheepY = sheepY;
                }
            }

        }while(!(newSheepX >= 0 && newSheepY >= 0 && newSheepX < X_TITLES && newSheepY < Y_TITLES));



        if(!sheepGrid[newSheepX][newSheepY].isBusy()){
            sheepX = newSheepX;
            sheepY = newSheepY;
        }
    }

    Sheep(Game game, Wolf wolf, int sheepNumber, Tile[][] sheepGrid){
        this.game = game;
        this.wolf = wolf;
        this.sheepNumber = sheepNumber;
        this.sheepGrid = sheepGrid;
        sheepX = new Random().nextInt(X_TITLES);
        sheepY = new Random().nextInt(Y_TITLES);
        alive = true;
        thread = new Thread(this, "Sheep" + sheepNumber);
        thread.start();
    }


}
