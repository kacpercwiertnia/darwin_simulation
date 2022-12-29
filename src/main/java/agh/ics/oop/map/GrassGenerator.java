package agh.ics.oop.map;

import agh.ics.oop.Grass;
import agh.ics.oop.Grave;
import agh.ics.oop.Vector2d;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class GrassGenerator {
    protected int width,height,plants;
    abstract Map<Vector2d, Grass> generateGrass(ArrayList<Vector2d> currentGrass, int grassnum, Grave[] graveyard);
}
