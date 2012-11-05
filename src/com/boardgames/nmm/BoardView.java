package com.boardgames.nmm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class BoardView extends View {

	// The board model
	private Board _board;
	// The positions on that board
	private Position[][] _positions;
	// Heigth of the screen
	private int _height;
	// Widht of the screen
	private int _width;
	// The paint object to draw the board
	private Paint _paint;
	// Size of one "pixel" of a stone
	private int _stonePixelSize;
	// Size of the clickable area around a position
	private float _clickAreaSize;
	// Size of the board
	private float _boardSize;
	// Distance between field and top of the screen
	private float _boardOffset;

	Colors[][] _blackStone = {
			{ Colors.TRANSPARENT, Colors.TRANSPARENT, Colors.WHITE,
					Colors.WHITE, Colors.WHITE, Colors.TRANSPARENT,
					Colors.TRANSPARENT },
			{ Colors.TRANSPARENT, Colors.WHITE, Colors.BLACK, Colors.BLACK,
					Colors.BLACK, Colors.WHITE, Colors.TRANSPARENT },
			{ Colors.WHITE, Colors.BLACK, Colors.BLACK, Colors.BLACK,
					Colors.BLACK, Colors.BLACK, Colors.WHITE },
			{ Colors.WHITE, Colors.BLACK, Colors.BLACK, Colors.BLACK,
					Colors.BLACK, Colors.BLACK, Colors.WHITE },
			{ Colors.WHITE, Colors.BLACK, Colors.BLACK, Colors.BLACK,
					Colors.BLACK, Colors.BLACK, Colors.WHITE },
			{ Colors.TRANSPARENT, Colors.WHITE, Colors.BLACK, Colors.BLACK,
					Colors.BLACK, Colors.WHITE, Colors.TRANSPARENT },
			{ Colors.TRANSPARENT, Colors.TRANSPARENT, Colors.WHITE,
					Colors.WHITE, Colors.WHITE, Colors.TRANSPARENT,
					Colors.TRANSPARENT } };

	Colors[][] _whiteStone = {
			{ Colors.TRANSPARENT, Colors.TRANSPARENT, Colors.BLACK,
					Colors.BLACK, Colors.BLACK, Colors.TRANSPARENT,
					Colors.TRANSPARENT },
			{ Colors.TRANSPARENT, Colors.BLACK, Colors.WHITE, Colors.WHITE,
					Colors.WHITE, Colors.BLACK, Colors.TRANSPARENT },
			{ Colors.BLACK, Colors.WHITE, Colors.WHITE, Colors.WHITE,
					Colors.WHITE, Colors.WHITE, Colors.BLACK },
			{ Colors.BLACK, Colors.WHITE, Colors.WHITE, Colors.WHITE,
					Colors.WHITE, Colors.WHITE, Colors.BLACK },
			{ Colors.BLACK, Colors.WHITE, Colors.WHITE, Colors.WHITE,
					Colors.WHITE, Colors.WHITE, Colors.BLACK },
			{ Colors.TRANSPARENT, Colors.BLACK, Colors.WHITE, Colors.WHITE,
					Colors.WHITE, Colors.BLACK, Colors.TRANSPARENT },
			{ Colors.TRANSPARENT, Colors.TRANSPARENT, Colors.BLACK,
					Colors.BLACK, Colors.BLACK, Colors.TRANSPARENT,
					Colors.TRANSPARENT } };

	/**
	 * Creates a new view that represents the board
	 * 
	 * @param context The Activity that uses this view
	 * @param board The model that will be represented by this view
	 */
	public BoardView(Context context, Board board) {
		super(context);
		setBackgroundColor(Colors.TAN.getColor());
		_paint = new Paint();
		_board = board;
		_positions = _board.getPositions();
	}
	
	/**
	 * Dummy Constructor, necessary...
	 */
	public BoardView(Context context) {
		super(context);
	}

	public boolean onTouchEvent(MotionEvent e) {
		int x = (int) ((e.getX()) / _clickAreaSize);
		int y = (int) ((e.getY()) / _clickAreaSize);
		
		_board.pick(x, y);
		
		invalidate();
		return false;
	}

	@Override
	/**
	 * Saves the screen resolution
	 */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		_height = View.MeasureSpec.getSize(heightMeasureSpec);
		_width = View.MeasureSpec.getSize(widthMeasureSpec);

		setMeasuredDimension(_width, _height);

		_stonePixelSize = _width / 70;
		_boardSize = _width * 0.85f;
		_clickAreaSize = _boardSize / 6f;
		_boardOffset = (_width - _boardSize) / 2f;
	}

	@Override
	/**
	 * Paints the view.
	 */
	protected void onDraw(Canvas canvas) {
		drawField(canvas);
		drawPositions(canvas);
	}

	/**
	 * Draws the field on the canvas.
	 */
	private void drawField(Canvas canvas) {
		_paint.setColor(Colors.BLACK.getColor());
		_paint.setStrokeWidth(6);
		_paint.setStyle(Paint.Style.STROKE);

		// Draw outer rectangle

		float oStop = _boardOffset + _boardSize;

		canvas.drawRect(_boardOffset, _boardOffset, oStop, oStop, _paint);

		// Draw middle rectangle
		float mStart = _boardOffset + _boardSize / 6f;
		float mStop = oStop - _boardSize / 6f;

		canvas.drawRect(mStart, mStart, mStop, mStop, _paint);

		// Draw inner rectangle
		float iStart = mStart + _boardSize / 6f;
		float iStop = mStop - _boardSize / 6f;

		canvas.drawRect(iStart, iStart, iStop, iStop, _paint);

		// Draw connection between rectangles
		canvas.drawLine(_boardOffset, _boardOffset + _boardSize / 2f, iStart,
				_boardOffset + _boardSize / 2f, _paint);
		canvas.drawLine(_boardOffset + _boardSize / 2f, _boardOffset,
				_boardOffset + _boardSize / 2f, iStart, _paint);
		canvas.drawLine(iStop, _boardOffset + _boardSize / 2f, oStop,
				_boardOffset + _boardSize / 2f, _paint);
		canvas.drawLine(_boardOffset + _boardSize / 2f, iStop, _boardOffset
				+ _boardSize / 2f, oStop, _paint);
	}
	
	private void drawPositions(Canvas canvas) {
		for (int i = 0; i < _positions.length; ++i) {
			for (int j = 0; j < _positions.length; ++j) {
				Position temp = _positions[i][j];
				if (temp != null) {
					float x = _boardOffset + i * _clickAreaSize;
					float y = _boardOffset + j * _clickAreaSize;
					if (temp.getStone() == Stone.BLACK) 
						drawStone(canvas, x, y, _blackStone);
					else if (temp.getStone() == Stone.WHITE)
						drawStone(canvas, x, y, _whiteStone);
				}
			}
		}
	}

	private void drawStone(Canvas canvas, float centerX, float centerY,
			Colors[][] stone) {
		_paint.setStyle(Paint.Style.FILL);
		float offsetX = centerX - (_stonePixelSize * stone.length / 2f);
		float offsetY = centerY - (_stonePixelSize * stone.length / 2f);

		for (int i = 0; i < stone.length; ++i) {
			for (int j = 0; j < stone.length; ++j) {
				int color = stone[i][j].getColor();
				_paint.setColor(color);
				canvas.drawRect(offsetX + i * _stonePixelSize, offsetY + j
						* _stonePixelSize, offsetX + (i + 1) * _stonePixelSize,
						offsetY + (j + 1) * _stonePixelSize, _paint);
			}
		}
	}
}
