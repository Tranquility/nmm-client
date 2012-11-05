package com.boardgames.nmm;

public interface StateObserver {

	public void notify(int oldField, int newField, int delField);

}
