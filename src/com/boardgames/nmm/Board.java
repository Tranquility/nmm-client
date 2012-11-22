package com.boardgames.nmm;

public class Board {

	private Position[][] _positions;

	private ObservableState _currentState;
	private ObservableState _lastState;
	private ObservableState _stateWaiting;
	private ObservableState _statePutting;
	private ObservableState _stateMoving;
	private ObservableState _stateFlying;

	private final Stone _playerStone = Stone.BLACK;
	private final Stone _opponentStone = Stone.WHITE;

	// There are nine stones for each player to place in the initial phase
	private int _stonesToPlace = 9;
	// How many of the players stones are on the field
	private int _stonesOnField = 0;

	public Board() {
		initializeField();
		initializeStates();
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
		Position a1, a4, a7, b2, b4, b6, c3, c4, c5, d1, d2, d3, d5, d6, d7, e3, e4, e5, f2, f4, f6, g1, g4, g7;

		a1 = new Position();
		a4 = new Position();
		a7 = new Position();
		b2 = new Position();
		b4 = new Position();
		b6 = new Position();
		c3 = new Position();
		c4 = new Position();
		c5 = new Position();
		d1 = new Position();
		d2 = new Position();
		d3 = new Position();
		d5 = new Position();
		d6 = new Position();
		d7 = new Position();
		e3 = new Position();
		e4 = new Position();
		e5 = new Position();
		f2 = new Position();
		f4 = new Position();
		f6 = new Position();
		g1 = new Position();
		g4 = new Position();
		g7 = new Position();

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
	public void setStartState() {
		if (_playerStone == Stone.WHITE)
			_currentState = _statePutting;
		else {
			_currentState = _stateWaiting;
			_lastState = _statePutting;
			((GameStateWaiting) _currentState).startPulling();
		}
	}

	/**
	 * Checks if a given coordinate is a valid Position. It is valid if it is
	 * within the array boundaries and not null and empty. THIS ONLY WORKS IN
	 * THE FIRST PHASE.
	 */
	private boolean isChoiceValid(int row, int col) {
		return row >= 0 && col >= 0 && row < _positions.length
				&& col < _positions.length && _positions[row][col] != null;
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
			int row = 0;
			int col = 0;
			if (oldField >= 0) {
				row = oldField / 10;
				col = oldField % 10;
				_positions[row][col].setStone(null);
			}

			row = newField / 10;
			col = newField % 10;
			_positions[row][col].setStone(_opponentStone);

			if (delField >= 0) {
				_stonesOnField--;
				row = delField / 10;
				col = delField % 10;
				_positions[row][col].setStone(null);
			}

			next();
		}

		public void startPulling() {
			notifyObservers(0, 0, 0);
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
			((GameStateWaiting) _currentState).startPulling();

			if (_stonesToPlace > 0)
				_lastState = this;
			else
				_lastState = _stateMoving;

		}

		@Override
		public void pick(int row, int col) {
			if (isChoiceValid(row, col)) {
				if (_positions[row][col].isEmpty()) {
					int to = row * 10 + col;
					move(-1, to, -1);
					// Check if Mühle, wenn nein next()
				}
			}
		}

		@Override
		public void move(int oldField, int newField, int delField) {
			int row = newField / 10;
			int col = newField % 10;
			_positions[row][col].setStone(_playerStone);

			_stonesToPlace--;
			_stonesOnField++;

			if (delField >= 0) {
				row = delField / 10;
				col = delField % 10;
				_positions[row][col].setStone(null);
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
			((GameStateWaiting) _currentState).startPulling();

			if (_stonesOnField > 3)
				_lastState = this;
			else
				_lastState = _stateFlying;
		}

		@Override
		public void pick(int row, int col) {
			if (isChoiceValid(row, col)) {
				Position pos = _positions[row][col];
				if (pos.getStone() == _playerStone) {
					_from = row * 10 + col;
				} else if (_from > -1 && pos.isEmpty()) {
					int fromRow = _from / 10;
					int fromCol = _from % 10;

					if (_positions[fromRow][fromCol].isNeighbor(pos)) {
						_to = row * 10 + col;
						move(_from, _to, -1);
					}
				}
			}

		}

		@Override
		public void move(int oldField, int newField, int delField) {
			int row = oldField / 10;
			int col = oldField % 10;
			_positions[row][col].setStone(null);

			row = newField / 10;
			col = newField % 10;
			_positions[row][col].setStone(_playerStone);

			_to = -1;
			_from = -1;
			
			notifyObservers(oldField, newField, delField);
			next();
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
