package com.yw;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

class Voice extends Thread{
	private String strFile;//文件名

	public Voice(String voiFile){
		strFile = voiFile;
	}
	public void run(){
		File file = new File(strFile);

		AudioInputStream audInStr = null;//音频输入流
		try{
			audInStr = AudioSystem.getAudioInputStream(file);
		}catch(Exception e){}

		AudioFormat auFor = audInStr.getFormat();//音频格式处理
		SourceDataLine souDaLi = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, auFor);

		try{//格式化包装
			souDaLi = (SourceDataLine) AudioSystem.getLine(info);
			souDaLi.open(auFor);
		}catch(Exception e){}
		souDaLi.start();

		int ch = 0;
		byte[] bufByte = new byte[1024];
		try{
			while(ch!=-1){//一个一个读取
				ch = audInStr.read(bufByte, 0, bufByte.length);//从音频流读取指定的最大数量的数据字节，并将其放入给定的字节数组中
				if(ch>=0){
					souDaLi.write(bufByte, 0, ch);//通过此源数据行将音频数据写入混频器
				}
			}
		}catch(Exception e){}
		finally{
			try{
				souDaLi.drain();//将残留部分处理干净
				souDaLi.close();
			}catch(Exception e){}		
		}
	}
}