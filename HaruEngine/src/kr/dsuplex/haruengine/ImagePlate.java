package kr.dsuplex.haruengine;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import bayaba.engine.lib.Texture;

public class ImagePlate extends Plate {

	public float angle = 0;	
	public Texture texture = new Texture();
	
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
	
	public void setAngle(float newAngle) {
		this.angle = newAngle;
	}

	@Override
	public void print(GL10 gl) {
		super.print(gl);
		texture.DrawTexture(gl, posX, posY, 0, 0, width, height, 0, 0, angle, 1f, 1f);
	}
}
