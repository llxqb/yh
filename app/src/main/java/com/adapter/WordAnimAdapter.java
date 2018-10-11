package com.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

import ll.to.syy.MainActivity;
import ll.to.syy.R;

/**
 * Created by li.liu on 2018/10/11.
 */

public class WordAnimAdapter extends RecyclerView.Adapter<WordAnimAdapter.MyHolder> {

    private List<Character> mList = new ArrayList<>();
    private String[] JsonList = new String[]{"Mobilo/A.json", "Mobilo/B.json", "Mobilo/C.json", "Mobilo/D.json",
            "Mobilo/E.json", "Mobilo/F.json", "Mobilo/G.json", "Mobilo/F.json", "Mobilo/I.json", "Mobilo/J.json", "Mobilo/K.json",
            "Mobilo/L.json", "Mobilo/M.json", "Mobilo/N.json", "Mobilo/O.json", "Mobilo/P.json", "Mobilo/Q.json", "Mobilo/R.json",
            "Mobilo/S.json", "Mobilo/T.json", "Mobilo/U.json", "Mobilo/V.json", "Mobilo/W.json", "Mobilo/X.json", "Mobilo/Y.json", "Mobilo/Z.json"};

    private List<Integer> resourceList = new ArrayList<>();
    private Context mContext;

    public WordAnimAdapter(List<Character> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item, parent, false);

        for (int i = 0; i < mList.size(); i++) {
            if (i == 0) {
                resourceList.add(R.drawable.a);
            } else if (i == 1) {
                resourceList.add(R.drawable.b);
            } else if (i == 2) {
                resourceList.add(R.drawable.c);
            } else if (i == 3) {
                resourceList.add(R.drawable.d);
            } else if (i == 4) {
                resourceList.add(R.drawable.e);
            } else if (i == 5) {
                resourceList.add(R.drawable.f);
            } else if (i == 6) {
                resourceList.add(R.drawable.g);
            } else if (i == 7) {
                resourceList.add(R.drawable.h);
            } else if (i == 8) {
                resourceList.add(R.drawable.i);
            } else if (i == 9) {
                resourceList.add(R.drawable.j);
            } else if (i == 10) {
                resourceList.add(R.drawable.k);
            } else if (i == 11) {
                resourceList.add(R.drawable.l);
            } else if (i == 12) {
                resourceList.add(R.drawable.m);
            } else if (i == 13) {
                resourceList.add(R.drawable.n);
            } else if (i == 14) {
                resourceList.add(R.drawable.o);
            } else if (i == 15) {
                resourceList.add(R.drawable.p);
            } else if (i == 16) {
                resourceList.add(R.drawable.q);
            } else if (i == 17) {
                resourceList.add(R.drawable.r);
            } else if (i == 18) {
                resourceList.add(R.drawable.s);
            } else if (i == 19) {
                resourceList.add(R.drawable.t);
            } else if (i == 20) {
                resourceList.add(R.drawable.u);
            } else if (i == 21) {
                resourceList.add(R.drawable.v);
            } else if (i == 22) {
                resourceList.add(R.drawable.w);
            } else if (i == 23) {
                resourceList.add(R.drawable.x);
            } else if (i == 24) {
                resourceList.add(R.drawable.y);
            } else if (i == 25) {
                resourceList.add(R.drawable.z);
            }
        }
        return new MyHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
        holder.mLottieAnimationView.setImageResource(resourceList.get(position));
        holder.mAnimationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加播放源
                holder.mLottieAnimationView.setAnimation(JsonList[position]);
                //是否循环播放
                holder.mLottieAnimationView.loop(false);
                //开始播放
                holder.mLottieAnimationView.playAnimation();

                if (position == 0) {
                    holder.mLottieAnimationView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            MainActivity.start(mContext);
                            ((Activity)mContext).finish();
                        }
                    }, 1400);
                }
            }
        });
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        LottieAnimationView mLottieAnimationView;
        LinearLayout mAnimationLayout;

        public MyHolder(View itemView) {
            super(itemView);
            mAnimationLayout = itemView.findViewById(R.id.animation_layout);
            mLottieAnimationView = itemView.findViewById(R.id.animation_view);
        }
    }
}
