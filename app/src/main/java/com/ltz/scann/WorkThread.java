package com.ltz.scann;

import com.ltz.scann.Bean.FileInfos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 扫描本地文件 使用线程池
 * Created by Explorer on 2016/5/29.
 */
public class WorkThread {

	private File path;
	private List<FileInfos> fileInfosList = new ArrayList<>();
	//	private ExecutorService mExector = Executors.newFixedThreadPool(1);
	private ExecutorService mExector = Executors.newFixedThreadPool(3);
	private static WorkThread mInstance;

	private WorkThread() {
	}


	public static WorkThread getInstance() {
		if (mInstance == null) {
			synchronized (WorkThread.class) {
				if (mInstance == null) {
					mInstance = new WorkThread();
				}
			}
		}
		return mInstance;
	}

	public WorkThread setPath(File path) {
		this.path = path;
		return mInstance;
	}

	/**
	 * 开始扫描 从sd卡和手机根目录开始
	 */
	public void startWork(WorkThreadCallBack callBack) {
		System.out.println("working =======================================");

		mExector.execute(new Thread(new Scanner(path)));
		mExector.shutdown();
		while (true) {
			if (mExector.isTerminated()) {
				try {
					if (mExector.isTerminated()) {
						workThreadCallBack = callBack;
						workThreadCallBack.hasCompleted(fileInfosList);
						System.out.println("result is ------------" + fileInfosList.size() + "-------------------");
					}

				} catch (Exception e) {
					e.printStackTrace();
					workThreadCallBack.onError(e);
				}
				break;
			}
		}


	}

	WorkThreadCallBack workThreadCallBack;

	/**
	 * 扫描结束（终止）回调
	 */
	public interface WorkThreadCallBack {
		public void hasCompleted(List<FileInfos> fileInfosList);

		public void onError(Exception e);
	}


	/**
	 * 扫描线程类
	 */
	class Scanner implements Runnable {

		private File mRootFile;
		private List<FileInfos> mFileList;

		public Scanner(File rootFile) {
			mRootFile = rootFile;
			mFileList = new ArrayList<>();
		}

		@Override
		public void run() {
			File[] files = mRootFile.listFiles();
			if (files != null) {
				if (files.length > 0) {
					for (File file : files) {
						if (file.isDirectory()) {
							System.out.println("path is ++++++++++++++++" + file.getAbsolutePath() + "+++++++++++++++++++++");
							new Thread(new Scanner(file)).start();
						} else {
							if (file.getName().endsWith(".jpg")) {
								System.out.println("PIC is *****************" + file.getName() + "****************");
								FileInfos fileInfos = new FileInfos(file.getName(), file.getAbsolutePath());
								mFileList.add(fileInfos);
							}

						}
					}
				}
			}
			fileInfosList.addAll(mFileList);
		}
	}

}
