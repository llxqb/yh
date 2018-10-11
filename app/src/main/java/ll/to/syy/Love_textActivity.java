package ll.to.syy;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import com.thunder.flytxtview.view.FlyTxtView;

public class Love_textActivity extends Activity {
	private FlyTxtView flyTxtView;
	private Handler myHandler = new Handler();
	private Runnable runnable = new Runnable() {
		public void run() {
			startActivity(new Intent(Love_textActivity.this,LoveActivity.class));
			finish();
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_love_text);
		setupViews();
		
		myHandler.postDelayed(runnable, 1000 * 12);
	}

	protected void setupViews() {
		String text = "happy every day , smile     every day";
		flyTxtView = (FlyTxtView) findViewById(R.id.love_fly_txt);
		flyTxtView.setTextSize(20);
		flyTxtView.setTextColor(Color.RED);
		flyTxtView.setTexts(text);
		flyTxtView.startAnimation();
	}
}
