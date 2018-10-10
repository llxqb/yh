package ll.to.syy;

import ll.to.syy.R;

import com.thunder.flytxtview.view.FlyTxtView;
import com.yh.DrawYH;
import com.yh.HolderSurfaceView;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class FriendActivity extends Activity {
	//从懂事开始我们就一直有友情，友情就开始陪伴我们这一生
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend);
		SurfaceView v = new SurfaceView(this);
		HolderSurfaceView.getInstance().setSurfaceView(v);
		v.setBackgroundResource(R.drawable.friend);
		this.setContentView(v);
		int raw_id=R.raw.bg;
		DrawYH yh=new DrawYH(raw_id);		
		v.setOnTouchListener(yh);
		yh.begin();
		
//		setupViews();
	}
	
	

}
