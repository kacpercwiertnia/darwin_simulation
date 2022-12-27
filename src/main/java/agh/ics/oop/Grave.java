package agh.ics.oop;

public class Grave {
    public final Vector2d position;
    public int corpses=0;
    public Grave(Vector2d position){
        this.position=position;
    }
    public void addCorpse(){
        this.corpses++;
    }
}
