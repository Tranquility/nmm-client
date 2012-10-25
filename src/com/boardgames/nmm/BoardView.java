package com.boardgames.nmm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class BoardView extends View {

	private int _height;
	private int _width;
	private Paint _paint;

	public BoardView(Context context) {
		super(context);
		setBackgroundColor(Colors.WHITE.getColor());
		_paint = new Paint();
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
	 * Paints the view
	 */
	protected void onDraw(Canvas canvas) {
		drawField(canvas);

	}

	/**
	 * @param canvas
	 */
	private void drawField(Canvas canvas) {
		_paint.setColor(Colors.BLACK.getColor());
		_paint.setStrokeWidth(3);
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
}
