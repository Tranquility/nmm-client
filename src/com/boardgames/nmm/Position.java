package com.boardgames.nmm;

import java.util.ArrayList;
import java.util.List;

public class Position {

    private List<Position> _neighbors;

    private Stone _stone = null;
    
    public Position() {
    	_neighbors = new ArrayList<Position>();
    }

    public void setNeighbors(Position... neighbors) {
    	for (Position p : neighbors) {
    		_neighbors.add(p);
    	}
    }

    public void setStone(Stone s) {
        _stone = s;
    }
    
    public Stone getStone() {
    	return _stone;
    }

    public boolean isNeighbor(Position position) {
        return _neighbors.contains(position);
    }

    public boolean isEmpty() {
        return _stone == null;
    }

}
