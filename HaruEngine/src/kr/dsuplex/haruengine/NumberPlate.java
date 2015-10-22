package kr.dsuplex.haruengine;

import java.text.DecimalFormat;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import bayaba.engine.lib.Texture;

public class NumberPlate extends Plate {

	private static final int NUMBER_IMAGE_LENGTH = 12; // 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, comma(,), dot(.)
	private static final int COMMA = 10;
	private static final int DOT = 11;
	private Texture[] source = new Texture[NUMBER_IMAGE_LENGTH];
	private double number = 0;
	private String numberString = "";
	public float scale = 1f;
	
	private DecimalFormat df = new DecimalFormat("#,##0");//("#,##0.000");
	
	public NumberPlate() {
		setNumber(0);
		for(int i = 0 ; i < NUMBER_IMAGE_LENGTH ; i++)
			source[i] = new Texture();
	}
	
	public void loadImages(GL10 gl, Context context) {
		for(int i = 0 ; i < NUMBER_IMAGE_LENGTH ; i++)
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
	
	public void addNumber(double number) {
		setNumber(this.number + number);
	}
	
	public void setNumber(double number) {
		this.number = number;
		convertNumber();
	}
	
	private void convertNumber() {
		numberString = df.format(number);
	}
	
	public void release() {
		for(int i = 0 ; i < NUMBER_IMAGE_LENGTH ; i++)
			source[i].Release();
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
			else if( c == '.')
				index = DOT;
			
			if(index >= 0)
			{
				if(index == 1 || index >= COMMA)
					currentX -= source[index].imgWidth / 4;
				
				source[index].DrawTexture(gl, currentX, posY, scale, scale);
				currentX += source[index].imgWidth;

				if(index == 1 || index >= COMMA)
					currentX -= source[index].imgWidth / 6;
			}
		}
	}
}
