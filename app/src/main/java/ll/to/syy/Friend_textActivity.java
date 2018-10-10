package ll.to.syy;

import ll.to.syy.R;

import com.thunder.flytxtview.view.FlyTxtView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

public class Friend_textActivity extends Activity {
	private FlyTxtView flyTxtView;
	//我们在亲人的周围成长，给我们温馨的家和健壮的体魄，他们是我们的依靠
	private Handler myHandler = new Handler();
	private Runnable runnable = new Runnable() {
		public void run() {
			startActivity(new Intent(Friend_textActivity.this,FriendActivity.class));
			finish();
		}

	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_text);
		
		setupViews();
		
		myHandler.postDelayed(runnable, 1000 * 8);
	}

	protected void setupViews() {
		flyTxtView = (FlyTxtView) findViewById(R.id.friend_fly_txt);
		flyTxtView.setTextSize(20);
		flyTxtView.setTextColor(Color.RED);
		flyTxtView.setTexts("从懂事开始我们就一直有友情，友情就开始陪伴我们这一生");
		flyTxtView.startAnimation();
	}
}
