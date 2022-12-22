package agh.ics.oop.map;

import agh.ics.oop.Grass;
import agh.ics.oop.Vector2d;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForestedEquator extends GrassGenerator{
    private Vector2d prefLowLeft;
    private Vector2d prefTopRight;
    public ForestedEquator(int width, int height, int grassToGenerate, Map<Vector2d, Grass> presentGrass){
        this.width=width;
        this.height=height;
        this.plants=grassToGenerate;
        this.coords=presentGrass.keySet().stream().toList();
        getPreferedArea(width,height);
    }
    private void getPreferedArea(int width,int height){
        int area=(int) (width*height*0.2);
        int h=area/width;
        this.prefTopRight=new Vector2d(width-1,height/2+h/2-1);
        this.prefLowLeft=new Vector2d(0,height/2-h/2);
        if (this.prefLowLeft.y>this.prefTopRight.y){
            this.prefTopRight=new Vector2d(prefTopRight.x, prefLowLeft.y);
        }
        System.out.println(prefTopRight);
        System.out.println(prefLowLeft);
    }

    public Map<Vector2d,Grass> generateGrass(List<Vector2d> currentGrass, int grassnum){
        this.coords=currentGrass;
        int i=0;
        boolean flag;
        return new HashMap<Vector2d,Grass>();


    }
}
