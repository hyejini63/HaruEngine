package kr.dsuplex.haruengine;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import bayaba.engine.lib.Texture;

public class NumberPlate extends Plate {

	private static final int NUMBER_IMAGE_LENGTH = 11; // 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, comma(,)
	private static final int COMMA = 10;
	private Texture[] source = new Texture[NUMBER_IMAGE_LENGTH];
	private double number = 0;
	private String numberString = "";
	public float scale = 1f;
	public NumberPlate() {
		for(Texture tex : source)
			tex = new Texture();
	}
	
	public void loadImages(GL10 gl, Context context) {
		for(int i = 0 ; i < NUMBER_IMAGE_LENGTH ; i++)
			if(i == NUMBER_IMAGE_LENGTH - 1)
				source[i].LoadTexture(gl, context, R.drawable.num_comma);
			else
				source[i].LoadTexture(gl, context, R.drawable.num_0 + i);
	}
	
	public void loadImages(GL10 gl, Context context, String[] filenames) {
		if(filenames == null || filenames.length != NUMBER_IMAGE_LENGTH)
			loadImages(gl, context);
		else
		{
			for(int i = 0 ; i < NUMBER_IMAGE_LENGTH ; i++)
				source[i].LoadTexture(gl, context, filenames[i]);
		}
	}
	
	public void setNumber(double number) {
		this.number = number;
		convertNumber();
	}
	
	private void convertNumber() {
		numberString = String.valueOf(number);
	}

	@Override
	public void print(GL10 gl) {
		float currentX = posX;
		for(int i = 0 ; i < numberString.length() ; i++)
		{
			char c = numberString.charAt(i);
			int index = -1;
			
			if(c >= '0' && c <= '9')
				index = c - '0';
			else if( c == ',')
				index = COMMA;
			
			if(index >= 0)
				source[index].DrawTexture(gl, currentX, posY, scale, scale);
			
			currentX += source[index].imgWidth;
		}
	}
}
