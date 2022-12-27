package agh.ics.oop.map;

import agh.ics.oop.Grass;
import agh.ics.oop.Grave;
import agh.ics.oop.Vector2d;

import java.util.*;
import java.util.Comparator;
import java.util.TreeSet;

public class ToxicCorpses extends GrassGenerator{

    @Override
    public Map<Vector2d, Grass> generateGrass(ArrayList<Vector2d> currentGrass, int grassnum){
        return  new HashMap<Vector2d,Grass>();
    }
}
