package ll.to.syy;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.yalantis.phoenix.PullToRefreshView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WelcomeActivity extends Activity {

    private PullToRefreshView mPullToRefreshView;
    public static final int REFRESH_DELAY = 2000;
    private String TAG = "WelcomeActivity";
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initView();
    }

    private void initView() {
        mPullToRefreshView = findViewById(R.id.pull_to_refresh);
        System.currentTimeMillis();

        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WelcomeActivity.this, getCurrentTime(), Toast.LENGTH_LONG).show();
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, REFRESH_DELAY);
            }
        });
    }


    private String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        String currentData = simpleDateFormat.format(date);
        String currentHour = currentData.substring(12, 14);
        Log.e(TAG, currentHour);

        if (Integer.parseInt(currentHour) >= 4 && Integer.parseInt(currentHour) < 6) {
            text = "现在时间是" + currentData + "\n" + "是什么让你起这么早呢？梦想吗？还是在想谁？嘻嘻~";
        } else if (Integer.parseInt(currentHour) >= 6 && Integer.parseInt(currentHour) < 9) {
            text = "现在时间是" + currentData + "\n" + "早上好，雅琴";
        } else if (Integer.parseInt(currentHour) >= 9 && Integer.parseInt(currentHour) < 12) {
            text = "现在时间是" + currentData + "\n" + "上午好，雅琴";
        } else if (Integer.parseInt(currentHour) >= 12 && Integer.parseInt(currentHour) < 18) {
            text = "现在时间是" + currentData + "\n" + "下午好，雅琴";
        } else if (Integer.parseInt(currentHour) >= 18 && Integer.parseInt(currentHour) < 22) {
            text = "现在时间是" + currentData + "\n" + "晚上好，雅琴";
        } else if (Integer.parseInt(currentHour) >= 22 && Integer.parseInt(currentHour) < 24) {
            text = "现在时间是" + currentData + "\n" + "夜深了，早点休息";
        } else if (Integer.parseInt(currentHour) >= 0 && Integer.parseInt(currentHour) < 4) {
            text = "现在时间是" + currentData + "\n" + "夜深了，早点休息";
        }
        return text;
    }
}
