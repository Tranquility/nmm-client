package com.boardgames.nmm;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;

public class BoardActivity extends Activity {

	private Board _board;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_board = new Board();
		BoardView view = new BoardView(this, _board);

		setContentView(view);

		registerObservers();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/**
	 * Registers one observer that sends a GET request after being notified and
	 * another one that sends a POST request after being notified.
	 */
	private void registerObservers() {
		_board.registerGetObserver(new StateObserver() {

			public void notify(JSONObject o) {
				getLatestMove();
			}
		});

		_board.registerPostObserver(new StateObserver() {

			public void notify(JSONObject o) {
				postLatestMove(o);
				_board.waitState();
			}
		});
	}

	/**
	 * This methods initiates a POST request to forward the users latest move to
	 * the server.
	 */
	private void postLatestMove(final JSONObject o) {

		new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... params) {
				String url = "http://nmm.ole-reifschneider.de/moves.json";
				return NetworkManager.postJson(url, o);
			}
		}.execute();
	}

	/**
	 * Starts a timer that creates a new GetRequest that checks for changes on
	 * the server
	 */
	public void getLatestMove() {
		Timer timer;
		timer = new Timer();
		timer.schedule(new GetRequest(), 0, 5 * 1000);
	}

	class GetRequest extends TimerTask {

		/**
		 * This methods initiates a GET request to fetch the opponents latest
		 * move from the server.
		 */
		public void run() {

			new AsyncTask<Void, Void, JSONArray>() {

				@Override
				protected JSONArray doInBackground(Void... params) {
					String url = "http://nmm.ole-reifschneider.de/moves.json";
					return NetworkManager.getJson(url);
				}

				protected void onPostExecute(JSONArray result) {
					try {
						_board.move(result.getJSONObject(0));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}.execute();
		}
	}
}
