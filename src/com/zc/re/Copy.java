package com.zc.re;

import java.io.File;

/*3.39将文件夹a(包括其下所有子文件夹和文件)复制到文件夹b下,
 * 使用10个线程同时进行,每个线程独立处理一个文件*/
public class Copy {
	public static void main(String[] args) {
		for(int i=0;i<10;i++){
			new Thread(new CopyBusiness()).start();
		}
	}
}

class CopyBusiness implements Runnable{
	@Override
	public void run() {
		
	}
}

class FileCopy{
	File root;
	public FileCopy(File root){
		this.root=root;
	}
	
	public File traceFile(File file){
		if(file.isFile()){
			copyFile(file);
			System.out.println(file);
		}
		if(file.isDirectory()){
			File[] fs=file.listFiles();
			for(File f:fs){
				if(f.isDirectory()){
					copyDir(f);
					traceFile(f);
				}
				if(f.isFile()){
					copyFile(f);
					System.out.println(f);
				}
			}
		}
		return null;
	}
	//复制目录
	private void copyDir(File f) {
		//C:\Users\Administrator\Workspaces\MyEclipse 10\a\note.txt
		String newDir=getAbsolute(f);
		File desc=new File(newDir);
		if(!desc.exists()){
			desc.mkdirs();
		}
	}

	private void copyFile(File file) {
		String newFileDir=getAbsolute(file);
		new File(newFileDir);
	}
	
	//根据当前目录得出要复制文件(或目录)的绝对路径
	public String getAbsolute(File f){
		String oldDir=f.getAbsolutePath();
		StringBuilder newDir=new StringBuilder(oldDir);
		int start=oldDir.lastIndexOf("\\a");//找到a目录下标
		newDir.replace(start, start+2,"\\b\\a");
		return newDir.toString();
	}
	
}
