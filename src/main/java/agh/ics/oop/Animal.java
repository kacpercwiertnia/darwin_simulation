package agh.ics.oop;

import agh.ics.oop.map.AbstractWorldMap;
import agh.ics.oop.map.GlobeMap;

import java.util.Random;

public class Animal {
    private MapDirection direction;
    private Vector2d position;
    private final Genotype genotype;
    private int health;
    private AbstractWorldMap map;
    private final int birthDate;
    private int children;
    private boolean isDead = false;
    private int deathDate;
    private int eatenGrass = 0;

    public Animal(Genotype genotype, Vector2d position, int health, AbstractWorldMap map){
        Random rn = new Random();
        this.direction = MapDirection.values()[rn.nextInt(0,8)];
        this.health = health;
        this.position = position;
        this.genotype = genotype;
        this.map = map;
        this.children=0;
        this.birthDate = this.map.getAge();
    }

    public int getHealth() {
        return this.health;
    }
    public Genotype getGenotype(){
        return this.genotype;
    }
    public int getChildren(){
        return this.children;
    }
    public void addChild(){
        this.children++;
    }
    public Vector2d getPosition(){
        return this.position;
    }
    public MapDirection getDirection(){
        return this.direction;
    }
    public int getAge(){
        if( this.isDead) {
            return this.deathDate - this.birthDate;
        }
        return this.map.getAge()-this.birthDate;
    }

    public int getDeathDate(){
        return this.deathDate;
    }

    public int getEatenGrass(){
        return this.eatenGrass;
    }
    @Override
    public String toString(){
        return this.direction.toString();
    }
    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public void move(){
        int nextMove = this.genotype.nextGene();
        this.direction = this.direction.turn(nextMove);
        Vector2d newpos = map.canMoveTo(this.position,this.direction.toVector2d());
        if(  newpos.x==this.position.x && newpos.y==this.position.y)
        {
            if( map instanceof GlobeMap ){
                this.direction = this.direction.turn(4);
            }
        }
        else{
            this.map.moveAnimal(this,newpos);
            this.position = newpos;
        }
        this.health -= 1;
    }

    public void eat(int energy){
        this.health += energy;
        this.eatenGrass++;
    }

    public void killAnimal(){
        this.isDead = true;
        this.deathDate = this.map.getAge();
    }

}
