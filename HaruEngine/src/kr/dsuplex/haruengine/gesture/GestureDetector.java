package kr.dsuplex.haruengine.gesture;

import android.os.Handler;

public abstract class GestureDetector {
	
	private static final int LONG_CLICK_TIME = 800; //롱터치로 인식할 시간, 밀리초 단위
	private static final int CLICK_OR_DRAG_DISTANCE = 15; //눌렀다 뗀 픽셀 단위

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
				//터치가 떨어졌을 때, LONG이나 Drag가 아닌 경우, click일 가능성 확인
				//최초 터치 위치와 터치가 떨어진 지점이 CLICK_OR_DRAG_DISTANCE의 반경 이내라면 click 제스쳐
				if((this.touchedX - CLICK_OR_DRAG_DISTANCE <= touchX && this.touchedX + CLICK_OR_DRAG_DISTANCE >= touchX)
						&& (this.touchedY - CLICK_OR_DRAG_DISTANCE <= touchY && this.touchedY + CLICK_OR_DRAG_DISTANCE >= touchY)
						&& touchedMS + LONG_CLICK_TIME > System.currentTimeMillis()) //클릭범위 이내일 때 + 터치 시작시간으로부터 LONG_CLICK_TIME 이내일 때
				{
					onClick(this.touchedX, this.touchedY);
				}
				else
				{
					//click 제스쳐가 아닌경우 아무것도 하지 않는다.
					//fling일 가능성이 있음
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
				//계속 눌린 상태로 CLICK_OR_DRAG_DISTANCE보다 거리가 멀어지면 drag이다.
				//TODO: drag관련 구현
				if((this.touchedX - CLICK_OR_DRAG_DISTANCE <= touchX && this.touchedX + CLICK_OR_DRAG_DISTANCE >= touchX)
						&& (this.touchedY - CLICK_OR_DRAG_DISTANCE <= touchY && this.touchedY + CLICK_OR_DRAG_DISTANCE >= touchY)) //클릭범위 이내일 때
				{
					//CLICK_OR_DRAG_DISTANCE 범위 안쪽에서 움직임
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
	//public abstract void onFling(); //플링은 천천히 만들자
}
