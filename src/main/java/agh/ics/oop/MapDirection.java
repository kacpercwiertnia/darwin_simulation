package agh.ics.oop;

import java.util.Random;

public enum MapDirection {
    NORTH("N"),
    NORTHEAST("NE"),
    EAST("E"),
    SOUTHEAST("SE"),
    SOUTH("S"),
    SOUTHWEST("SW"),
    WEST("W"),
    NORTHWEST("NW");

    private final String name;
    MapDirection(String name){
        this.name=name;
    }

    @Override
    public String toString(){
        return this.name;
    }

    public Vector2d toVector2d(){
        return switch (this){
            case NORTH -> new Vector2d(0,1);
            case NORTHEAST -> new Vector2d(1,1);
            case EAST-> new Vector2d(1,0);
            case SOUTHEAST -> new Vector2d(1,-1);
            case SOUTH -> new Vector2d(0,-1);
            case SOUTHWEST -> new Vector2d(-1,-1);
            case WEST -> new Vector2d(-1,0);
            case NORTHWEST -> new Vector2d(-1,1);
        };
    }

    public MapDirection next(){
        MapDirection[] directions=this.values();
        return directions[(this.ordinal()+1)% directions.length];
    }

    public MapDirection previous(){
        MapDirection[] directions=this.values();
        return directions[(this.ordinal()+directions.length-1)% directions.length];
    }
    public MapDirection getRandom(){
        Random rn=new Random();
        return this.values()[rn.nextInt(0,8)];
    }
}
