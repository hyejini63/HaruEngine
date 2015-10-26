package kr.dsuplex.haruengine.activity;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import bayaba.engine.lib.GameInfo;
import kr.dsuplex.haruengine.GLView;
import kr.dsuplex.haruengine.SurfaceClass;

public abstract class HActivity extends Activity {

	private GLView play;
	private HGameMain gMain;
	public GameInfo gInfo;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().addFlags( WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON );
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setVolumeControlStream( AudioManager.STREAM_MUSIC );
        
        gInfo = new GameInfo( 1080, 1920 ); // ���� ����̴�. ���� ��带 �ϰ� ������ 800, 480���� �����ϸ� �ȴ�. ���ػ󵵷� ��ȯ�ص� �����ϴ�.
        gInfo.ScreenXsize = getResources().getDisplayMetrics().widthPixels;
        gInfo.ScreenYsize = getResources().getDisplayMetrics().heightPixels;
        gInfo.SetScale();

        gMain = setGameMain( this, gInfo );
        play = new GLView( this, gMain );
        play.setRenderer( new SurfaceClass(gMain) );
        
        setContentView( play );
    }
    
    public abstract HGameMain setGameMain(Context context, GameInfo gInfo);
}
