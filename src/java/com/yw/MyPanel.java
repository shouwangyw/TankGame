package com.yw;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

class MyPanel extends JPanel implements KeyListener,Runnable{//我的面板类
	MyTank myTank = null;
	//MyTank myTank1 = null;//用于双人玩
	EnemyTank enemyTank = null;
	Tank tank;
	Bullet bullet = null;
	
	Explosion explo = null;
	
	int tankNum = 3;//敌人坦克数量
	Vector<EnemyTank> eTankVec = new Vector<EnemyTank>();//敌人坦克组，做成集合类
	//有了泛型，我们在集合类中添加对象的时候，就不用再强转了
	
	Vector<Explosion>exploVec = new Vector<Explosion>();

	Vector<Location> locatVec = new Vector<Location>();
	
	//爆炸效果图片初始化
	Image picture1 = null;
	Image picture2 = null;
	Image picture3 = null;

	public MyPanel(String str){		
		if(str.equals("newGame")){	
			myTank = new MyTank(150,327);//设定玩家坦克出现的位置、方向和颜色
//			myTank1 = new MyTank(300,330);
			myTank.setDirection(0);
			myTank.setColor(0);
			
			for(int i=0;i<tankNum;i++){
				enemyTank = new EnemyTank((i)*181+9,3);//设定敌方坦克出场位置、方向和颜色
				enemyTank.setDirection(1);
				enemyTank.setColor(1);
				
				eTankVec.add(enemyTank);//将敌方坦克加入敌方坦克集合、并传回敌方坦克集合方法中
				enemyTank.eTankSet(eTankVec);
				bullet = new Bullet(enemyTank.x+10,enemyTank.y+30,1);//设定敌方坦克出场位置
				
				Thread thread1 = new Thread(enemyTank);				
				Thread thread2 = new Thread(bullet);
				thread1.start();//敌人坦克线程开启
				thread2.start();//敌人子弹线程开启				
			}
		}else if(str.equals("conGame")){
			myTank = new MyTank(150,327);//设定玩家坦克出现的位置、方向和颜色
//			myTank1 = new MyTank(300,330);
			myTank.setDirection(0);
			myTank.setColor(0);
			locatVec = Record.readGame();//读取游戏记录
			for(int i=0;i<locatVec.size();i++){//将记录的位置横纵坐标以及方向给敌方坦克
				Location locat = locatVec.get(i);				
				enemyTank = new EnemyTank(locat.x,locat.y);				
				enemyTank.setDirection(locat.direction);
				enemyTank.setColor(1);
				enemyTank.eTankSet(eTankVec);
				eTankVec.add(enemyTank);
				bullet = new Bullet(enemyTank.x+10,enemyTank.y+30,1);
				
				Thread thread1 = new Thread(enemyTank);
				thread1.start();//敌人坦克线程开启					
				Thread thread2 = new Thread(bullet);				
				thread2.start();//敌人子弹线程开启							
			}
		}	
				try{
			picture1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/explo1.png"));
			picture2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/explo2.png"));
			picture3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/explo3.png"));
//			picture1 = ImageIO.read(new File("./explo1.png"));
//			picture2 = ImageIO.read(new File("./explo2.png"));
//			picture3 = ImageIO.read(new File("./explo3.png"));
		}catch(Exception e){}
		Voice voice = new Voice("./TankGame/TankGameStartMusic.wav");
		voice.start();		
	}	
	public void recData(Graphics g){//记录数据的方法
		this.drawTank(80, 390, g, 0, 0);
		g.setColor(Color.black);
		g.drawString(Record.getmTankNum()+"", 120, 410);//整型数据后面加双引号，会把整型数据转换成字符串
		this.drawTank(160, 390, g, 1, 0);
		g.setColor(Color.black);
		g.drawString(Record.geteTankNum()+"", 200, 410);
		this.drawTank(480, 180, g, 1, 0);
		g.setColor(Color.black);
		g.drawString(Record.getDieTankNum()+"", 520, 200);
		g.setColor(Color.red);
		Font f = new Font("楷体",Font.BOLD,25);
		g.setFont(f);
		g.drawString("您消灭的坦克总数", 410, 150);		
	}
	public void paint(Graphics g){//用于调用绘制坦克、子弹等的方法
		super.paint(g);
		g.setColor(Color.gray);
		
		g.fillRect(0, 0, 640, 520);		
		g.setColor(Color.black);
		g.fillRect(0, 0, 400, 360);
		this.recData(g);
		g.setColor(Color.red);
		g.fill3DRect(0, 0, 3, 360, false);		
		g.fill3DRect(0, 0, 400, 3, false);		
		g.fill3DRect(0, 357, 400, 3, false);		
		g.fill3DRect(397, 0, 3, 360, false);
				

		if(myTank.being){//绘制出玩家的坦克、子弹和爆炸效果
			this.drawTank(myTank.getX(), myTank.getY(), g, myTank.color, myTank.direction);
			//this.drawTank(myTank1.getX(),myTank1.getY(),g,myTank1.color,myTank1.direction);
			this.drawBullet(myTank.getX(), myTank.getY(), g, myTank);
			this.drawExplosion(myTank.getX(), myTank.getY(), g);
		}

		for(int i=0;i<eTankVec.size();i++){//绘制出敌方的坦克、子弹和爆炸效果
			enemyTank = eTankVec.get(i);//把敌方的每一辆坦克取出
			if(enemyTank.being){
				this.drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.color, enemyTank.direction);
				this.drawBullet(enemyTank.getX(), enemyTank.getY(), g, enemyTank);
				this.drawExplosion(enemyTank.getX(), enemyTank.getY(), g);
			}			
		}			
	}
	public boolean hitTarget(Bullet bullet,Tank tank){//击中坦克方法
		boolean b = false;

		switch(tank.direction){
		case 0:
		case 1:
			if(bullet.x>tank.x-1&&bullet.x<tank.x+21&&bullet.y>tank.y-4&&bullet.y<tank.y+26){
				bullet.being = false;
				tank.being = false;
				b = true;
				explo = new Explosion(tank.x,tank.y);
				exploVec.add(explo);
			}
			break;
		case 2:
		case 3:
			if(bullet.x>tank.x-4&&bullet.x<tank.x+26&&bullet.y>tank.y+1&&bullet.y<tank.y+21){
				bullet.being = false;
				tank.being = false;
				b = true;
				explo = new Explosion(tank.x,tank.y);
				exploVec.add(explo);
			}			
		}
		return b;
	} 
	public void hitEnemy(){//玩家坦克击中敌方坦克方法
		for(int i=0;i<myTank.bulVec.size();i++){//子弹打到坦克后子弹和坦克一起消失
			//这里用二层循环的作用是，让每发子弹都去和对方坦克比较
			bullet = myTank.bulVec.get(i);//把玩家的每一颗子弹取出
			if(bullet.being){
				for(int j=0;j<eTankVec.size();j++){
					enemyTank = eTankVec.get(j);//把敌方的每一辆坦克取出
					if(enemyTank.being){
						if(this.hitTarget(bullet, enemyTank)){
							Record.eTankdec();
							Record.dieTankNum();
						}
					}
				}				
			}
		}
	}
	public void hitMy(){//敌方坦克击中玩家坦克方法
		for(int i=0;i<this.eTankVec.size();i++){
			enemyTank = eTankVec.get(i);//把敌方的每一辆坦克取出
			for(int j=0;j<enemyTank.bulVec.size();j++){
				bullet = enemyTank.bulVec.get(j);//再把敌方的每一颗子弹取出
				if(myTank.being){
					if(this.hitTarget(bullet, myTank)){
						Record.mTankdec();						
					}
				}
			}
		}
	}
	public void drawTank(int x,int y,Graphics g,int color,int direction){//绘制各种坦克的方法
		//x、y：横纵坐标；g：画笔；type:类型；direction：方向
		switch(color){
		case 0://代表玩家的坦克
			g.setColor(Color.yellow);	break;
		case 1://代表敌人的坦克
			g.setColor(Color.green);	break;
		case 2://扩展
			g.setColor(Color.red); 		break;
		}
		switch(direction){
		case 0://上
			g.fill3DRect(x+5, y, 5, 30, false);
			g.fill3DRect(x+20, y, 5, 30, false);
			g.fill3DRect(x+10, y+5, 10, 20, false);
			g.fillOval(x+10, y+10, 10, 10);
			g.drawLine(x+15, y+15, x+15, y-3);
			break;
		case 1://下
			g.fill3DRect(x+5, y, 5, 30, false);
			g.fill3DRect(x+20, y, 5, 30, false);
			g.fill3DRect(x+10, y+5, 10, 20, false);
			g.fillOval(x+10, y+10, 10, 10);
			g.drawLine(x+15, y+15, x+15, y+33);
			break;
		case 2://左
			g.fill3DRect(x, y+5, 30, 5, false);
			g.fill3DRect(x, y+20, 30, 5, false);
			g.fill3DRect(x+5, y+10, 20, 10, false);
			g.fillOval(x+10, y+10, 10, 10);
			g.drawLine(x+15, y+15, x-3, y+15);
			break;
		case 3://右
			g.fill3DRect(x, y+5, 30, 5, false);
			g.fill3DRect(x, y+20, 30, 5, false);
			g.fill3DRect(x+5, y+10, 20, 10, false);
			g.fillOval(x+10, y+10, 10, 10);
			g.drawLine(x+15, y+15, x+33, y+15);
			break;
		}
	}
	public void drawBullet(int x,int y,Graphics g,Tank tank){//绘制子弹的方法
		for(int i=0;i<tank.bulVec.size();i++){	
			bullet = tank.bulVec.get(i);//遍历取出子弹

			if(bullet!=null&&bullet.being==true){
				g.setColor(Color.white);
				g.fill3DRect(bullet.x+4, bullet.y+4, 3, 3, false);
			}
			if(bullet.being==false){
				tank.bulVec.remove(bullet);
			}
		}
	}
	public void drawExplosion(int x,int y,Graphics g){//绘制爆炸效果的方法
		for(int i=0;i<exploVec.size();i++){
			explo = exploVec.get(i);
			explo.survival();//启动坦克爆炸生存期方法

			switch(explo.sur){
			case 3:				
				g.drawImage(picture1, explo.x, explo.y, 30, 30, this);
				break;
			case 2:
				g.drawImage(picture2, explo.x+5, explo.y+5, 20, 20, this);
				break;
			case 1:
				g.drawImage(picture3, explo.x+10, explo.y+10, 10, 10, this);
				break;
			case 0:
				exploVec.remove(explo);
				break;
			}
		}
	}

	//重写KeyListener中的方法
	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_W||e.getKeyCode()==KeyEvent.VK_UP){//上
			this.myTank.setDirection(0);
			this.myTank.direcUp();
		}else if(e.getKeyCode()==KeyEvent.VK_S||e.getKeyCode()==KeyEvent.VK_DOWN){//下
			this.myTank.setDirection(1);
			this.myTank.direcDown();
		}else if(e.getKeyCode()==KeyEvent.VK_A||e.getKeyCode()==KeyEvent.VK_LEFT){//左
			this.myTank.setDirection(2);
			this.myTank.direcLeft();
		}else if(e.getKeyCode()==KeyEvent.VK_D||e.getKeyCode()==KeyEvent.VK_RIGHT){//右
			this.myTank.setDirection(3);
			this.myTank.direcRight();
		}
		if(e.getKeyCode()==KeyEvent.VK_ENTER){//开炮
			if(this.myTank.bulVec.size()<8){//控制子弹连发数目
				this.myTank.shootBullet();
				Voice voice = new Voice("./TankGame/bullet.wav");
				voice.start();
			}			
		}
		this.repaint();
	}

	public void run(){
		while(true){
			try{
				Thread.sleep(100);//睡眠100ms后重绘				
			}catch(Exception e){}
			this.hitEnemy();
			this.hitMy();
			this.repaint();
		}
	}
}