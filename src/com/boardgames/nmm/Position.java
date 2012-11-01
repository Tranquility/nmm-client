package com.boardgames.nmm;

public class Position {

    private Position _neighborNorth;
    private Position _neighborEast;
    private Position _neighborSouth;
    private Position _neighborWest;

    private String _positionName;

    private Stone _stone = null;

    public Position(String name) {
        _positionName = name;
    }

    public Position(String name, Position n, Position e, Position s, Position w) {
        _positionName = name;
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

    public String getPositionName() {
        return _positionName;
    }

    public boolean isNeighbor(String position) {
        return (position.equals(_neighborEast.getPositionName())
                || position.equals(_neighborWest.getPositionName())
                || position.equals(_neighborNorth.getPositionName()) 
                || position.equals(_neighborSouth.getPositionName()));
    }

    public boolean isEmpty() {
        return _stone == null;
    }

}
