package com.qhy.letter.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.qhy.letter.R;

/**
 * 带边框的ImageView
 * 
 * @author qihuiyou
 * 
 */
public class MyImageView extends ImageView {

	private String namespace = "http://com.qhy.letter.view";
	private int color;

	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

		TypedArray typedArray = context
				.obtainStyledAttributes(attrs, R.styleable.BorderImageView);
        String border_color = typedArray.getString(R.styleable.BorderImageView_BorderColor);

        color = Color.parseColor(border_color);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ImageView#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub

		super.onDraw(canvas);
		// 画边框
		Rect rec = canvas.getClipBounds();
		rec.bottom--;
		rec.right--;
		Paint paint = new Paint();
		paint.setColor(color);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(rec, paint);
	}

}