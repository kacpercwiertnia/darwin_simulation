package agh.ics.oop.map;

import agh.ics.oop.Animal;
import agh.ics.oop.Grass;
import agh.ics.oop.Vector2d;

import java.util.HashMap;
import java.util.Map;

public class AbstractWorldMap {
    int width,height,grassnum;
    GrassGenerator grassField;
    Map<Vector2d,Animal> animals = new HashMap<>();
    Map<Vector2d, Grass> plants = new HashMap<>();

    public void place(Animal animal,Vector2d position){
        if (!isOccupied(position)){
            this.animals.put(position,animal);
        }
    }
    public boolean isOccupied(Vector2d position){
        return animals.containsKey(position);
    }
    public Animal objectAt(Vector2d position){
        if (this.animals.containsKey(position)){
            return this.animals.get(position);
        }
        return null;
    }

    public Vector2d getLowerLeft(){
        return new Vector2d(0,0);
    }
    public Vector2d getUpperRight(){
        return new Vector2d(this.width-1,this.height-1);
    }
}
