package kr.dsuplex.haruengine;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import kr.dsuplex.haruengine.gesture.GestureDetector;

public class Plate {
	public float posX = 0 , posY = 0;
	public float width = 0 ,height = 0;
	public float color = 0;
	public ArrayList<Plate> childs = new ArrayList<Plate>();
	
	public GestureDetector gestureDetector = null;
	
	public boolean touched = false;
	/** 
	 * @param x : Plate가 위치할 x좌표
	 * @param y : Plate가 위치할 Y좌표
	 */
	public void setPosition(float x,float y){
		posX = x;
		posY = y;
	}
	
	/**
	 * 
	 * @param touch : 터치에 반응할지 여부, false인 경우 터치해도 터치관련 메소드에 반응하지 않음
	 */
	public void touchEnable(GestureDetector detector) {
		detector.setPushArea(posX, posY, width, height);
		gestureDetector = detector;
	}
	
	/**
	 * 
	 * @param push : 터치상태 true인 경우 터치 됨
	 * @param touchX
	 * @param touchY
	 * @return
	 */
	public boolean push(boolean push, float touchX, float touchY) {

		for(Plate child : childs)
			if(child.push(push, touchX, touchY))
				return true;
		
		if(gestureDetector != null)
		{
			if(gestureDetector.push(push, touchX, touchY))
				return true;
			else
				return false;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 
	 * @param gl
	 */	
	public void print(GL10 gl){
		// 프린트할곳
	}
}
