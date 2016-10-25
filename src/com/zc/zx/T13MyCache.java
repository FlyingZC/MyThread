package com.zc.zx;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//缓存系统
public class T13MyCache {
	private Map<String,Object> cache=new HashMap<String,Object>();
	public static void main(String[] args) {
		
	}
	
	private ReadWriteLock rwl=new ReentrantReadWriteLock();
	public Object getData(String key){//检查是否有缓存,有直接给.无则查询
		Object value=null;
		try{
			rwl.readLock().lock();//[1]先加读锁
			value=cache.get(key);
			
			if(value==null){//第一个线程发现value为null,则会加写锁,去数据库获取数据
				rwl.readLock().unlock();//解除读锁
				rwl.writeLock().lock();//加写锁
				try{
					if(value==null){//除了第一个线程能去数据库查询,后面线程一旦发现value!=null,直接跳过
						value="aaa";//实际去数据库查询	
					}
				}finally{
					rwl.writeLock().unlock();//解除写锁
				}
				rwl.readLock().lock();//解除写锁后重新加写锁,后面才能释放读锁
			}
		}finally{
			rwl.readLock().unlock();//[2]解除读锁
		}
		return value;
	}
}