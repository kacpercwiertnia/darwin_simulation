package agh.ics.oop;

public class Grave {
    public final Vector2d position;
    public int corpses;
    public Grave(Vector2d position){
        this.position=position;
        this.corpses=0;
    }
    public void addCorpse(){
        this.corpses++;
    }
}
