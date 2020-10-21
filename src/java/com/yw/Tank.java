package com.yw;

import java.util.Vector;

class Tank {//坦克类
	int x = 0,y = 0;//横纵坐标
	int direction;//方向
	int color;//颜色
	Vector<Bullet> bulVec = new Vector<Bullet>();//子弹集合，存放子弹
	Bullet bullet = null;	
	boolean being = true;//坦克生命
		
	//封装变量
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}	
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}		
	public Tank(int x,int y){//构造方法,初始化横纵坐标
		this.x = x;
		this.y = y;
	}
	public void shootBullet(){//坦克子弹发射方法
		switch(this.direction){
		case 0://向上发射子弹
			bullet = new Bullet(x+10,y-10,0);	
			bulVec.add(bullet);//每发射一颗子弹就再添加一颗
			break;
		case 1://向下发射子弹
			bullet = new Bullet(x+10,y+30,1);
			bulVec.add(bullet);
			break;
		case 2://向左发射子弹
			bullet = new Bullet(x-10,y+10,2);
			bulVec.add(bullet);
			break;
		case 3://向右发射子弹
			bullet = new Bullet(x+30,y+10,3);
			bulVec.add(bullet);
			break;
		}
		Thread thread = new Thread(bullet);
		thread.start();//启动发射子弹线程
	}	
}