package kr.dsuplex.haruengine.plate;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.text.TextUtils;
import bayaba.engine.lib.GameObject;
import bayaba.engine.lib.Sprite;

public class HPlate extends GameObject {

	private Context MyContext;
	private GL10 mGL;
	private String imageFilename;
	public HPlate(Context context, String filename) {
		MyContext = context;
		imageFilename = filename;
	}
	
	public void LoadGameData(GL10 gl) {
		mGL = gl;
		
		if(!TextUtils.isEmpty(imageFilename))
		{
			Sprite imgFile = new Sprite();
			imgFile.LoadBitmap(mGL, MyContext, imageFilename);
			SetObject(imgFile, 0, 0, 0, 0, 0, 0);
		}
	}
}
