package kr.dsuplex.haruengine.activity;

import java.util.ArrayList;

import android.content.Context;
import bayaba.engine.lib.GameInfo;
import kr.dsuplex.haruengine.GameMain;
import kr.dsuplex.haruengine.plate.HPlate;
import kr.dsuplex.haruengine.plate.HSprite;

public class HGameMain extends GameMain {

	private ArrayList<HSprite> sprites = new ArrayList<HSprite>(); //이미지들	
	private ArrayList<HPlate> plates = new ArrayList<HPlate>(); //플레이트들
	
	public HGameMain(Context context, GameInfo info) {		
		super(context, info);
		
		created();
	}	

	@Override
	public void LoadGameData() {
		for(int i = 0 ; i < sprites.size() ; i++)
			sprites.get(i).LoadGameData(mGL, MainContext);

		for(int i = 0 ; i < plates.size() ; i++)
			plates.get(i).LoadGameData(sprites);
		
	}

	@Override
	public void PushButton(boolean push) {
		for(int i = 0 ; i < plates.size() ; i++)
			if(plates.get(i).PushButton(push, TouchX, TouchY))
				onTouchDown(plates.get(i).name, TouchX, TouchY);
	}

	@Override
	public void DoGame() {
		for(HPlate plate : plates)
			if(plate.dead != true)
				plate.DrawSprite(gInfo);
	}
	
	////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////
	public void addSprite(String name, String imgFilename) {
		sprites.add(new HSprite(name, imgFilename));
	}
	
	public void addPlate(String name, String imgName, float x, float y) {
		plates.add(new HPlate(name, imgName, x, y));
	}
	
	public void addPlate(String name, HPlate plate) {
		plate.name = name;
		plates.add(plate);
	}
	
	public HSprite getSprite(String name) {
		for(HSprite sprite : sprites)
			if(sprite.name.equals(name))
				return sprite;
		
		return null;
	}
	
	public HPlate getPlate(String name) {
		for(HPlate plate : plates)
			if(plate.name.equals(name))
				return plate;
		
		return null;
	}
	public void onTouchDown(String name, float touchX, float touchY) {
		//플레이트가 터치되면 여기에 들어온다. 클릭이 아니라 터치다운 되면 바로 들어옴
	}
}
