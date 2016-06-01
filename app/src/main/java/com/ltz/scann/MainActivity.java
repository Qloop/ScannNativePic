package com.ltz.scann;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ltz.scann.Bean.FileInfos;
import com.ltz.scann.adapter.RecyclerAdapter;

import java.io.File;
import java.util.List;

/**
 * Created by Explorer on 2016/5/22.
 */
public class MainActivity extends Activity {

	private RecyclerView mRecyclerView;
	private ProgressBar mProgressBar;
	private Button mBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		initViews();
	}

	private void initViews() {
		mRecyclerView = (RecyclerView) findViewById(R.id.rv_scann);
		mProgressBar = (ProgressBar) findViewById(R.id.pb_scann);
		mBtn = (Button) findViewById(R.id.btn_scann);
	}

	public void start(View v) {
		mBtn.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.VISIBLE);
		startScann();
	}


	private void startScann() {
		if (hasSDCard()) {
			final File externalStorageDirectory = Environment.getExternalStorageDirectory();
			WorkThread.getInstance()
					.setPath(externalStorageDirectory)
					.startWork(new WorkThread.WorkThreadCallBack() {
						@Override
						public void hasCompleted(List<FileInfos> fileInfosList) {
							RecyclerAdapter adapter = new RecyclerAdapter(fileInfosList, mRecyclerView);
							mRecyclerView.setHasFixedSize(true);
							mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
							//设置显示动画
							mRecyclerView.setItemAnimator(new DefaultItemAnimator());
							mRecyclerView.setAdapter(adapter);
							mProgressBar.setVisibility(View.GONE);

						}

						@Override
						public void onError(Exception e) {

						}
					});
		} else {
			File rootDirectory = Environment.getRootDirectory();
			WorkThread.getInstance()
					.setPath(rootDirectory)
					.startWork(new WorkThread.WorkThreadCallBack() {
						@Override
						public void hasCompleted(List<FileInfos> fileInfosList) {
							Toast.makeText(MainActivity.this, "hasCompleted", Toast.LENGTH_SHORT).show();
							RecyclerAdapter adapter = new RecyclerAdapter(fileInfosList, mRecyclerView);
							mProgressBar.setVisibility(View.GONE);
							mRecyclerView.setHasFixedSize(true);
							mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
							//设置显示动画
							mRecyclerView.setItemAnimator(new DefaultItemAnimator());
							mRecyclerView.setAdapter(adapter);

						}

						@Override
						public void onError(Exception e) {
							Toast.makeText(MainActivity.this, "扫描出错了T_T", Toast.LENGTH_SHORT).show();
						}
					});
		}
	}

	/**
	 * 判断是否挂载SD卡
	 */
	private boolean hasSDCard() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

}
