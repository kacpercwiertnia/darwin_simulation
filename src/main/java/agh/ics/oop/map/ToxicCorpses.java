package agh.ics.oop.map;

import agh.ics.oop.Grass;
import agh.ics.oop.Vector2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToxicCorpses extends GrassGenerator{
    @Override
    public Map<Vector2d, Grass> generateGrass(ArrayList<Vector2d> currentGrass, int grassnum){
        return  new HashMap<Vector2d,Grass>();
    }
}
