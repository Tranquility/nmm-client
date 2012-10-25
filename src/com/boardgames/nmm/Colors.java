package com.boardgames.nmm;

public enum Colors {
	BLACK(0xFF000000), 
	DARKBLUE(0xFF605CA8), 
	GREEN(0xFF7E9D1E), 
	GREY(0xFF888888), 
	LIGHTBLUE(0xFF46B1E2), 
	PINK(0xFFED70A1), 
	RED(0xFFDC4A20), 
	YELLOW(0xFFF3F61D), 
	WHITE(0xFFFFFFFF), 
	TRANS_BLACK(0xBB000000);

	private final int _color;

	Colors(int color) {
		_color = color;
	}

	public int getColor() {
		return _color;
	}
}
