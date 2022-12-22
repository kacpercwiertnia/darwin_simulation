package agh.ics.oop;

import agh.ics.oop.map.AbstractWorldMap;
import agh.ics.oop.map.GrassGenerator;
import agh.ics.oop.map.HellPortal;
import agh.ics.oop.map.MapVisualizer;

public class main {
    public static void main(String[] args){
        Animal doggy= new Animal(new Genotype(8),new Vector2d(4,8),20);
        AbstractWorldMap map= new HellPortal(20,20,new GrassGenerator());
        map.place(doggy,doggy.getPosition());
        MapVisualizer mapVisualizer = new MapVisualizer(map);
        System.out.println(mapVisualizer.draw(map.getLowerLeft(),map.getUpperRight()));
    }


}
