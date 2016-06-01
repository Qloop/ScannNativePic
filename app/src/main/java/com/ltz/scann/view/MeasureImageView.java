package com.ltz.scann.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 便于在adapter中获取imageview的width AND height
 * Created by Explorer on 2016/5/31.
 */
public class MeasureImageView extends ImageView {
	public MeasureImageView(Context context) {
		super(context);
	}

	public MeasureImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MeasureImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		if (onMeasureListener != null) {
			onMeasureListener.onMeasureSize(getMeasuredWidth(), getMeasuredHeight());
		}
	}

	private OnMeasureListener onMeasureListener;

	public void setOnMeasureListener(OnMeasureListener onMeasureListener){

		this.onMeasureListener =onMeasureListener;
	}

	public interface OnMeasureListener {

		public void onMeasureSize(int width, int height);
	}
}
