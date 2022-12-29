package agh.ics.oop;

import agh.ics.oop.gui.IMapRefreshObserver;
import agh.ics.oop.map.AbstractWorldMap;
import agh.ics.oop.map.MapVisualizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationEngine implements Runnable{
    private final AbstractWorldMap map;
    private List<Animal> animals;
    private final int numOfAnimals;
    private final int lengthOfGenotype;
    private final MovementType movementType;
    private final int healthOfAnimal;
    private final int energyFromGrass;
    private final int dailyGrass;
    private MapVisualizer mapVisualizer;
    private final List<IMapRefreshObserver> observers;

    public SimulationEngine( AbstractWorldMap map, int numOfAnimals, int lengthOfGenotype, MovementType movementType, int healthOfAnimal, int energyFromGrass,int dailyGrass){
        this.map = map;
        this.numOfAnimals = numOfAnimals;
        this.lengthOfGenotype = lengthOfGenotype;
        this.movementType = movementType;
        this.healthOfAnimal = healthOfAnimal;
        this.animals = new ArrayList<>();
        this.energyFromGrass = energyFromGrass;
        this.mapVisualizer=new MapVisualizer(map);
        this.observers = new ArrayList<IMapRefreshObserver>();
        this.dailyGrass=dailyGrass;
        Random rn = new Random();

        for( int i = 0; i < this.numOfAnimals; i++) {
            Vector2d position = new Vector2d( (rn.nextInt(map.getUpperRight().x+1)), (rn.nextInt(map.getUpperRight().y+1)));
            Animal animal = new Animal(new Genotype(this.lengthOfGenotype, this.movementType), position, this.healthOfAnimal, this.map);
            this.animals.add(animal);
            this.map.place(animal, position);
        }
    }

    public void run(){
        try {
            System.out.println(this.mapVisualizer.draw(map.getLowerLeft(), map.getUpperRight()));
            while(true) {
                this.animals = this.map.clearCorpses();
                mapRefresh();
                Thread.sleep(50);
                for (Animal animal : animals) {
                    animal.move();

                }
                mapRefresh();
                Thread.sleep(50);
                map.eatingTime(this.energyFromGrass);
                mapRefresh();
                Thread.sleep(50);
                this.animals=map.reproduction(1,lengthOfGenotype,MutationType.BLESSRNG,movementType);
                mapRefresh();
                Thread.sleep(50);
                this.map.generateGrass(this.dailyGrass);
                this.map.increaseAge();
                mapRefresh();
                Thread.sleep(50);
            }
        }
        catch (InterruptedException e){
            System.out.println("Interruption while waiting for animal move!");
        }
    }

    void mapRefresh(){
        for(IMapRefreshObserver observer: this.observers){
            observer.refresh();
        }
    }

    public void addObserver( IMapRefreshObserver observer){
        this.observers.add(observer);
    }
}
