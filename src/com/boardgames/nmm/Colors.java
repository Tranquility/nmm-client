package com.boardgames.nmm;

public enum Colors {
	BLACK(0xFF000000), 
	TAN(0xFFEE9A49),
	WHITE(0xFFFFFFFF), 
	TRANS_BLACK(0xBB000000),
	TRANSPARENT(0x00000000);

	private final int _color;

	Colors(int color) {
		_color = color;
	}

	public int getColor() {
		return _color;
	}
}
