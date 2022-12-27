package agh.ics.oop.map;

import agh.ics.oop.Vector2d;

import java.util.ArrayList;
import java.util.Random;

public class HellPortal extends AbstractWorldMap {
    public HellPortal(int width, int height, int grassnum, GeneratorType generator){
        this.height=height;
        this.width=width;
        this.grassField=generator.getGenerator(width,height,grassnum);
        this.grassnum=grassnum;
        this.plants=this.grassField.generateGrass(new ArrayList<>(this.plants.keySet()),grassnum);
    }
    public Vector2d canMoveTo(Vector2d position, Vector2d movement){
        Vector2d newposition=position.add(movement);
        if ( (!newposition.follows(getLowerLeft()) ) || (!newposition.precedes(getUpperRight())) ){
            Random rn = new Random();
            int x= rn.nextInt(this.width);
            int y= rn.nextInt(this.width);
            return new Vector2d(x,y);
        }
        return newposition;
    }
}
