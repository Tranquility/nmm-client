package com.boardgames.nmm;

import java.util.List;

public class Position {

    private List<Position> _neighbors;

    private Stone _stone = null;

    public void setNeighbors(List<Position> neighbors) {
    	_neighbors = neighbors;
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
