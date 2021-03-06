package com.zc.exercise;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class T0510ThreadCopyFile {
	//1.声明线程数组
	MyThread[] mts=new MyThread[10];
	//控制第几个线程启动
	public int n=0;
	
	//2.在构造方法中创建每个线程
	public T0510ThreadCopyFile(){
		for(int i=0;i<mts.length;i++){
			mts[i]=new MyThread();
		}
	}
	
	public static void main(String[] args) {
		try {
			new T0510ThreadCopyFile().copyDirectory(new File("E:\\0ShuJuJieGou"),new File("f:\\copyf"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void copyDirectory(File sourceDir,File targetDir) throws Exception{
		if(!targetDir.exists()){
			//如果目录不存在.创建多层目录
			targetDir.mkdirs();
		}
		
		//返回一个File数组
		File[] files = sourceDir.listFiles();
		for(int i=0;i<files.length;i++){
			if(files[i].isFile()){
				//复制后的目标文件
				File targetFile=new File(targetDir.getAbsolutePath()
						+File.separator+files[i].getName());		
				//3.启动线程
				MyThread t=mts[n++%10];//线程数组中的线程在构造方法中已经new过,直接使用
				t.set(files[i],targetFile);//设置要copy的源文件和目标文件
				t.start();
				t.join();//main线程等待t线程执行完毕
				mts[n%10]=new MyThread();
			}
			if(files[i].isDirectory()){
				String dir2=targetDir.getAbsolutePath()+File.separator+files[i].getName();
				copyDirectory(files[i],new File(dir2));
			}
		}
	}
}

class MyThread extends Thread{
	private File src;
	private File desc;

	public void set(File src, File desc) {
		this.src=src;
		this.desc=desc;
		
	}
	
	@Override
	public void run() {
		copyPerFile(src, desc);
	}
	
	public static void copyPerFile(File src,File desc){
		if(!desc.exists()){
			try {
				desc.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		FileInputStream fis=null;
		FileOutputStream fos=null;
		BufferedInputStream bis=null;
		BufferedOutputStream bos=null;
		try {
			fis=new FileInputStream(src);
			fos=new FileOutputStream(desc);
			bis=new BufferedInputStream(fis);
			bos=new BufferedOutputStream(fos);
			byte[] b=new byte[24];
			int len;
			while((len=bis.read(b))!=-1){
				bos.write(b,0,len);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
		}
	}
}
