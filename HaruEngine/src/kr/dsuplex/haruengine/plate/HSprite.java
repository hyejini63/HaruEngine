package kr.dsuplex.haruengine.plate;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.text.TextUtils;
import bayaba.engine.lib.Sprite;

public class HSprite extends Sprite {

	public String name = "";
	public String imgFile = "";
	
	public HSprite(String index, String imgFilename) {
		this.name = index;
		this.imgFile = imgFilename;
	}
	
	public void LoadGameData(GL10 mGL, Context context) {
		if(!TextUtils.isEmpty(imgFile)) //�̹��� ���� �̸��� �ִ� ���
			this.LoadBitmap(mGL, context, imgFile);
	}
}
