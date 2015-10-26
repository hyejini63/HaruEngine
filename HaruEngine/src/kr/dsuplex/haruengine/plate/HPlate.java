package kr.dsuplex.haruengine.plate;

import java.util.ArrayList;

import bayaba.engine.lib.GameObject;

public class HPlate extends GameObject {
		
	public String name = "";
	public String imgName = "";
	
	public HPlate(String name, String imgName, float x, float y) {
		this.name = name;
		this.imgName = imgName;
		this.x = x;
		this.y = y;
	}

	public void LoadGameData(ArrayList<HSprite> sprites) {
		for(HSprite sprite : sprites)
			if(imgName.equals(sprite.name))
				this.SetObject(sprite, 0, 0, x, y, 0, 0);
	}

	public boolean PushButton(boolean push, float touchX, float touchY) {
		if(push == true && this.CheckPos(touchX, touchY))
			return true;
		return false;
	}
}
