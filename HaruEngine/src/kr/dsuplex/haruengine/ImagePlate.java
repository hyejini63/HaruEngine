package kr.dsuplex.haruengine;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import bayaba.engine.lib.Texture;

public class ImagePlate extends Plate {

	public float angle = 0;
	@Override
	public void print(GL10 gl) {
		super.print(gl);

		//texture.DrawTexture(gl, posX, posY, 1f, 1f);
		texture.DrawTexture(gl, posX, posY, 0, 0, width, height, 0, 0, angle, 1f, 1f);
	}
	
	public Texture texture;
	public void setTexture(GL10 gl, Context context){
		texture = new Texture();
		texture.LoadTexture(gl, context, "face.png");
		width = texture.imgWidth;
		height = texture.imgHeight;
	}
	
	public void setAngle(float newAngle) {
		this.angle = newAngle;
	}
	
}
