package com.yw;

import java.util.Vector;

class EnemyTank extends Tank implements Runnable{//敌方坦克类
	public static final int SPEED = 3;//敌方坦克速度
	public static final int TIME = 50;//坦克休眠时间
	int time = 0;
	Vector<EnemyTank> eTankVec = new Vector<EnemyTank>();
	EnemyTank enemyTank;

	public EnemyTank(int x,int y){
		super(x,y);
	}
	public void run(){//启动线程时自动被执行
		while(true){			
			switch(this.direction){
			case 0://上
				for(int i=0;i<30;i++){
					if(y>5&&!collision()){//不能碰边界且不能碰到坦克
						y-=SPEED;
					}
					try{
						Thread.sleep(TIME);
					}catch(Exception e){}
				}				
				break;
			case 1://下
				for(int i=0;i<30;i++){
					if(y<325&&!collision()){
					//if(y<100&&!collision()){
						y+=SPEED;
					}
					try{
						Thread.sleep(TIME);
					}catch(Exception e){}
				}
				break;
			case 2://左
				for(int i=0;i<30;i++){
					if(x>5&&!collision()){
						x-=SPEED;
					}
					try{
						Thread.sleep(TIME);
					}catch(Exception e){}
				}
				break;
			case 3://右
				for(int i=0;i<30;i++){
					if(x<365&&!collision()){
					//if(x<100&&!collision()){
						x+=SPEED;
					}
					try{
						Thread.sleep(TIME);
					}catch(Exception e){}
				}
				break;
			}
			this.direction = (int)(Math.random()*4);//随机出现0~3之间的数字
			if(this.being==false){
				break;
			}

			this.time++;
			if(time%1==0){
				if(being){
					if(bulVec.size()<5){
						this.shootBullet();
					}
				}
			}
		}
	}
	public void eTankSet(Vector<EnemyTank> eTankVec){//敌方坦克集合方法
		this.eTankVec = eTankVec;
	}
		public boolean collision(){//坦克碰撞方法
		boolean b = false;

		switch(this.direction){		
		case 0:
		case 1:
			for(int i=0;i<eTankVec.size();i++){
				enemyTank = eTankVec.get(i);				
				if(enemyTank!=this){
					if(enemyTank.direction==0||enemyTank.direction==1){
						if(this.x>=enemyTank.x-20&&this.x<=enemyTank.x+20&&this.y>=enemyTank.y-30&&this.y<=enemyTank.y+30){
							return true;
						}
					}
					if(enemyTank.direction==2||enemyTank.direction==3){
						if(this.x>=enemyTank.x-25&&this.x<=enemyTank.x+25&&this.y>=enemyTank.y-25&&this.y<=enemyTank.y-5){
							return true;
						}else if(this.x>=enemyTank.x-25&&this.x<=enemyTank.x+25&&this.y>=enemyTank.y+5&&this.y<=enemyTank.y+25){
							return true;
						}
					}
				}
			}
			break;			
		case 2:
		case 3:
			for(int i=0;i<eTankVec.size();i++){
				enemyTank = eTankVec.get(i);
				if(enemyTank!=this){
					if(enemyTank.direction==0||enemyTank.direction==1){
						if(this.x>=enemyTank.x-25&&this.x<=enemyTank.x-5&&this.y>=enemyTank.y-25&&this.y<=enemyTank.y+25){
							return true;
						}else if(this.x>=enemyTank.x+5&&this.x<=enemyTank.x+25&&this.y>=enemyTank.y-25&&this.y<=enemyTank.y+25){
							return true;
						}
					}
					if(enemyTank.direction==2||enemyTank.direction==3){
						if(this.x>=enemyTank.x-30&&this.x<=enemyTank.x+30&&this.y>=enemyTank.y-20&&this.y<=enemyTank.y+20){
							return true;
						}
					}
				}
			}
			break;
		}
		return b;//因为以上所有return都在循环里面，所以编译器会认为不一定会有return被执行
	}
}