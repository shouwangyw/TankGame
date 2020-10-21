package com.yw;

class Bullet implements Runnable{//子弹类
	int x,y;//出现的横纵坐标
	int direction;//子弹方向
	public static final int SPEED = 5;
	boolean being = true;//子弹存在或生命
	
	public Bullet(int x,int y,int direction){
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	public void run(){//实现run()方法
		while(true){//凡是线程下面的死循环里都要有个睡眠
			try{
				Thread.sleep(50);//睡眠，一按发射不能马上出子弹
			}catch(Exception e){}
			
			switch(direction){
			case 0://上
				y-=SPEED;	break;
			case 1://下
				y+=SPEED;	break;
			case 2://左
				x-=SPEED;	break;
			case 3://右
				x+=SPEED;	break;
			}
			if(x<0||x>395||y<0||y>355){//不再panel面板上的子弹消失
				this.being = false;
				break;
			}
		}
	}
}