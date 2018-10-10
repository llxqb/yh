package com.yh;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class DrawText extends DrawTopBase {
	@Override
	protected void doWork(Canvas canvas) {
		super.doWork(canvas);
//		mPaint.setColor(0xffff0000);

		mPaint.setColor(0xffff0000);
		mPaint.setTextSize(20);
		canvas.drawText("啦啦啦啦啦啦啦", 0, textH, mPaint);
		 
		if(textb!=null)
		{
			canvas.drawBitmap(textb, 100, 100, mPaint);
		}
	}

	// ���ֵ�
	Bitmap textb;
	// ���ֻ���
	Canvas textc;
	// ����ͼƬ
	int[] textints;
	// ��·����
	int[] bints;
	// �ֿ�
	int textW;
	// �ָ�
	int textH;
	Rect textR;
	@Override
	public void set() {
		super.set();
		textR = new Rect();
		Paint paint=new Paint();
		paint.setTextSize(20);
		
		String s = "����һ������";
		paint.getTextBounds(s, 0, s.length(), textR);
		textW=textR.width();
		textH=textR.height();
		textb=Bitmap.createBitmap(textW, textH, Bitmap.Config.ARGB_8888);
		textc = new Canvas();
		textc.setBitmap(textb);
		
		paint.setColor(0xffffffff);
		textc.drawRGB(255, 0, 0);
		mPaint.setColor(0xff00ff00);
		textc.drawText(s, 0,textH-2, paint);
		 
	}
}
