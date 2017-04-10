package com.kyle.popupwindow;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

public class MainActivity extends Activity {
	private PopupWindow popwindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.d("Kyle", "OnbuttonClicekd");
				Intent intent = new Intent();
				intent.setClass(getContext(), BackServcie.class);
				getContext().startService(intent);

//				createFloatingWindow();
			}
		});

	}

	public Context getContext() {
		return this;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void createFloatingWindow() {
		final WindowManager wm = (WindowManager) this
				.getSystemService(WINDOW_SERVICE);
		LayoutInflater li = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		final WindowManager.LayoutParams wmParams = new LayoutParams();
		wmParams.type = LayoutParams.TYPE_PHONE;
		wmParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
				| LayoutParams.FLAG_NOT_FOCUSABLE;
		wmParams.gravity = Gravity.CENTER; // 调整悬浮窗口至中间
		wmParams.x = 0;
		wmParams.y = 0;
		// 设置悬浮窗口长宽数据
		wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		wmParams.format = PixelFormat.RGBA_8888;

		final LinearLayout view = (LinearLayout) li
				.inflate(R.layout.icon, null);
		view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getContext(), "View clicked", Toast.LENGTH_LONG)
						.show();
			}
		});
		
		
		view.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				int action = arg1.getAction();
				float startX = (float) 0.0;
				float startY = (float) 0.0;
				
				switch(action){
				case MotionEvent.ACTION_DOWN:
					Log.d("Kyle","action down x: "+arg1.getX()+" y: "+arg1.getY());
					startX = arg1.getX();
					startY = arg1.getY();
					break;
				case MotionEvent.ACTION_MOVE:
					Log.d("Kyle","action move x: "+arg1.getX()+" y: "+arg1.getY());
					wmParams.x = (int)( arg1.getX()-startX);
					wmParams.y = (int)( arg1.getY()-startY);
					wm.updateViewLayout(view, wmParams);
					break;
				case MotionEvent.ACTION_UP:
					Log.d("Kyle","action up x: "+arg1.getX()+" y: "+arg1.getY());
					wmParams.x = (int)( arg1.getX()-startX);
					wmParams.y = (int)( arg1.getY()-startY);
					wm.updateViewLayout(view, wmParams);
					break;
				}
				return false;
			}
		});
		wm.addView(view, wmParams);

	
	}
}
