package com.boardgames.nmm;


public class Board {

	private Position[][] _positions;

	private ObservableState _currentState;
	private ObservableState _lastState;
	private ObservableState _stateWait;
	private ObservableState _statePutting;
	private ObservableState _stateMoving;
	private ObservableState _stateFlying;

	public Board() {
		initializeField();
		initializeStates();
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

	public Position[][] getPositions() {
		return _positions;
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
		Position a1, a4 = null, a7 = null, b2, b4 = null, b6 = null, c3, c4 = null, c5 = null, d1 = null, d2 = null, d3 = null, d5 = null, d6 = null, d7 = null, e3 = null, e4 = null, e5 = null, f2 = null, f4 = null, f6 = null, g1 = null, g4 = null, g7 = null;

		a1 = new Position("a1", null, d1, a4, null);
		a4 = new Position("a4", a1, b4, a7, null);
		a7 = new Position("a7", a4, d7, null, null);
		b2 = new Position("b2", null, d2, b4, null);
		b4 = new Position("b4", b6, c4, b2, a4);
		b6 = new Position("b6", b4, d6, null, null);
		c3 = new Position("c3", null, d3, c4, null);
		c4 = new Position("c4", c3, null, c5, b4);
		c5 = new Position("c5", c4, d5, null, null);
		d1 = new Position("d1", null, g1, d2, a1);
		d2 = new Position("d2", d1, f2, d3, b2);
		d3 = new Position("d3", d2, e3, null, c3);
		d5 = new Position("d5", null, e5, d6, c5);
		d6 = new Position("d6", d5, f6, d7, b6);
		d7 = new Position("d7", d6, g7, null, a1);
		e3 = new Position("e3", null, null, e4, d3);
		e4 = new Position("e4", e3, f4, e5, null);
		e5 = new Position("e5", e4, null, null, d5);
		f2 = new Position("f2", null, null, f4, d2);
		f4 = new Position("f4", f2, g4, f6, e4);
		f6 = new Position("f6", f4, null, null, d6);
		g1 = new Position("g1", null, null, g4, d1);
		g4 = new Position("g4", g1, null, g7, f4);
		g7 = new Position("g7", g4, null, null, d7);

		_positions = new Position[][] { { a1, null, null, a4, null, null, a7 },
				{ null, b2, null, b4, null, b6, null },
				{ null, null, c3, c4, c5, null, null },
				{ d1, d2, d3, null, d5, d6, d7 },
				{ null, null, e3, e4, e5, null, null },
				{ null, f2, null, f4, null, f6, null },
				{ g1, null, null, g4, null, null, g7 } };

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
