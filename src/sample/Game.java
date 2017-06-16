package sample;

import java.util.Random;

/**
 * Projekt - symulacja wilka i owiec
 * Game.java
 * Klasa odpowiada za synchronizację ruchu wilka i owiec
 *
 * @author Szymon Wojtaszek 236592
 * @version Do oddania
 *
 */
public class Game {

    private Queue queue;
    private int moveTime;
    private Random rand;

    /**
     * Synchronizuje ruch wilka
     * @param wolf
     * @return
     */
    synchronized boolean moveWolf(Wolf wolf){
        boolean b;

        while(queue.getNumber() != 0){
            try{
                wait();
            }catch (InterruptedException e){
                System.out.println("Ohoho Wolf");
            }
        }

        b = wolf.move();

        try{
            Thread.sleep((int) (moveTime*(0.5 + rand.nextDouble())));
        }catch(InterruptedException e){
            System.out.println("Wolf interrupted");
        }
        queue.put();
        notifyAll();
        return b;
    }

    /**
     * Synchronizuje ruch owcy
     * @param sheep
     * @param sheepNumber
     */
    synchronized void moveSheep(Sheep sheep, int sheepNumber){
        while(queue.getNumber() != sheepNumber){
            try{
                wait();
            }catch (InterruptedException e){
                System.out.println("Ohohoho Sheep " + sheepNumber);
            }
        }
        if(sheep.isAlive())
            sheep.move();

        try{
            Thread.sleep((int) (moveTime*(0.5 + rand.nextDouble())));
        }catch(InterruptedException e){
            System.out.println("Sheep " + sheepNumber + " interrupted");
        }
        queue.put();
        notifyAll();

    }
    Game(int n, int moveTime){
        this.queue = new Queue(n);
        this.moveTime = moveTime;
        rand = new Random();
    }
}
