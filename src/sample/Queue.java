package sample;
/**
 * Projekt - symulacja wilka i owiec
 * Queue.java
 * Klasa tworzy kolejkę wykonywania wątków, dzięki której zwierzęta poruszają się w określonym porządku
 *
 * @author Szymon Wojtaszek 236592
 * @version Lepiej nie będzie
 *
 */
class Queue {
    private int number;
    private int mod;

    /**
     * Oddaje numer aktualnie dostępny do ruchu
     * @return numer zwierzęcia, który się teraz poruszy
     */
    int getNumber(){
        return number;
    }

    /**
     * Przesuwa kolejkę dalej
     */
    void put(){
        if(number == mod)
            number = 0;
        else
            number++;
    }



    Queue(int mod){
        number = 0;
        this.mod = mod;
    }

}
