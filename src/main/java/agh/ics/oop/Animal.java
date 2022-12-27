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

    public Animal(Genotype genotype, Vector2d position, int health, AbstractWorldMap map){
        Random rn = new Random();
        this.direction = MapDirection.values()[rn.nextInt(0,8)];
        this.health = health;
        this.position = position;
        this.genotype = genotype;
        this.map = map;
        this.birthDate = this.map.getAge();
        //System.out.println(this.genotype.toString());
        //System.out.println(this.direction.toString());
        //System.out.println(this.position.toString());

    }

    public int getHealth() {
        return this.health;
    }

    public Genotype getGenotype(){
        return this.genotype;
    }

    public Vector2d getPosition(){
        return this.position;
    }

    public MapDirection getDirection(){
        return this.direction;
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
    }
}
