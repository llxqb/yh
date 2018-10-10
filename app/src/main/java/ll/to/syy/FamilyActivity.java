package ll.to.syy;

import ll.to.syy.R;

import com.thunder.flytxtview.view.FlyTxtView;
import com.yh.DrawYH;
import com.yh.HolderSurfaceView;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;

public class FamilyActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_family);
		
		SurfaceView v = new SurfaceView(this);
		HolderSurfaceView.getInstance().setSurfaceView(v);
		v.setBackgroundResource(R.drawable.family);
		this.setContentView(v);
		int raw_id=R.raw.cunni;
		DrawYH yh=new DrawYH(raw_id);		
		v.setOnTouchListener(yh);
		yh.begin();
		
		
	}

}
