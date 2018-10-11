package ll.to.syy;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import ll.to.syy.R;

import com.yh.DrawYH;
import com.yh.HolderSurfaceView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridLayout;
//import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener,
        OnTouchListener {

    private static final String[] LEVEL = new String[]{"初级", "中级", "高级"};
    private static final int[] BOMBS = new int[]{15, 25, 35};

    public static Drawable[] drawables = null;

    private boolean isStart = false;
    private boolean isMark = false;

    private int bombNumber = 0;
    private int level = 0;

    private boolean[][] bombs = null;
    private boolean[][] marks = null;
    private boolean[][] opens = null;
    private int openNumber = 0;
    private CellImageView[][] civs = null;
    private int[][] ambients = null;

    private int time = 0;
    private Timer timer = null;
    private TimerTask timerTask = null;
    private Handler handler = null;

    private AlertDialog ad = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawables = Utils.getDrawable(MainActivity.this);
        setContentView(R.layout.activity_main);
        bombNumber = BOMBS[level % 3];
        ((TextView) findViewById(R.id.tv_level)).setText(LEVEL[0]);
        ((TextView) findViewById(R.id.tv_bomb)).setText(bombNumber + "");
        ((TextView) findViewById(R.id.tv_time)).setText(0 + "");
        initView();
        initAction();
    }

