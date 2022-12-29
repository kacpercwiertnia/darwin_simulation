package agh.ics.oop.map;

import agh.ics.oop.Grass;
import agh.ics.oop.Grave;
import agh.ics.oop.Vector2d;

import java.util.*;
import java.util.Comparator;
import java.util.TreeSet;

public class ToxicCorpses extends GrassGenerator{

    @Override
    public Map<Vector2d, Grass> generateGrass(ArrayList<Vector2d> currentGrass, int grassnum, Grave[] graveyard) {
        int i = 0, j = 0, k = 0;

        while (i < grassnum) {


            ArrayList<Vector2d> potential = new ArrayList<>();
            for (Grave grave : graveyard) {
                if (!currentGrass.contains(grave.position) && grave.corpses == j) {
                    potential.add(grave.position);
                }
            }


            if (potential.size() < grassnum) {
                i += potential.size();
                j++;
                currentGrass.addAll(potential);
            }

            else if (potential.size() == grassnum) {
                currentGrass.addAll(potential);
                i+=potential.size();
                return makeGrassList(currentGrass);
            }
            else {
                Random rn = new Random();
                int x;
                while (i < grassnum) {
                    x = rn.nextInt(potential.size());
                    if (!currentGrass.contains(potential.get(x))) {
                        i++;
                        currentGrass.add(potential.get(x));
                    }
                }

            }

        }
        return makeGrassList(currentGrass);
    }




    private Map<Vector2d,Grass> makeGrassList(ArrayList<Vector2d> grasslist){
        Map<Vector2d,Grass> toReturn = new HashMap<>();
        for (Vector2d position:grasslist){
            toReturn.put(position,new Grass());
        }
        return toReturn;
    }
}
