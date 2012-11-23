package com.boardgames.nmm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GamesActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_games);
		getGames();
	}

	/**
	 * This methods initiates a GET request to fetch the opponents latest move
	 * from the server.
	 */
	private void getGames() {
		new AsyncTask<Void, Void, JSONArray>() {

			@Override
			protected JSONArray doInBackground(Void... params) {
				String url = "http://nmm.ole-reifschneider.de/games.json";
				return NetworkManager.getJsonArray(url);
			}

			protected void onPostExecute(JSONArray result) {
				createTables(result);
			}
		}.execute();

	}

	private void createTables(JSONArray games) {
		System.out.println(games.toString());
		// Get the TableLayout
		TableLayout tl = (TableLayout) findViewById(R.id.gametable);

		// Go through each item in the array
		for (int current = 0; current < games.length(); current++) {
			TableRow tr = new TableRow(this);
			TextView gameLabel = new TextView(this);
			String player = "";
			JSONObject currentObject;
			try {
				currentObject = games.getJSONObject(current);
				if (currentObject.optString("player_white") != null) {
					player = currentObject.optString("player_white").toString();
				} else {
					player = currentObject.optString("player_black").toString();
				}

				gameLabel.setText(player);
				tr.addView(gameLabel);

				Button btn = new Button(this);
				btn.setText("Join");
				int gameId = currentObject.getInt("id");
				btn.setId(gameId);
				btn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(GamesActivity.this,
								BoardActivity.class);
						startActivity(intent);
						System.out.println(v.getId());
					}
				});
				tr.addView(btn);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Add the TableRow to the TableLayout
			tl.addView(tr);
		}
	}
}
