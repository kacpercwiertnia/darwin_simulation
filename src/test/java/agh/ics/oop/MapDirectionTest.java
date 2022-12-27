package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapDirectionTest {
    MapDirection n = MapDirection.NORTH;
    MapDirection ne = MapDirection.NORTHEAST;
    MapDirection e = MapDirection.EAST;
    MapDirection se = MapDirection.SOUTHEAST;
    MapDirection s = MapDirection.SOUTH;
    MapDirection sw = MapDirection.SOUTHWEST;
    MapDirection w = MapDirection.WEST;
    MapDirection nw = MapDirection.NORTHWEST;


    @Test
    void testToString() {
        assertEquals(n.toString(),"N");
        assertEquals(ne.toString(),"NE");
        assertEquals(e.toString(),"E");
        assertEquals(se.toString(),"SE");
        assertEquals(s.toString(),"S");
        assertEquals(sw.toString(),"SW");
        assertEquals(w.toString(),"W");
        assertEquals(nw.toString(),"NW");
    }

    @Test
    void toVector2d() {
        assertEquals(n.toVector2d(),new Vector2d(0,1));
        assertEquals(ne.toVector2d(),new Vector2d(1,1));
        assertEquals(e.toVector2d(),new Vector2d(1,0));
        assertEquals(se.toVector2d(),new Vector2d(1,-1));
        assertEquals(s.toVector2d(),new Vector2d(0,-1));
        assertEquals(sw.toVector2d(),new Vector2d(-1,-1));
        assertEquals(w.toVector2d(),new Vector2d(-1,0));
        assertEquals(nw.toVector2d(),new Vector2d(-1,1));
    }

    @Test
    void next() {
        assertEquals(n.next(),ne);
        assertEquals(ne.next(),e);
        assertEquals(e.next(),se);
        assertEquals(se.next(),s);
        assertEquals(s.next(),sw);
        assertEquals(sw.next(),w);
        assertEquals(w.next(),nw);
        assertEquals(nw.next(),n);
    }

    @Test
    void previous() {
        assertEquals(n.previous(),nw);
        assertEquals(nw.previous(),w);
        assertEquals(w.previous(),sw);
        assertEquals(sw.previous(),s);
        assertEquals(s.previous(),se);
        assertEquals(se.previous(),e);
        assertEquals(e.previous(),ne);
        assertEquals(ne.previous(),n);
    }
    @Test
    void turn(){
        System.out.println(w.turn(0));
        System.out.println(w.turn(1));
        System.out.println(w.turn(2));
        System.out.println(w.turn(3));
        System.out.println(w.turn(4));
        System.out.println(w.turn(5));
        System.out.println(w.turn(6));
        System.out.println(w.turn(7));

    }
}