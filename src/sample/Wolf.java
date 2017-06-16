package sample;

import javafx.scene.paint.Color;

import static sample.Main.*;
/**
 * Projekt - symulacja wilka i owiec
 * Wolf.java
 * Klasa tworzy obiek Wilka, jego wątek i odpowiada za jego ruchy
 *
 * @author Szymon Wojtaszek 236592
 * @version Do oddania
 *
 */
public class Wolf implements Runnable {
    private int wolfX, wolfY;
    private Thread thread;
    private Game game;
    private Sheep sheepList[];
    private boolean alive;
    private Queue wolfQueoe;
    private Tile[][] wolfGrid;

    public int getWolfX() {
        return wolfX;
    }
    public int getWolfY() {return wolfY;}
    public boolean isAlive(){
        return alive;
    }

    /**
     * Funkcja uruchamia wątek wilka
     */
    public void run() {
        try{
            Thread.sleep(500);

        }catch (InterruptedException e){
            System.out.println("ups");
        }
        while(game.moveWolf(this))
            ;
        getOut();
    }

    /**
     * Funkcja tworząca ruch wilka, znajduje on owce najbliżej siebie i robi jeden krok w jej kierunku
     * Kiedy dobiegnie do owcy zjada ją i odpoczywa przez kilka kolejek
     * @return
     */
    boolean move(){
        if(wolfQueoe.getNumber() == 0){
            Sheep target = null;
            for(Sheep x:  sheepList)
                if(x.isAlive() && (target == null || distance(x) < distance(target))){
                    target = x;
                }



            if(target == null){
                alive = false;
                return false;
            }

            getOut();
            findNext(target);
            getIn();

            if(target.getSheepX() == wolfX && target.getSheepY() == wolfY){
                target.kill();
                wolfQueoe.put();
                wolfGrid[wolfX][wolfY].setBusy(false);
            }


            return true;

        }else{
            wolfQueoe.put();
            return true;
        }

    }

    /**
     * Funkcja znajdująca następną klatkę, gdzie pójdzie wilk
     * @param target
     */
    void findNext(Sheep target){
        int dX = target.getSheepX() - wolfX;
        int dY = target.getSheepY() - wolfY;


        if(dX > 0)
            wolfX++;
        else if(dX < 0)
            wolfX--;

        if(dY > 0)
            wolfY++;
        else if(dY < 0)
            wolfY--;

    }

    /**
     * Funkcja zwracająca odległość pomiędzy wilkiem, a przyjętym zającem
     * @param sheep
     * @return
     */
    double distance(Sheep sheep){
        return Math.pow(sheep.getSheepX() - this.getWolfX(), 2) + Math.pow(sheep.getSheepY() - this.getWolfY(),2);
    }

    /**
     * Wejscie wilka w pole
     */
    private void getIn(){

        wolfGrid[wolfX][wolfY].setColour(Color.RED);
    }

    /**
     * Wyjscie, nic oryginalnego
     */
    private void getOut(){
        wolfGrid[wolfX][wolfY].setColour(Color.BLACK);

    }

    Wolf(Game game, Sheep sheepList[], Tile[][] grid){
        this.game = game;
        this.sheepList = sheepList;
        this.wolfX = X_TITLES/2;
        this.wolfY = Y_TITLES/2;
        this.alive = true;
        this.wolfQueoe = new Queue(4);
        this.wolfGrid = grid;
        thread = new Thread(this, "Wolf");
        thread.start();

    }


}