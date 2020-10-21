package com.yw;

class Explosion {//爆炸类
	int x,y;//爆炸的位置坐标
	int sur = 3;//坦克爆炸过程生存期
	boolean being = true;////坦克生命
	
	public Explosion(int x,int y){//爆炸的构造函数将爆炸位置初始化
		this.x = x;
		this.y = y;
	}
	public void survival(){//坦克爆炸生存期方法
		if(sur>0){
			sur--;
		}else{
			this.being = false;
		}
	}
}