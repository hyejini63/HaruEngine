package kr.dsuplex.haruengine.gesture;

import android.os.Handler;

public abstract class GestureDetector {
	
	private static final int LONG_CLICK_TIME = 800; //����ġ�� �ν��� �ð�, �и��� ����
	private static final int CLICK_OR_DRAG_DISTANCE = 15; //������ �� �ȼ� ����

	public static final int TOUCH_NONE = 0;
	public static final int TOUCH_PRESSED = 1;
	public static final int TOUCH_DRAG = 2;
	
	private int touchedState = TOUCH_NONE;
	private float touchedX = -1, touchedY = -1;
	private long touchedMS = 0;
	private float x, y, width, height;
	
	private Handler longTouchCatcher = null;
	private Runnable longTouchRun = new Runnable() {

		@Override
		public void run() {
			onLongClick(touchedX, touchedY);
		}
		
	};
	
	public void setPushArea(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public boolean push(boolean push, float touchX, float touchY) {
		switch(touchedState)
		{
		case TOUCH_NONE:
			if(push == true
			&& (touchX >= x && touchX <= x + width)
			&& (touchY >= y && touchY <= y + height))
			{
				touchedState = TOUCH_PRESSED;
				this.touchedX = touchX;
				this.touchedY = touchY;
				this.touchedMS = System.currentTimeMillis();
				
				if(longTouchCatcher == null) longTouchCatcher = new Handler();
				
				longTouchCatcher.postDelayed(longTouchRun, LONG_CLICK_TIME);
				
				onTouchDown(this.touchedX, this.touchedY);
				
				return true;
			}
			else
			{
				return false;
			}
		case TOUCH_PRESSED:
			if(push == false)
			{
				//��ġ�� �������� ��, LONG�̳� Drag�� �ƴ� ���, click�� ���ɼ� Ȯ��
				//���� ��ġ ��ġ�� ��ġ�� ������ ������ CLICK_OR_DRAG_DISTANCE�� �ݰ� �̳���� click ������
				if((this.touchedX - CLICK_OR_DRAG_DISTANCE <= touchX && this.touchedX + CLICK_OR_DRAG_DISTANCE >= touchX)
						&& (this.touchedY - CLICK_OR_DRAG_DISTANCE <= touchY && this.touchedY + CLICK_OR_DRAG_DISTANCE >= touchY)
						&& touchedMS + LONG_CLICK_TIME > System.currentTimeMillis()) //Ŭ������ �̳��� �� + ��ġ ���۽ð����κ��� LONG_CLICK_TIME �̳��� ��
				{
					onClick(this.touchedX, this.touchedY);
				}
				else
				{
					//click �����İ� �ƴѰ�� �ƹ��͵� ���� �ʴ´�.
					//fling�� ���ɼ��� ����
				}
				
				onTouchUp(touchX, touchY);
				
				touchedState = TOUCH_NONE;
				this.touchedX = -1;
				this.touchedY = -1;
				this.touchedMS = 0;
				longTouchCatcher.removeCallbacks(longTouchRun);
			}
			else
			{
				//��� ���� ���·� CLICK_OR_DRAG_DISTANCE���� �Ÿ��� �־����� drag�̴�.
				//TODO: drag���� ����
				if((this.touchedX - CLICK_OR_DRAG_DISTANCE <= touchX && this.touchedX + CLICK_OR_DRAG_DISTANCE >= touchX)
						&& (this.touchedY - CLICK_OR_DRAG_DISTANCE <= touchY && this.touchedY + CLICK_OR_DRAG_DISTANCE >= touchY)) //Ŭ������ �̳��� ��
				{
					//CLICK_OR_DRAG_DISTANCE ���� ���ʿ��� ������
				}
				else
				{
					touchedState = TOUCH_DRAG;
					longTouchCatcher.removeCallbacks(longTouchRun);
				}
					
			}
			return true;
		case TOUCH_DRAG:
			if(push == false)
			{
				onTouchUp(touchX, touchY);
				
				touchedState = TOUCH_NONE;
				this.touchedX = -1;
				this.touchedY = -1;
				this.touchedMS = 0;
			}
			else
			{
				onDrag(this.touchedX, this.touchedY, touchX, touchY);
			}
			return true;
		}
		
		return false;
	}

	public abstract void onTouchDown(float x, float y);
	public abstract void onTouchUp(float x, float y);
	public abstract void onClick(float x, float y);
	//public abstract void onDoubleClick();
	public abstract void onLongClick(float x, float y);
	public abstract void onDrag(float oldX, float oldY, float currentX, float currentY);
	//public abstract void onFling(); //�ø��� õõ�� ������
}