//	@Override
//	protected void onRestart() {
//		super.onRestart();
//		initView();
//	}

    @SuppressLint({"HandlerLeak", "NewApi"})
    private void initView() {
        openNumber = 0;
        isStart = false;
        CheckBox cb_mark = (CheckBox) findViewById(R.id.cb_mark);
        cb_mark.setChecked(false);
        isMark = false;
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                TextView tv_time = (TextView) MainActivity.this
                        .findViewById(R.id.tv_time);
                tv_time.setText(msg.what + "");
            }
        };
        marks = new boolean[15][10];
        bombs = new boolean[15][10];
        opens = new boolean[15][10];
        ambients = new int[15][10];
        civs = new CellImageView[15][10];
        bombNumber = BOMBS[level % 3];
        ((TextView) findViewById(R.id.tv_bomb)).setText(bombNumber + "");
        initGL();
        closeTimer();
    }

    private void initBombs(int frow, int fcolumn) {
        Random random = new Random();
        int i = 0;
        while (i < bombNumber) {
            int row = random.nextInt(15);
            int column = random.nextInt(10);
            if (!(row > frow - 2 && row < frow + 2 && column > fcolumn - 2 && column < fcolumn + 2)
                    && !bombs[row][column]) {
                i++;
                bombs[row][column] = true;
            }
        }
        initAmbients();
    }

    private void initAmbients() {
        for (int i = 0; i < bombs.length; i++)
            for (int j = 0; j < bombs[i].length; j++)
                if (bombs[i][j]) {
                    if (j - 1 >= 0)
                        if (!bombs[i][j - 1])
                            ambients[i][j - 1]++;
                    if (j + 1 < bombs[i].length)
                        if (!bombs[i][j + 1])
                            ambients[i][j + 1]++;
                    if (i - 1 >= 0) {
                        if (!bombs[i - 1][j])
                            ambients[i - 1][j]++;
                        if (j - 1 >= 0)
                            if (!bombs[i - 1][j - 1])
                                ambients[i - 1][j - 1]++;
                        if (j + 1 < bombs[i].length)
                            if (!bombs[i - 1][j + 1])
                                ambients[i - 1][j + 1]++;
                    }
                    if (i + 1 < bombs.length) {
                        if (!bombs[i + 1][j])
                            ambients[i + 1][j]++;
                        if (j - 1 >= 0)
                            if (!bombs[i + 1][j - 1])
                                ambients[i + 1][j - 1]++;
                        if (j + 1 < bombs[i].length)
                            if (!bombs[i + 1][j + 1])
                                ambients[i + 1][j + 1]++;
                    }
                }
    }

    @SuppressLint({"NewApi", "ClickableViewAccessibility"})
    private void initGL() {
        GridLayout gl = (GridLayout) findViewById(R.id.gl);
        gl.removeAllViews();
        for (int i = 0; i < gl.getRowCount(); i++)
            for (int j = 0; j < gl.getColumnCount(); j++) {
                GridLayout.LayoutParams gllp = new GridLayout.LayoutParams(
                        GridLayout.spec(i), GridLayout.spec(j));
                CellImageView civ = new CellImageView(MainActivity.this, i, j);
                civ.setLayoutParams(gllp);
                civ.setOnClickListener(MainActivity.this);
                civ.setOnTouchListener(MainActivity.this);
                civs[i][j] = civ;
                gl.addView(civ);
            }
    }

    private void openTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {

            @Override
            public void run() {
                Message msg = new Message();
                msg.what = time++;
                handler.sendMessage(msg);
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    private void closeTimer() {
        if (time != 0 || timer != null || timerTask != null) {
            time = 0;
            timer.cancel();
            timerTask.cancel();
            TextView tv_time = (TextView) MainActivity.this
                    .findViewById(R.id.tv_time);
            tv_time.setText(0 + "");
        }
    }

    private void initAction() {
        Button b_back = (Button) findViewById(R.id.b_back);
        b_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//				closeTimer();
//				MainActivity.this.finish();
                Toast.makeText(MainActivity.this, "不是后退键，啥也没有", Toast.LENGTH_SHORT).show();
            }
        });

        final TextView tv_bomb = (TextView) findViewById(R.id.tv_bomb);
        TextView tv_level = (TextView) findViewById(R.id.tv_level);
        tv_level.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                initView();
                ((TextView) v).setText(LEVEL[++level % 3]);
                bombNumber = BOMBS[level % 3];
                tv_bomb.setText(bombNumber + "");
            }
        });

        CheckBox cb_mark = (CheckBox) findViewById(R.id.cb_mark);
        cb_mark.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked)
                    isMark = true;
                else
                    isMark = false;
            }
        });

        Button b_more = (Button) findViewById(R.id.b_more);
        b_more.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "author:  liuli大咖",
                        Toast.LENGTH_LONG).show();
            }
        });

        Button b_restart = (Button) findViewById(R.id.b_restart);
        b_restart.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                initView();
            }
        });
    }

    private void isOver(boolean isWin) {
        String message = null;
        if (isWin) {
            for (int i = 0; i < civs.length; i++)
                for (int j = 0; j < civs[i].length; j++)
                    if (bombs[i][j])
                        civs[i][j].setImageDrawable(12);
        } else {
            if (level % 3 == 0) {
                message = "你的分数为："
                        + (int) (Math.pow(BOMBS[level % 3], 2) / Math.sqrt(time)) + "  蛮厉害的嘛";
//				SystemClock.sleep(1000);
                startActivity(new Intent(MainActivity.this, FriendActivity.class));
            } else if (level % 3 == 1) {
                message = "你的分数为："
                        + (int) (Math.pow(BOMBS[level % 3], 2) / Math.sqrt(time)) + "  果然你就是天选少年";
                startActivity(new Intent(MainActivity.this, FamilyActivity.class));

            } else if (level % 3 == 2) {
                message = "你的分数为："
                        + (int) (Math.pow(BOMBS[level % 3], 2) / Math.sqrt(time)) + "   洋洋得意";
                startActivity(new Intent(MainActivity.this, Love_textActivity.class));
            }
//			for (int i = 0; i < civs.length; i++)
//				for (int j = 0; j < civs[i].length; j++)
//					if (bombs[i][j])
//						civs[i][j].setImageDrawable(14);
//					else if (marks[i][j])
//						civs[i][j].setImageDrawable(15);
//			message = "Game Over!";

        }
        ad = new AlertDialog.Builder(MainActivity.this).create();
        if (!ad.isShowing())
            ad.show();
        Window window = ad.getWindow();
        window.setContentView(R.layout.style_alertdialog);
        Button b_ok = (Button) window.findViewById(R.id.b_ok);
        TextView tv_message = (TextView) window.findViewById(R.id.tv_message);
        tv_message.setText(message);
        b_ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ad.dismiss();
                initView();
            }
        });
    }

    private void openNext(int row, int column) {
        opens[row][column] = true;
        civs[row][column].setImageDrawable(ambients[row][column]);
        openNumber++;
        if (ambients[row][column] == 0)
            openAround(row, column);
        if (openNumber + BOMBS[level % 3] == 150)
            isOver(true);
    }

    private void changeAround(int row, int column, int drawableId) {
        if (column - 1 >= 0)
            if (!marks[row][column - 1] && !opens[row][column - 1])
                civs[row][column - 1].setImageDrawable(drawableId);
        if (column + 1 < civs[row].length)
            if (!marks[row][column + 1] && !opens[row][column + 1])
                civs[row][column + 1].setImageDrawable(drawableId);
        if (row - 1 >= 0) {
            if (!marks[row - 1][column] && !opens[row - 1][column])
                civs[row - 1][column].setImageDrawable(drawableId);
            if (column - 1 >= 0)
                if (!marks[row - 1][column - 1] && !opens[row - 1][column - 1])
                    civs[row - 1][column - 1].setImageDrawable(drawableId);
            if (column + 1 < civs[row].length)
                if (!marks[row - 1][column + 1] && !opens[row - 1][column + 1])
                    civs[row - 1][column + 1].setImageDrawable(drawableId);
        }
        if (row + 1 < civs.length) {
            if (!marks[row + 1][column] && !opens[row + 1][column])
                civs[row + 1][column].setImageDrawable(drawableId);
            if (column - 1 >= 0)
                if (!marks[row + 1][column - 1] && !opens[row + 1][column - 1])
                    civs[row + 1][column - 1].setImageDrawable(drawableId);
            if (column + 1 < civs[row].length)
                if (!marks[row + 1][column + 1] && !opens[row + 1][column + 1])
                    civs[row + 1][column + 1].setImageDrawable(drawableId);
        }
    }

    private void checkDetail(int row, int column, int[] d) {
        if (marks[row][column])
            d[0]++;
        if (marks[row][column] && bombs[row][column])
            d[1]++;
    }

    private boolean checkAround(int row, int column) {
        int[] d = new int[2];
        if (column - 1 >= 0)
            checkDetail(row, column - 1, d);
        if (column + 1 < civs[row].length)
            checkDetail(row, column + 1, d);
        if (row - 1 >= 0) {
            checkDetail(row - 1, column, d);
            if (column - 1 >= 0)
                checkDetail(row - 1, column - 1, d);
            if (column + 1 < civs[row].length)
                checkDetail(row - 1, column + 1, d);
        }
        if (row + 1 < civs.length) {
            checkDetail(row + 1, column, d);
            if (column - 1 >= 0)
                checkDetail(row + 1, column - 1, d);
            if (column + 1 < civs[row].length)
                checkDetail(row + 1, column + 1, d);
        }
        if (d[0] == ambients[row][column])
            if (d[0] == d[1])
                openAround(row, column);
            else
                return true;
        return false;
    }

    @Override
    public void onClick(View v) {
        int row = ((CellImageView) v).getRow();
        int column = ((CellImageView) v).getColumn();

        if (isMark) {
            if (!opens[row][column]) {
                if (marks[row][column]) {
                    marks[row][column] = false;
                    bombNumber++;
                } else {
                    marks[row][column] = true;
                    bombNumber--;
                }
                ((CellImageView) v).setImageDrawable(marks[row][column] ? 10
                        : 11);
                TextView tv_bomb = (TextView) findViewById(R.id.tv_bomb);
                tv_bomb.setText(bombNumber + "");
            }
        } else {
            if (!isStart) {
                initBombs(row, column);
                isStart = true;
                openTimer();
            }
            if (!opens[row][column] && !marks[row][column])
                if (!bombs[row][column])
                    openNext(row, column);
                else
                    isOver(false);
        }
    }

    private void openAround(int row, int column) {
        if (column - 1 >= 0)
            if (!bombs[row][column - 1] && !opens[row][column - 1]
                    && !marks[row][column - 1])
                openNext(row, column - 1);
        if (column + 1 < opens[row].length)
            if (!bombs[row][column + 1] && !opens[row][column + 1]
                    && !marks[row][column + 1])
                openNext(row, column + 1);
        if (row - 1 >= 0) {
            if (!bombs[row - 1][column] && !opens[row - 1][column]
                    && !marks[row - 1][column])
                openNext(row - 1, column);
            if (column - 1 >= 0)
                if (!bombs[row - 1][column - 1] && !opens[row - 1][column - 1]
                        && !marks[row - 1][column - 1])
                    openNext(row - 1, column - 1);
            if (column + 1 < opens[row].length)
                if (!bombs[row - 1][column + 1] && !opens[row - 1][column + 1]
                        && !marks[row - 1][column + 1])
                    openNext(row - 1, column + 1);
        }
        if (row + 1 < opens.length) {
            if (!bombs[row + 1][column] && !opens[row + 1][column]
                    && !marks[row + 1][column])
                openNext(row + 1, column);
            if (column - 1 >= 0)
                if (!bombs[row + 1][column - 1] && !opens[row + 1][column - 1]
                        && !marks[row + 1][column - 1])
                    openNext(row + 1, column - 1);
            if (column + 1 < opens[row].length)
                if (!bombs[row + 1][column + 1] && !opens[row + 1][column + 1]
                        && !marks[row + 1][column + 1])
                    openNext(row + 1, column + 1);
        }
    }

    @SuppressLint({"ClickableViewAccessibility", "NewApi"})
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int with = v.getWidth();
        int heght = v.getHeight();
        int row = ((CellImageView) v).getRow();
        int column = ((CellImageView) v).getColumn();
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        if (!marks[row][column])
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (opens[row][column])
                        changeAround(row, column, 9);
                    else
                        civs[row][column].setImageDrawable(13);
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (x < location[0] || x > location[0] + with
                            || y < location[1] || y > location[1] + heght) {
                        changeAround(row, column, 11);
                        if (!opens[row][column])
                            civs[row][column].setImageDrawable(11);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (!(x < location[0] || x > location[0] + with
                            || y < location[1] || y > location[1] + heght))
                        if (opens[row][column]) {
                            if (checkAround(row, column)) {
                                changeAround(row, column, 11);
                                isOver(false);
                                return true;
                            }
                        } else
                            civs[row][column].setImageDrawable(11);
                    else if (!opens[row][column])
                        civs[row][column].setImageDrawable(11);
                    changeAround(row, column, 11);
                    break;
            }
        return false;
    }

}
