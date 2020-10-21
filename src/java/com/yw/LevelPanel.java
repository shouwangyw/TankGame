package com.yw;

import java.awt.*;

import javax.swing.JPanel;

class LevelPanel extends JPanel implements Runnable{//关卡面板类
	int times = 0;
	
	public void paint(Graphics g){
		super.paint(g);
		g.fillRect(0, 0, 400, 360);
		if(times%2==0){
			g.setColor(Color.yellow);
			Font myFont = new Font("楷体",Font.BOLD,40);
			g.setFont(myFont);
			g.drawString("第一关", 140, 200);
		}
	}
	public void run(){
		while(true){
			try{
				Thread.sleep(600);//控制闪烁时间
			}catch(Exception e){}
			times++;
			this.repaint();
		}
	}
}
