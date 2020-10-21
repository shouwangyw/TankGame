package com.yw;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TankGame extends JFrame implements ActionListener {//主程序启动
	MyPanel myPanel = null;
	LevelPanel levelPanel = null;
	JMenuBar menuBar = null;//菜单栏
	JMenu menu1 = null;//菜单
	JMenuItem menuItem1 = null;//子菜单
	JMenuItem menuItem2 = null;
	JMenuItem menuItem3 = null;
	JMenuItem menuItem4 = null;

	public static void main(String[] args){		
		TankGame t = new TankGame();
	}
	public TankGame(){
		menuBar = new JMenuBar();
		menu1 = new JMenu("游戏(G)");
		menu1.setMnemonic('G');
		menuItem1 = new JMenuItem("新游戏(N)");
		menuItem2 = new JMenuItem("继续游戏(C)");
		menuItem3 = new JMenuItem("存盘退出(S)");
		menuItem4 = new JMenuItem("退出游戏(E)");
		menuItem1.addActionListener(this);
		menuItem2.addActionListener(this);
		menuItem3.addActionListener(this);
		menuItem4.addActionListener(this);
		menuItem1.setActionCommand("newGame");
		menuItem2.setActionCommand("conGame");
		menuItem3.setActionCommand("saveExit");
		menuItem4.setActionCommand("exit");
		menu1.add(menuItem1);
		menu1.add(menuItem2);
		menu1.add(menuItem3);
		menu1.add(menuItem4);
		menuBar.add(menu1);
		
		levelPanel = new LevelPanel();
		Thread thread = new Thread(levelPanel);
		thread.start();
		
		this.setJMenuBar(menuBar);
		this.add(levelPanel);
		
		this.setTitle("坦克大战");
		this.setIconImage((new ImageIcon("./TankGame/tank2.png")).getImage());
		this.setSize(640, 520);
		this.setLocation(100, 100);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("newGame")){
			myPanel = new MyPanel("newGame");
			this.remove(levelPanel);//删除关卡面板
			this.add(myPanel);
			this.addKeyListener(myPanel);
			Thread thread = new Thread(myPanel);
			thread.start();
			this.setVisible(true);//开显示
		}else if(e.getActionCommand().equals("conGame")){
			myPanel = new MyPanel("conGame");
			Thread thread = new Thread(myPanel);
			thread.start();
			this.remove(levelPanel);
			this.add(myPanel);
			this.addKeyListener(myPanel);
			this.setVisible(true);
		}else if(e.getActionCommand().equals("saveExit")){
			Record.seteTankVec(myPanel.eTankVec);//是静态方法所以不用new对象
			Record.saveGame();
			System.exit(0);
		}else if(e.getActionCommand().equals("exit")){
			System.exit(0);
		}
	}
}