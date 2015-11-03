package com.example.demo3c;




import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.SurfaceHolder.Callback;
import android.view.View.OnTouchListener;

public class MyPaintingBoard {
	SurfaceView surfaceView;
	SurfaceHolder holder;
	Paint paint;
	Bitmap background;
	Fragment fragment;
	Box[] boxs;
	Bitmap[] backs;
	int nb=4;
	
	boolean setSurfaceView(SurfaceView surfaceView){
		System.out.println("check4");
		if (surfaceView==null)return false;
		System.out.println("check5");
		this.surfaceView=surfaceView;
		holder=surfaceView.getHolder();
		holder.addCallback(new Callback()
		{
			@Override
			public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2,
					int arg3)
			{
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder)
			{
				System.out.println("check1");
				// 锁定整个SurfaceView
				Canvas canvas = holder.lockCanvas();
				// 绘制背景
				canvas.drawBitmap(backs[0], 0, 0, null);
				// 绘制完成，释放画布，提交修改
				holder.unlockCanvasAndPost(canvas);
              
			}
			@Override
			public void surfaceDestroyed(SurfaceHolder holder)
			{
			}
		});
		// 为surface的触摸事件绑定监听器
				surfaceView.setOnTouchListener(new OnTouchListener()
				{
					@Override
					public boolean onTouch(View source, MotionEvent event)
					{
						if (event.getAction() == MotionEvent.ACTION_DOWN ||event.getAction() == MotionEvent.ACTION_MOVE  )
						{
							int cx = (int) event.getX();
							int cy = (int) event.getY();
							System.out.println(cx+" "+cy);
							boolean flag=false;
							for (int i=0;i<nb;++i){
								if (boxs[i].inBox(cx, cy))
								{
									// 锁定整个SurfaceView
									Canvas canvas = holder.lockCanvas();
									// 绘制背景
									canvas.drawBitmap(backs[i+1], 0, 0, null);
									// 绘制完成，释放画布，提交修改
									holder.unlockCanvasAndPost(canvas);
									flag=true;
								}
							}
							if (!flag)
							{
								// 锁定整个SurfaceView
								Canvas canvas = holder.lockCanvas();
								
								// 绘制背景
								canvas.drawBitmap(backs[0], 0, 0, null);
								// 绘制完成，释放画布，提交修改
								holder.unlockCanvasAndPost(canvas);
							}
						}
						
						else if (event.getAction() == MotionEvent.ACTION_UP)
						{
							
							System.out.println("up");
							// 锁定整个SurfaceView
							Canvas canvas = holder.lockCanvas();
							
							// 绘制背景
							canvas.drawBitmap(backs[0], 0, 0, null);
							// 绘制完成，释放画布，提交修改
							holder.unlockCanvasAndPost(canvas);
			              
						}
						return  true;
					}
				});
		return true;
	}
	

	
	
	void intitial(){
		
		
	}
	
	MyPaintingBoard(SurfaceView sur,Bitmap back,Fragment frag){
		System.out.println("check3");
		background=back;
		fragment=frag;
		setSurfaceView(sur);
		paint=new Paint();
		
		boxs=new Box[4];
		boxs[0]=new Box(460,700,620,860);
		boxs[1]=new Box(700,590,845,770);
		boxs[2]=new Box(890,700,1050,865);
		boxs[3]=new Box(690,920,880,1160);
		
		backs=new Bitmap[5];
		// 绘制背景
		backs[0] = BitmapFactory.decodeResource(
			fragment.getResources()
			, R.drawable.button0);
		backs[1] = BitmapFactory.decodeResource(
				fragment.getResources()
				, R.drawable.button1);
		backs[2] = BitmapFactory.decodeResource(
				fragment.getResources()
				, R.drawable.button2);
		backs[3] = BitmapFactory.decodeResource(
				fragment.getResources()
				, R.drawable.button3);
		backs[4] = BitmapFactory.decodeResource(
				fragment.getResources()
				, R.drawable.button4);
	}
	
	
}

class Box
{
	int x1;
	int x2;
	int y1;
	int y2;
	Box(int a1,int b1,int a2,int b2){
		x1=a1;x2=a2;y1=b1;y2=b2;
	}
	boolean inBox(int x,int y){
		if (x1<x && x<x2 && y1<y && y<y2)return true;
		return false;
	}
}
