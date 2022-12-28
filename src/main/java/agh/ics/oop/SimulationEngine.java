package agh.ics.oop;

import agh.ics.oop.map.AbstractWorldMap;
import agh.ics.oop.map.MapVisualizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationEngine {
    private final AbstractWorldMap map;
    private List<Animal> animals;
    private final int numOfAnimals;
    private final int lengthOfGenotype;
    private final MovementType movementType;
    private final int healthOfAnimal;
    private final int energyFromGrass;
    private MapVisualizer mapVisualizer;

    public SimulationEngine( AbstractWorldMap map, int numOfAnimals, int lengthOfGenotype, MovementType movementType, int healthOfAnimal, int energyFromGrass){
        this.map = map;
        this.numOfAnimals = numOfAnimals;
        this.lengthOfGenotype = lengthOfGenotype;
        this.movementType = movementType;
        this.healthOfAnimal = healthOfAnimal;
        this.animals = new ArrayList<>();
        this.energyFromGrass = energyFromGrass;
        this.mapVisualizer=new MapVisualizer(map);
        Random rn = new Random();

        for( int i = 0; i < this.numOfAnimals; i++) {
            Vector2d position = new Vector2d( (rn.nextInt(map.getUpperRight().x+1)), (rn.nextInt(map.getUpperRight().y+1)));
            Animal animal = new Animal(new Genotype(this.lengthOfGenotype, this.movementType), position, this.healthOfAnimal, this.map);
            this.animals.add(animal);
            this.map.place(animal, position);
        }
    }

    public void run(){
        System.out.println(this.mapVisualizer.draw(map.getLowerLeft(),map.getUpperRight()));
        for( int i = 0; i < 10; i++){
            this.animals = this.map.clearCorpses();
            for( Animal animal: animals){
                animal.move();
                map.eatingTime(this.energyFromGrass);
                /*System.out.println("POCZATEK RUCHU");
                System.out.println("Przed ruchem: " + animal.getPosition().toString() + ' ' + animal.getDirection().toString());
                System.out.println("Po ruchu/: " + animal.getPosition().toString() + ' ' + animal.getDirection().toString());
                System.out.println("KONIEC RUCHU");*/
            }
            System.out.println(this.mapVisualizer.draw(map.getLowerLeft(),map.getUpperRight()));
        }
    }
}
