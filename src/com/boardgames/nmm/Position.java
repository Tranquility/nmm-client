package com.boardgames.nmm;

public class Position {

    private Position _neighborNorth;
    private Position _neighborEast;
    private Position _neighborSouth;
    private Position _neighborWest;

    private Stone _stone = null;

    public Position(Position n, Position e, Position s, Position w) {
        _neighborEast = e;
        _neighborNorth = n;
        _neighborSouth = s;
        _neighborWest = w;
    }

    public void setNeighborNorth(Position n) {
        _neighborNorth = n;
    }

    public void setNeighborEast(Position e) {
        _neighborEast = e;
    }

    public void setNeighborSouth(Position s) {
        _neighborSouth = s;
    }

    public void setNeighborWest(Position w) {
        _neighborWest = w;
    }

    public void setStone(Stone s) {
        _stone = s;
    }
    
    public Stone getStone() {
    	return _stone;
    }

    public boolean isNeighbor(Position position) {
        return (position == _neighborEast
                || position == _neighborWest
                || position == _neighborNorth 
                || position == _neighborSouth);
    }

    public boolean isEmpty() {
        return _stone == null;
    }

}
