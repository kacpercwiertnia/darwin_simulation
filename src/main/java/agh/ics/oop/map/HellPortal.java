package agh.ics.oop.map;

public class HellPortal extends AbstractWorldMap {
    public HellPortal(int width, int height, int grassnum, GrassGenerator grassField){
        this.height=height;
        this.width=width;
        this.grassField=grassField;
        this.grassnum=grassnum;
    }
}
