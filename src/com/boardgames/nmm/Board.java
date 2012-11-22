package com.boardgames.nmm;

public class Board {

	private Position[][] _positions;

	private ObservableState _currentState;
	private ObservableState _lastState;
	private ObservableState _stateWaiting;
	private ObservableState _statePutting;
	private ObservableState _stateMoving;
	private ObservableState _stateFlying;

	private final Stone _playerStone = Stone.WHITE;
	private final Stone _opponentStone = Stone.BLACK;

	// There are nine stones for each player to place in the initial phase
	private int _stonesToPlace = 9;
	// How many of the players stones are on the field
	private int _stonesOnField = 0;

	public Board() {
		initializeField();
		initializeStates();
		setStartState();
	}

	/**
	 * Registers the observers that have to be notified if the game is in a
	 * state where the player is waiting for the opponent.
	 */
	public void registerGetObserver(StateObserver so) {
		_stateWaiting.registerObserver(so);
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

	public void move(int oldField, int newField, int delField) {
		// TODO: Do something with the data
		// It may be necessary to change the move method of the abstract class
		// ObservableState
		_currentState.move(oldField, newField, delField);
	}

	public void pick(int x, int y) {
		_currentState.pick(x, y);
	}

	public Position[][] getPositions() {
		return _positions;
	}

	private void initializeStates() {
		_stateWaiting = new GameStateWaiting();
		_statePutting = new GameStatePutting();
		_stateMoving = new GameStateMoving();
		_stateFlying = new GameStateFlying();
	}

	private void initializeField() {
		Position a1, a4 = null, a7 = null, b2, b4 = null, b6 = null, c3, c4 = null, c5 = null, d1 = null, d2 = null, d3 = null, d5 = null, d6 = null, d7 = null, e3 = null, e4 = null, e5 = null, f2 = null, f4 = null, f6 = null, g1 = null, g4 = null, g7 = null;

		a1 = new Position(null, d1, a4, null);
		a4 = new Position(a1, b4, a7, null);
		a7 = new Position(a4, d7, null, null);
		b2 = new Position(null, d2, b4, null);
		b4 = new Position(b6, c4, b2, a4);
		b6 = new Position(b4, d6, null, null);
		c3 = new Position(null, d3, c4, null);
		c4 = new Position(c3, null, c5, b4);
		c5 = new Position(c4, d5, null, null);
		d1 = new Position(null, g1, d2, a1);
		d2 = new Position(d1, f2, d3, b2);
		d3 = new Position(d2, e3, null, c3);
		d5 = new Position(null, e5, d6, c5);
		d6 = new Position(d5, f6, d7, b6);
		d7 = new Position(d6, g7, null, a1);
		e3 = new Position(null, null, e4, d3);
		e4 = new Position(e3, f4, e5, null);
		e5 = new Position(e4, null, null, d5);
		f2 = new Position(null, null, f4, d2);
		f4 = new Position(f2, g4, f6, e4);
		f6 = new Position(f4, null, null, d6);
		g1 = new Position(null, null, g4, d1);
		g4 = new Position(g1, null, g7, f4);
		g7 = new Position(g4, null, null, d7);

		_positions = new Position[][] { { a1, null, null, a4, null, null, a7 },
				{ null, b2, null, b4, null, b6, null },
				{ null, null, c3, c4, c5, null, null },
				{ d1, d2, d3, null, d5, d6, d7 },
				{ null, null, e3, e4, e5, null, null },
				{ null, f2, null, f4, null, f6, null },
				{ g1, null, null, g4, null, null, g7 } };

	}

	/**
	 * Sets the state at the beginning of the game, depending on the player's
	 * color. If player has white, he begins, otherwise he waits for the
	 * opponent-
	 */
	private void setStartState() {
		if (_playerStone == Stone.WHITE)
			_currentState = _statePutting;
		else
			_currentState = _stateWaiting;
	}

	/**
	 * Checks if a given coordinate is a valid Position. It is valid if it is
	 * within the array boundaries and not null and empty. THIS ONLY WORKS IN
	 * THE FIRST PHASE.
	 */
	private boolean isChoiceValid(int x, int y) {
		return x >= 0 && y >= 0 && x < _positions.length
				&& y < _positions.length && _positions[x][y] != null;
	}

	/**
	 * This state is active while the player is waiting for the opponents move.
	 * During this state the player cannot move or put any of his stones.
	 */
	class GameStateWaiting extends ObservableState {

		@Override
		public void next() {
			_currentState = _lastState;
		}

		@Override
		public void pick(int x, int y) {
			// This is empty because in this state we wait for the opponent.
			// This requires no user interaction

		}

		@Override
		public void move(int oldField, int newField, int delField) {
			if (oldField >= 0) {
				int fromX = oldField / 10;
				int fromY = oldField % 10;
				_positions[fromX][fromY].setStone(null);
			}

			int toX = newField / 10;
			int toY = newField % 10;
			_positions[toX][toY].setStone(_opponentStone);

			if (delField >= 0) {
				_stonesOnField--;
				int delX = delField / 10;
				int delY = delField % 10;
				_positions[delX][delY].setStone(null);
			}

			next();
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
		public void next() {
			_currentState = _stateWaiting;

			if (_stonesToPlace > 0)
				_lastState = this;
			else
				_lastState = _stateMoving;

		}

		@Override
		public void pick(int x, int y) {
			if (isChoiceValid(x, y)) {
				if (_positions[x][y].isEmpty()) {
					int to = x * 10 + y;
					move(-1, to, -1);
					// Check if Mühle, wenn nein next()
				}
			}
		}

		@Override
		public void move(int oldField, int newField, int delField) {
			int x = newField / 10;
			int y = newField % 10;
			_positions[x][y].setStone(_playerStone);
			
			_stonesToPlace--;
			_stonesOnField++;

			if (delField >= 0) {
				x = delField / 10;
				y = delField % 10;
				_positions[x][y].setStone(null);
			}
			next();

			notifyObservers(oldField, newField, delField);
		}
	}

	/**
	 * This state is active during the second phase. The player can move his
	 * stones from one spot to the next, but only along the board lines. The two
	 * players are alternately placing their stones, therefore the GameStateWait
	 * is activated after each move.
	 */
	class GameStateMoving extends ObservableState {
		int _from = -1;
		int _to = -1;

		@Override
		public void next() {
			_currentState = _stateWaiting;
			
			if (_stonesOnField > 3)
				_lastState = this;
			else
				_lastState = _stateFlying;
		}

		@Override
		public void pick(int x, int y) {
			if (isChoiceValid(x, y)) {
				if (_positions[x][y].getStone() == _playerStone) {
					_from = x * 10 + y;
				} else if (_from > -1 && _positions[x][y].isEmpty()) {
					_to = x * 10 + y;
					move(_from, _to, -1);
				}
			}

		}

		@Override
		public void move(int oldField, int newField, int delField) {
			int x = oldField / 10;
			int y = oldField % 10;
			_positions[x][y].setStone(null);

			x = newField / 10;
			y = newField % 10;
			_positions[x][y].setStone(_playerStone);
		}
	}

	/**
	 * This state is active during the third phase. The player has only three
	 * stones left, but can place them on any available spot.
	 */
	class GameStateFlying extends ObservableState {

		@Override
		public void next() {
			_currentState = _stateWaiting;
			_lastState = this;
		}

		@Override
		public void pick(int x, int y) {
			// TODO Auto-generated method stub

		}

		@Override
		public void move(int oldField, int newField, int delField) {
			// TODO Auto-generated method stub

		}
	}
}
