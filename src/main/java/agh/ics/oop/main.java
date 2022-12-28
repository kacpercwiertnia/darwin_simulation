package agh.ics.oop;

import agh.ics.oop.map.*;

import java.util.ArrayList;
import java.util.HashMap;

public class main {
    public static void main(String[] args){
        GeneratorType generator=GeneratorType.FORESTED_EQUATOR;
        AbstractWorldMap map= new GlobeMap(20,20,20, generator);
//        Animal doggy= new Animal(new Genotype(8,0),new Vector2d(4,8),20,map);
//        map.place(doggy,doggy.getPosition());
//        map.moveAnimal(doggy,new Vector2d(4,2));
//        MapVisualizer mapVisualizer = new MapVisualizer(map);
//        System.out.println(mapVisualizer.draw(map.getLowerLeft(),map.getUpperRight()));
        SimulationEngine silnik= new SimulationEngine(map,8,5,MovementType.FULL_PREDESTINATION,5,10);
        silnik.run();
    }


}
