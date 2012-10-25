package com.boardgames.nmm;

import org.json.JSONException;
import org.json.JSONObject;

public class MoveConverter {
	
	private String _oldField;
	private String _newField;
	private Integer _playerId;
	private JSONObject _json = new JSONObject();

	
	public MoveConverter (JSONObject json) {
		try {
			_oldField = json.getString("old_field");
			_newField = json.getString("new_field");
			_playerId = json.getInt("player_id");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public MoveConverter (String oldField, String newField, int playerId) {
		try {
			_json.put("old_field", oldField);
			_json.put("new_field", newField);
			_json.put("player_id", playerId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String oldField() {
		return _oldField;
	}
	
	public String newField() {
		return _newField;
	}
	
	public Integer playerId() {
		return _playerId;
	}
	
	public JSONObject json() {
		return _json;
	}

}	
