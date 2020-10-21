package com.yw;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Vector;

import javax.swing.JOptionPane;

class Record {//记录类
	private static int eTankNum = 10;//敌方坦克数量
	private static int mTankNum = 2;//玩家坦克数量
	private static int dieTankNum = 0;//消灭敌方坦克数量
	private static FileWriter fw = null;
	private static FileReader fr = null;
	private static BufferedWriter bw = null;
	private static BufferedReader br = null;

	public static Vector<EnemyTank> eTankVec = new Vector<EnemyTank>();
	public static Vector<Location> locatVec = new Vector<Location>();//坦克坐标集合

	public static EnemyTank enemyTank;//必须设为静态的
	
	public static int geteTankNum() {//取敌方坦克数量
		return eTankNum;
	}
	public static void seteTankNum(int eTankNum) {//设置敌方坦克数量
		Record.eTankNum = eTankNum;
	}
	public static int getmTankNum() {//取玩家坦克数量
		return mTankNum;
	}
	public static void setmTankNum(int mTankNum) {//设置玩家坦克数量
		Record.mTankNum = mTankNum;
	}	
	public static int getDieTankNum() {//取消灭敌方坦克数量
		return dieTankNum;
	}
	public static void setDieTankNum(int dieTankNum) {//设置下灭敌方坦克数量
		Record.dieTankNum = dieTankNum;
	}	
	public static Vector<EnemyTank> geteTankVec() {//取敌方坦克集合
		return eTankVec;
	}
	public  static void seteTankVec(Vector<EnemyTank> eTankVec) {//设置敌方坦克集合,注意没有static
		Record.eTankVec = eTankVec;
	}

	public static void eTankdec(){//敌方坦克减少方法
		eTankNum--;
	}
	public static void mTankdec(){//玩家坦克减少方法
		mTankNum--;
		if(mTankNum<=0){//告知用户某事已发生
			JOptionPane.showMessageDialog(null, "你结束了!!!", "Game Over !", JOptionPane.ERROR_MESSAGE); 
			//结束游戏 
			System.exit(0) ; 
		}
	}
	public static void dieTankNum(){//打死敌方坦克数量方法
		dieTankNum++;
	}	
	public static void saveGame(){//游戏存盘方法,注意没有static
		try{
			fw = new FileWriter("./TankGame/saveGame.txt");
			bw = new BufferedWriter(fw);
			bw.write(dieTankNum+"\r\n");
			for(int i=0;i<eTankVec.size();i++){
				enemyTank = eTankVec.get(i);
				if(enemyTank.being){
					String str = enemyTank.x+" "+enemyTank.y+" "+enemyTank.direction;
					bw.write(str+"\r\n");
				}
			}
		}catch(Exception e){
			e.printStackTrace();//在命令行打印异常信息在程序中出错的位置及原因
		}
		finally{//关闭流
			try{
				bw.close();
				fw.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public static Vector<Location> readGame(){//读取游戏记录方法
		try{
			fr = new FileReader("./TankGame/saveGame.txt");
			br = new BufferedReader(fr);
			String str = "";
			str = br.readLine();
			dieTankNum = Integer.parseInt(str);//转换为整型
			while((str=br.readLine())!=null){
				String[] strArr = str.split(" ");//用字符做间隔来确定数组元素
				Location locat = new Location(Integer.parseInt(strArr[0]),Integer.parseInt(strArr[1]),Integer.parseInt(strArr[2]));
				locatVec.add(locat);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{//关闭流
			try{
				fr.close();
				br.close();
			}catch(Exception e){}
		}
		return locatVec;
	}
}