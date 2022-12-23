package agh.ics.oop.map;

import java.util.ArrayList;

public class HellPortal extends AbstractWorldMap {
    public HellPortal(int width, int height, int grassnum, GrassGenerator grassField){
        this.height=height;
        this.width=width;
        this.grassField=grassField;
        this.grassnum=grassnum;
        this.plants=this.grassField.generateGrass(new ArrayList<>(this.plants.keySet()),grassnum);
        System.out.println(this.plants);
    }
}
