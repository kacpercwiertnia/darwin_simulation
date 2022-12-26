package agh.ics.oop.map;

import agh.ics.oop.Grass;
import agh.ics.oop.Vector2d;

import java.util.Map;

public enum GeneratorType {
    FORESTED_EQUATOR,
    TOXIC_CORPSES;

    public GrassGenerator getGenerator(int width, int height, int grassToGenerate, Map<Vector2d, Grass> presentGrass){
        return switch(this){
            case TOXIC_CORPSES -> new ToxicCorpses();
            case FORESTED_EQUATOR -> new ForestedEquator(width, height,grassToGenerate);
        };
    }
}
