package com.boardgames.nmm;

import org.json.JSONObject;

public interface StateObserver {
	
	public void notify(JSONObject o);

}
