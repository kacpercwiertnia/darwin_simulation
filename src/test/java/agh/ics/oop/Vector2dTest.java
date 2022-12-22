package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {
    Vector2d a=new Vector2d(4,7);
    Vector2d b = new Vector2d(4,8);
    Vector2d c = new Vector2d(8,8);
    @Test
    void testToString() {
        assertEquals("(4,7)",a.toString());
        assertEquals("(4,8)",b.toString());
        assertEquals("(8,8)",c.toString());
    }

    @Test
    void precedes() {
        assertTrue(a.precedes(b));
        assertTrue(a.precedes(c));
        assertTrue(b.precedes(c));
        assertFalse(b.precedes(a));
        assertFalse(c.precedes(a));
        assertFalse(c.precedes(b));
        assertTrue(b.precedes(b));
        assertTrue(c.precedes(c));
        assertTrue(a.precedes(a));

    }

    @Test
    void follows() {
        assertTrue(b.follows(a));
        assertTrue(c.follows(a));
        assertTrue(c.follows(b));
        assertFalse(a.follows(b));
        assertFalse(a.follows(c));
        assertFalse(b.follows(c));
        assertTrue(a.follows(a));
        assertTrue(c.follows(c));
        assertTrue(b.follows(b));
    }

    @Test
    void upperRight() {
        assertEquals(new Vector2d(8,8),c.upperRight(a));
        assertEquals(new Vector2d(8,8),c.upperRight(b));
        assertEquals(new Vector2d(4,8),b.upperRight(a));
        assertEquals(new Vector2d(8,8),a.upperRight(c));
        assertEquals(new Vector2d(8,8),b.upperRight(c));
        assertEquals(new Vector2d(4,8),a.upperRight(b));
        assertEquals(new Vector2d(8,8),c.upperRight(c));
        assertEquals(new Vector2d(4,8),b.upperRight(b));
        assertEquals(new Vector2d(4,7),a.upperRight(a));

    }

    @Test
    void lowerLeft() {
        assertEquals(new Vector2d(4,7),a.lowerLeft(b));
        assertEquals(new Vector2d(4,7),a.lowerLeft(c));
        assertEquals(new Vector2d(4,7),c.lowerLeft(a));
        assertEquals(new Vector2d(4,7),b.lowerLeft(a));
        assertEquals(new Vector2d(4,8),b.lowerLeft(c));
        assertEquals(new Vector2d(4,8),c.lowerLeft(b));
        assertEquals(new Vector2d(8,8),c.lowerLeft(c));
        assertEquals(new Vector2d(4,8),b.lowerLeft(b));
        assertEquals(new Vector2d(4,7),a.lowerLeft(a));
    }

    @Test
    void add() {
        assertEquals(new Vector2d(12,16),c.add(b));
        assertEquals(new Vector2d(12,16),b.add(c));
        assertEquals(new Vector2d(12,15),c.add(a));
        assertEquals(new Vector2d(12,15),a.add(c));
    }

    @Test
    void subtract() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void opposite() {
        assertEquals(new Vector2d(-4,-7),a.opposite());
        assertEquals(new Vector2d(-4,-8),b.opposite());
        assertEquals(new Vector2d(-8,-8),c.opposite());
    }
}