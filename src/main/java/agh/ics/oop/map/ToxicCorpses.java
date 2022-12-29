package agh.ics.oop.map;

import agh.ics.oop.Grass;
import agh.ics.oop.Grave;
import agh.ics.oop.Vector2d;

import java.util.*;
import java.util.Comparator;
import java.util.TreeSet;

public class ToxicCorpses extends GrassGenerator{

    @Override
    public Map<Vector2d, Grass> generateGrass(ArrayList<Vector2d> currentGrass, int grassnum, Grave[] graveyard){
        int j=0;
        for (Grave grave:graveyard){
            if (!currentGrass.contains(grave.position)){
                currentGrass.add(grave.position);
                j++;
            }
            if (j==grassnum){
                return makeGrassList(currentGrass);
            }
        }
        return  makeGrassList(currentGrass);
    }

    private Map<Vector2d,Grass> makeGrassList(ArrayList<Vector2d> grasslist){
        Map<Vector2d,Grass> toReturn = new HashMap<>();
        for (Vector2d position:grasslist){
            toReturn.put(position,new Grass());
        }
        return toReturn;
    }
}
