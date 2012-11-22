package com.boardgames.nmm;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;

public class BoardActivity extends Activity {

	private Board _board;
	private BoardView _view;
	private Timer _timer;
	private String _token;
	private int _playerId = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SharedPreferences pref = getSharedPreferences("token",
				Context.MODE_PRIVATE);
		_token = pref.getString("token", null);

		_board = new Board();
		registerObservers();
		_board.setStartState();

		_view = new BoardView(this, _board);
		setContentView(_view);
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

			public void notify(int oldField, int newField, int delField) {
				getLatestMove();
			}
		});

		_board.registerPostObserver(new StateObserver() {

			public void notify(int oldField, int newField, int delField) {
				MoveConverter mc = new MoveConverter(oldField, newField,
						delField, _playerId);
				postLatestMove(mc.json());
			}
		});
	}

	/**
	 * This methods initiates a POST request to forward the users latest move to
	 * the server.
	 */
	private void postLatestMove(final JSONObject o) {

		new AsyncTask<Void, Void, JSONObject>() {

			@Override
			protected JSONObject doInBackground(Void... params) {
				String url = "http://nmm.ole-reifschneider.de/moves.json?auth_token="
						+ _token;
				return NetworkManager.postJson(url, o);
			}
		}.execute();
	}

	/**
	 * Starts a timer that creates a new GetRequest that checks for changes on
	 * the server
	 */

	public void getLatestMove() {
		System.out.println("Get");
		_timer = new Timer();
		_timer.schedule(new GetRequest(), 1500);
	}

	/**
	 * This methods initiates a GET request to fetch the opponents latest move
	 * from the server.
	 */
	class GetRequest extends TimerTask {

		public void run() {

			new AsyncTask<Void, Void, JSONObject>() {

				@Override
				protected JSONObject doInBackground(Void... params) {
					String url = "http://nmm.ole-reifschneider.de/moves.json?auth_token="
							+ _token + "&player_id=" + _playerId;
					return NetworkManager.getJson(url);
				}

				protected void onPostExecute(JSONObject result) {
					if (result == null) {
						_timer.schedule(new GetRequest(), 1000);
					} else {
						MoveConverter mc = new MoveConverter(result);
						_board.move(mc.oldField(), mc.newField(), mc.delField());

						_view.invalidate();
					}
				}
			}.execute();
		}
	}
}
