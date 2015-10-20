package kr.dsuplex.haruengine;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import bayaba.engine.lib.Texture;

public class ImagePlate extends Plate {

	public float angle = 0;	
	public Texture texture = new Texture();
	public float scaleX = 1f, scaleY = 1f;
	
	public float halfWidth, halfHeight;
	
	public void setTexture(Texture newTexture) {
		texture = newTexture;
		width = texture.imgWidth;
		height = texture.imgHeight;
	}
	
	public void setTexture(GL10 gl, Context context, String filename) {
		texture.LoadTexture(gl, context, filename);
		width = texture.imgWidth;
		height = texture.imgHeight;
	}
	
	public void setAntiAliasing(boolean enable) {
		texture.Antialiasing = enable;
	}
	
	public void setScale(float scale) {
		scaleX = scaleY = scale;
	}
	
	public void setAngle(float newAngle) {
		this.angle = newAngle;
	}

	@Override
	public void print(GL10 gl) {
		//texture.DrawTexture(gl, posX+(width/2), posY+(height/2), 0, 0, width, height, -width/2, -height/2, angle, scaleX, scaleY);
		texture.DrawTexture(gl, posX, posY, scaleX, scaleY);
	}
}
