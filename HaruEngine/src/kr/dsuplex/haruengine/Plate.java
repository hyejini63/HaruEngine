package kr.dsuplex.haruengine;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class Plate {
	public float posX = 0 , posY = 0;
	public float width = 0 ,height = 0;
	public float color = 0;
	public ArrayList<Plate> childs = new ArrayList<Plate>();
	
	public void setPosition(float x,float y){
		posX = x;
		posY = y;
	}
	
	public void print(GL10 gl){
		// ����Ʈ�Ұ�
	}
}
