package agh.ics.oop;

public class Animal {
    private MapDirection direction;
    private Vector2d position;
    private final Genotype genotype;
    private int health;
    //private final int birthDate;

    public Animal(Genotype genotype, Vector2d position, int health){
        this.health = health;
        this.position = position;
        this.genotype = genotype;

    }

    public int getHealth() {
        return this.health;
    }

    public Genotype getGenotype(){
        return this.genotype;
    }
}
