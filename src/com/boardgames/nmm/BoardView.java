package com.boardgames.nmm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class BoardView extends View {

	private Board _board;
	private int _height;
	private int _width;
	private Paint _paint;
	private final int _size = 10;

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

	public BoardView(Context context, Board board) {
		super(context);
		setBackgroundColor(Colors.TAN.getColor());
		_paint = new Paint();
		_board = board;
	}

	@Override
	/**
	 * Saves the screen resolution
	 */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		_height = View.MeasureSpec.getSize(heightMeasureSpec);
		_width = View.MeasureSpec.getSize(widthMeasureSpec);

		setMeasuredDimension(_width, _height);
	}

	@Override
	/**
	 * Paints the view.
	 */
	protected void onDraw(Canvas canvas) {
		drawField(canvas);
		drawStone(canvas, _width * 0.075f, _width * 0.075f, _blackStone);
		drawStone(canvas, _width * 0.925f, _width * 0.925f, _whiteStone);
	}

	/**
	 * Draws the field on the canvas.
	 */
	private void drawField(Canvas canvas) {
		_paint.setColor(Colors.BLACK.getColor());
		_paint.setStrokeWidth(6);
		_paint.setStyle(Paint.Style.STROKE);

		// Draw outer rectangle
		float oLength = _width * 0.85f;
		float oStart = (_width - oLength) / 2f;
		float oStop = oStart + oLength;

		canvas.drawRect(oStart, oStart, oStop, oStop, _paint);

		// Draw middle rectangle
		float mStart = oStart + oLength / 7f;
		float mStop = oStop - oLength / 7f;

		canvas.drawRect(mStart, mStart, mStop, mStop, _paint);

		// Draw inner rectangle
		float iStart = mStart + oLength / 7f;
		float iStop = mStop - oLength / 7f;

		canvas.drawRect(iStart, iStart, iStop, iStop, _paint);

		// Draw connection between rectangles
		canvas.drawLine(oStart, oStart + oLength / 2f, iStart, oStart + oLength
				/ 2f, _paint);
		canvas.drawLine(oStart + oLength / 2f, oStart, oStart + oLength / 2f,
				iStart, _paint);
		canvas.drawLine(iStop, oStart + oLength / 2f, oStop, oStart + oLength
				/ 2f, _paint);
		canvas.drawLine(oStart + oLength / 2f, iStop, oStart + oLength / 2f,
				oStop, _paint);
	}

	private void drawStone(Canvas canvas, float centerX, float centerY, Colors[][] stone) {
		_paint.setStyle(Paint.Style.FILL);
		float offsetX = centerX - (_size * stone.length / 2f);
		float offsetY = centerY - (_size * stone.length / 2f);

		for (int i = 0; i < stone.length; ++i) {
			for (int j = 0; j < stone.length; ++j) {
				int color = stone[i][j].getColor();
				_paint.setColor(color);
				canvas.drawRect(offsetX + i * _size, offsetY + j * _size, offsetX
						+ (i + 1) * _size, offsetY + (j + 1) * _size, _paint);
			}
		}
	}
}
