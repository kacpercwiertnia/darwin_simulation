package agh.ics.oop.map;

import agh.ics.oop.Grass;
import agh.ics.oop.Vector2d;

import java.util.*;

public class ForestedEquator extends GrassGenerator{
    private Vector2d prefLowLeft;
    private Vector2d prefTopRight;
    public ForestedEquator(int width, int height, int grassToGenerate){
        this.width=width;
        this.height=height;
        this.plants=grassToGenerate;
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

    }

    public Map<Vector2d,Grass> generateGrass(ArrayList<Vector2d> currentGrass, int grassnum){
        int j=0,i=0,x,y;

        Random rn = new Random();
        Vector2d v;
        while (i<grassnum*0.8 && j<100){
            j++;
            x=rn.nextInt(prefLowLeft.x,prefTopRight.x+1);
            y=rn.nextInt(prefLowLeft.y, prefTopRight.y+1);
            v = new Vector2d(x,y);
            if (!currentGrass.contains(v) && v.follows(prefLowLeft)&&v.precedes(prefTopRight)){
                currentGrass.add(v);
                i++;
                j=0;
            }
        }
        j=0;
        while (i<grassnum && j<100){
            j++;
            x=rn.nextInt(0,this.width);
            y=rn.nextInt(0,this.height);
            v = new Vector2d(x,y);
            if (!currentGrass.contains(v) && (v.y<prefLowLeft.y || v.y> prefTopRight.y)){
                currentGrass.add(v);
                i++;
                j=0;
            }
        }
        Map<Vector2d,Grass> map= new HashMap<>();
        for (Vector2d vector:currentGrass){
            map.put(vector,new Grass());
        }
        return map;


    }
}
