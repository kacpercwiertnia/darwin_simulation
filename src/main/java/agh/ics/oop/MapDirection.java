package agh.ics.oop;

public enum MapDirection {
    NORTH,
    NORTHEAST,
    WEST,


    WEST,
    EAST;


    public String toString(){
        switch (this){
            case EAST:
                return "Wschód";
            case WEST:
                return "Zachód";
            case NORTH:
                return "Północ";
            case SOUTH:
                return "Południe";
            default:
                return "Błąd";
        }
    }

    public MapDirection next(){
        switch (this){
            case NORTH:
                return EAST;
            case EAST:
                return SOUTH;
            case SOUTH:
                return WEST;
            case WEST:
                return NORTH;
            default:
                return null;
        }
    }

    public MapDirection previous(){
        switch (this){
            case NORTH:
                return WEST;
            case WEST:
                return SOUTH;
            case SOUTH:
                return EAST;
            case EAST:
                return NORTH;
            default:
                return null;
        }
    }

    public Vector2d toUnitVector(){
        switch (this){
            case NORTH:
                return new Vector2d(0,1);
            case WEST:
                return new Vector2d(-1,0);
            case SOUTH:
                return new Vector2d(0,-1);
            case EAST:
                return new Vector2d(1,0);
            default:
                return null;
        }
    }
}
