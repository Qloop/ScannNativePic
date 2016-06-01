package com.ltz.scann.adapter;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ltz.scann.Bean.FileInfos;
import com.ltz.scann.R;
import com.ltz.scann.utils.NativeImageLoader;
import com.ltz.scann.view.MeasureImageView;

import java.util.List;

/**
 * RecyclerView适配器
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

	private List<?> mData;
	private RecyclerView mRecyclerView;
	private Point mPoint = new Point(0, 0);//保存ImageView的宽高 用于比例缩放

	public RecyclerAdapter(List<?> data, RecyclerView recyclerView) {
		mData = data;
		mRecyclerView = recyclerView;
	}

	public OnItemClickListener itemClickListener;

	public void setOnItemClickListener(OnItemClickListener itemClickListener) {
		this.itemClickListener = itemClickListener;
	}

	public interface OnItemClickListener {
		void onItemClick(View view, int position);
	}

	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		public LinearLayout mLayout;
		public TextView tvName;
		public MeasureImageView ivPic;

		public ViewHolder(View itemView) {
			super(itemView);
			mLayout = (LinearLayout) itemView;
			mLayout.setOnClickListener(this);

			tvName = (TextView) itemView.findViewById(R.id.tv_pic_name);
			ivPic = (MeasureImageView) itemView.findViewById(R.id.miv_pic);

			ivPic.setOnMeasureListener(new MeasureImageView.OnMeasureListener() {
				@Override
				public void onMeasureSize(int width, int height) {
					mPoint.set(width, height);
				}
			});

		}

		@Override
		public void onClick(View v) {
			if (itemClickListener != null) {
				itemClickListener.onItemClick(v, getPosition());
			}
		}
	}

	@Override
	public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pic_info_recycleview, parent, false);

		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final RecyclerAdapter.ViewHolder holder, int position) {
		FileInfos fileInfos = (FileInfos) mData.get(position);
		holder.tvName.setText(fileInfos.getName());
		holder.ivPic.setTag(fileInfos.getPath());
		Bitmap bitmap = NativeImageLoader.getInstance()
				.loadNativeImage(fileInfos.getPath(), mPoint,new NativeImageLoader.NativeImageCallBack() {
			@Override
			public void onImageLoader(Bitmap bitmap, String path) {
				ImageView imageView = (ImageView) mRecyclerView.findViewWithTag(path);
				if (bitmap != null && imageView != null) {
					imageView.setImageBitmap(bitmap);
				}
			}
		});
		if (bitmap != null) {
			holder.ivPic.setImageBitmap(bitmap);
		} else {
			//设置默认图片
			holder.ivPic.setImageResource(R.mipmap.ic_launcher);
		}
	}

	@Override
	public int getItemCount() {
		return mData.size();
	}
}