package com.zc.zx;

public class T04TenHund2 {
	public static void main(String[] args) {
		final Business bus=new Business();
		new Thread(new Runnable(){
			@Override
			public void run() {
				for(int i=0;i<50;i++){
					bus.sub(i);
				}
			}
		}).start();//!!!!!!!!!一定记得start()线程!!!
		for(int i=0;i<50;i++){
			bus.main(i);
		}
	}
}

class Business{
	//是否让子线程执行
	private boolean bShouldSub=true;
	public synchronized void sub(int ciShu){
		if(!bShouldSub){//如果不该执行
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}//放弃
		}
		for(int i=1;i<=10;i++){
			System.out.println("子线程"+ciShu+",i="+i);
		}
		bShouldSub=false;
		this.notify();
	}
	public synchronized void main(int ciShu){
		if(bShouldSub){//不该本线程执行
			try {
				this.wait();//如果已经wait(),没人nofity(),则一直wait()...04 26min
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for(int i=1;i<=100;i++){
			System.out.println("主线程"+ciShu+",i="+i);
		}
		bShouldSub=true;
		this.notify();//如果有子线程在等待,notify()
	}
}