package com.ltz.scann.Bean;

/**
 * 文件名和文件本身封装一个JavaBean  方便展示操作
 * Created by Explorer on 2016/5/29.
 */
public class FileInfos {

	private String name;
	private String path;

	public FileInfos(String name, String path) {
		this.name = name;
		this.path = path;
	}


	public void setName(String name) {
		this.name = name;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	@Override
	public String toString() {
		return "FileInfos{" +
				"path='" + path + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
