package com.boardgames.nmm;

import com.boardgames.nmm.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class BoardActivity extends Activity {

	private Position _a1, _a4, _a7, _b2, _b4, _b6, _c3, _c4, _c5, _d1, _d2,
			_d3, _d5, _d6, _d7, _e3, _e4, _e5, _f2, _f4, _f6, _g1, _g4, _g7;

	private ObservableState _currentState;
	private ObservableState _lastState;
	private ObservableState _stateWait;
	private ObservableState _statePutting;
	private ObservableState _stateMoving;
	private ObservableState _stateFlying;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initializeStates();
		initializeField();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/**
	 * Registers the observers that have to be notified if the game is in a
	 * state where the player is waiting for the opponent.
	 */
	public void registerGetObserver(StateObserver so) {
		_stateWait.registerObserver(so);
	}

	/**
	 * Registers the observers that have to be notified if the game is in a
	 * state where the player has made a valid move.
	 * 
	 * @param so
	 */
	public void registerPostObserver(StateObserver so) {
		_statePutting.registerObserver(so);
		_stateMoving.registerObserver(so);
		_stateFlying.registerObserver(so);
	}

	public void move(String oldField, String newField, int playerId) {
		// TODO: Do something with the data
		// It may be necessary to change the move method of the abstract class
		// ObservableState
		_currentState.move(oldField, newField, playerId);
	}

	
	public void waitState() {
		_lastState = _currentState;
		_currentState = _stateWait;
	}

	private void initializeStates() {
		_stateWait = new GameStateWaiting();
		_statePutting = new GameStatePutting();
		_stateMoving = new GameStateMoving();
		_stateFlying = new GameStateFlying();
	}

	private void initializeField() {
		_a1 = new Position("a1", _a4, _d1, null, null);
		_a4 = new Position("a4", _a7, _b4, _a1, null);
		_a7 = new Position("a7", null, _d7, _a4, null);
		_b2 = new Position("b2", _b4, _d2, null, null);
		_b4 = new Position("b4", _b6, _c4, _b2, _a4);
		_b6 = new Position("b6", null, _d6, _b4, null);
		_c3 = new Position("c3", _c4, _d3, null, null);
		_c4 = new Position("c4", _c5, null, _c3, _b4);
		_c5 = new Position("c5", null, _d5, _c4, null);
		_d1 = new Position("d1", _d2, _g1, null, _a1);
		_d2 = new Position("d2", _d3, _f2, _d1, _b2);
		_d3 = new Position("d3", null, _e3, _d2, _c3);
		_d5 = new Position("d5", _d6, _e5, null, _c5);
		_d6 = new Position("d6", _d7, _f6, _d5, _b6);
		_d7 = new Position("d7", null, _g7, _d6, _a7);
		_e3 = new Position("e3", _e4, null, null, _d3);
		_e4 = new Position("e4", _e5, _f4, _e3, null);
		_e5 = new Position("e5", null, null, _e4, _d5);
		_f2 = new Position("f2", _f4, null, null, _d2);
		_f4 = new Position("f4", _f6, _g4, _f2, _e4);
		_f6 = new Position("f6", null, null, _f4, _d6);
		_g1 = new Position("g1", _g4, null, null, _d1);
		_g4 = new Position("g4", _g7, null, _g1, _f4);
		_g7 = new Position("g7", null, null, _g4, _d7);
	}

	/**
	 * This state is active while the player is waiting for the opponents move.
	 * During this state the player cannot move or put any of his stones.
	 */
	class GameStateWaiting extends ObservableState {

		@Override
		public void onTouch() {

		}

		@Override
		public void move(String oldField, String newField, int playerId) {

		}

		@Override
		public void next() {
			_currentState = _lastState;
		}
	}

	/**
	 * This state is active during the first phase of the game. The player can
	 * place each of his 9 stones on any free spot on the board. The two players
	 * are alternately placing their stones, therefore the GameStateWait is
	 * activated after each move.
	 */
	class GameStatePutting extends ObservableState {

		@Override
		public void onTouch() {

		}

		@Override
		public void move(String oldField, String newField, int playerId) {

		}

		@Override
		public void next() {
			_currentState = _stateWait;
			_lastState = this;
		}
	}

	/**
	 * This state is active during the second phase. The player can move his
	 * stones from one spot to the next, but only along the board lines. The two
	 * players are alternately placing their stones, therefore the GameStateWait
	 * is activated after each move.
	 */
	class GameStateMoving extends ObservableState {

		@Override
		public void onTouch() {

		}

		@Override
		public void move(String oldField, String newField, int playerId) {

		}

		@Override
		public void next() {
			_currentState = _stateWait;
			_lastState = this;
		}
	}

	/**
	 * This state is active during the third phase. The player has only three
	 * stones left, but can place them on any available spot.
	 */
	class GameStateFlying extends ObservableState {

		@Override
		public void onTouch() {

		}

		@Override
		public void move(String oldField, String newField, int playerId) {

		}

		@Override
		public void next() {
			_currentState = _stateWait;
			_lastState = this;
		}
	}
}
