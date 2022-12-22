package agh.ics.oop;

import agh.ics.oop.map.*;

import java.util.ArrayList;
import java.util.HashMap;

public class main {
    public static void main(String[] args){
        Animal doggy= new Animal(new Genotype(8),new Vector2d(4,8),20);
        AbstractWorldMap map= new HellPortal(3,3,10,new ForestedEquator(3,3,10,new HashMap<Vector2d,Grass>()));
        map.place(doggy,doggy.getPosition());
        MapVisualizer mapVisualizer = new MapVisualizer(map);
        System.out.println(mapVisualizer.draw(map.getLowerLeft(),map.getUpperRight()));
    }


}
