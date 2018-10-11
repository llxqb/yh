package ll.to.syy;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.adapter.WordAnimAdapter;
import com.yalantis.phoenix.PullToRefreshView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WelcomeActivity extends Activity {

    private PullToRefreshView mPullToRefreshView;
    private RecyclerView mRecyclerView;
    public static final int REFRESH_DELAY = 2000;
    private WordAnimAdapter mWordAnimAdapter;
    private String TAG = "WelcomeActivity";
    private String text;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initView();
        initRecycler();
    }

    private void initRecycler() {
        List<Character> list = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add((char) (65 + i));
        }
        mWordAnimAdapter = new WordAnimAdapter(list, WelcomeActivity.this);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setAdapter(mWordAnimAdapter);
    }

    String time;

    private void initView() {
        mPullToRefreshView = findViewById(R.id.pull_to_refresh);

        System.currentTimeMillis();
        time = getCurrentTime();
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WelcomeActivity.this, time, Toast.LENGTH_LONG).show();
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, REFRESH_DELAY);
            }
        }, type);
    }


    private String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        String currentData = simpleDateFormat.format(date);
        String currentHour = currentData.substring(12, 14);
        Log.e(TAG, currentHour);

        if (Integer.parseInt(currentHour) >= 4 && Integer.parseInt(currentHour) < 6) {
            text = "是什么让你起这么早呢？梦想吗？还是在想谁呢？嘻嘻~";
            type = 0;
        } else if (Integer.parseInt(currentHour) >= 6 && Integer.parseInt(currentHour) < 9) {
            text = "早上好，雅琴";
            type = 0;
        } else if (Integer.parseInt(currentHour) >= 9 && Integer.parseInt(currentHour) < 12) {
            text = "上午好，雅琴";
            type = 0;
        } else if (Integer.parseInt(currentHour) >= 12 && Integer.parseInt(currentHour) < 18) {
            text = "下午好，雅琴";
            type = 1;
        } else if (Integer.parseInt(currentHour) >= 18 && Integer.parseInt(currentHour) < 22) {
            text = "晚上好，雅琴";
            type = 1;
        } else if (Integer.parseInt(currentHour) >= 22 && Integer.parseInt(currentHour) < 24) {
            text = "夜深了，早点休息";
            type = 1;
        } else if (Integer.parseInt(currentHour) >= 0 && Integer.parseInt(currentHour) < 4) {
            text = "夜很深了，记得早点休息";
            type = 1;
        }
//        "现在时间是" + currentData + "\n" +
        return text;
    }
}
