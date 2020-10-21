package com.yw;

import java.util.Vector;

class MyTank extends Tank{//游戏玩家的坦克类
	public static final int SPEED = 3;
	//因为子弹是跟着坦克走的，所以子弹的功能大多都和坦克类有关
	MyTank myTank;
	Vector<EnemyTank> eTankVec = new Vector<EnemyTank>();
	EnemyTank enemyTank;

	public MyTank(int x,int y){
		super(x,y);
	}
	public void direcUp(){//方向上
		if(y>5){
			y = y-SPEED;		
			//y-=speed;
		}
	}
	public void direcDown(){//方向下
		if(y<325){
			y = y+SPEED;		
			//y+=speed;
		}
	}
	public void direcLeft(){//方向左
		if(x>5){
			x = x-SPEED;
			//x-=speed;
		}
	}
	public void direcRight(){//方向右
		if(x<365){
			x = x+SPEED;
			//x+=speed;
		}
	}
}