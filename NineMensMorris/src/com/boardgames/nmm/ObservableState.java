package com.boardgames.nmm;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public abstract class ObservableState {
	
	private List<StateObserver> _observers;

	public ObservableState() {
		_observers = new ArrayList<StateObserver>();
	}
	
	public void registerObserver(StateObserver so) {
		_observers.add(so);
	}
	
	public void notifyObservers(JSONObject o) {
		for (StateObserver so : _observers) {
			so.notify(o);
		}
	}
	
	public abstract void onTouch();
	
	public abstract void move();
	
	public abstract void next();
	
}
