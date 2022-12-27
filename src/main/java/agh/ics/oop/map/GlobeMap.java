package agh.ics.oop.map;

import agh.ics.oop.Vector2d;

import java.util.ArrayList;

public class GlobeMap extends AbstractWorldMap{
    public GlobeMap(int width, int height, int grassnum, GeneratorType generator){
        this.height=height;
        this.width=width;
        this.grassField=generator.getGenerator(width,height,grassnum);
        this.grassnum=grassnum;
        this.plants=this.grassField.generateGrass(new ArrayList<>(this.plants.keySet()),grassnum);
    }

    public Vector2d canMoveTo(Vector2d position, Vector2d movement){
        Vector2d newposition=position.add(movement);
        if ((newposition.y>=this.height) || (newposition.y<0)){
            return position;
        }
        return  new Vector2d((newposition.x+this.width)%this.width,newposition.y);

    }
}
