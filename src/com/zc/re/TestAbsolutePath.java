package com.zc.re;

import java.io.File;

public class TestAbsolutePath {
	public static void main(String[] args) {
		File f=new File("note.txt");
		//C:\Users\Administrator\Workspaces\MyEclipse 10\MyThread\note.txt
		System.out.println(f.getAbsolutePath());
		
		String oldDir="C:\\Users\\Administrator\\Workspaces\\MyEclipse 10\\MyThread\\a\\note.txt";
		StringBuilder newDir=new StringBuilder(oldDir);
		int start=oldDir.lastIndexOf("\\a");//找到a目录下标
		newDir.replace(start, start+2,"\\b\\a");
		System.out.println(newDir);
	}
}
