package com.kyle.popupwindow;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class BackServcie extends Service {
	int coordinate[] = new int[2];
	float startX = (float) 0.0;
	float startY = (float) 0.0;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Context getContext() {
		return this;
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
		wmParams.gravity = Gravity.NO_GRAVITY; // 调整悬浮窗口至中间
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
				
				switch (action) {
				case MotionEvent.ACTION_DOWN:
					Log.d("Kyle", "action down or x: " + arg1.getRawX() + " y: "
							+ arg1.getRawY());
					Log.d("Kyle", "action down x: " + (arg1.getRawX()+ coordinate[0]) + " y: "
							+ (arg1.getRawY()+ coordinate[1]));
					view.getLocationOnScreen(coordinate);
					startX = (arg1.getRawX() - wmParams.x );
					startY = (arg1.getRawY() - wmParams.y );
					break;
				case MotionEvent.ACTION_MOVE:
					Log.d("Kyle", "action move x: " + arg1.getRawX() + " y: "
							+ arg1.getRawY());
					wmParams.x = (int) (arg1.getRawX() - startX);
					wmParams.y = (int) (arg1.getRawY() - startY);
					wm.updateViewLayout(view, wmParams);
					Log.d("Kyle",
							"action up wmParams.x: " + wmParams.x + " wmParams.y: "
									+ wmParams.y);
					break;
				case MotionEvent.ACTION_UP:
					Log.d("Kyle",
							"action up x: " + arg1.getRawX() + " y: "
									+ arg1.getRawY());
					wmParams.x = (int) (arg1.getRawX() - startX);
					wmParams.y = (int) (arg1.getRawY() - startY);
					Log.d("Kyle",
							"action up wmParams.x: " + wmParams.x + " wmParams.y: "
									+ wmParams.y);
					wm.updateViewLayout(view, wmParams);
					break;
				}
				return false;
			}
		});
		wm.addView(view, wmParams);
	}

	public void onCreate() {
		super.onCreate();
		Log.d("Kyle", "backservice created");
		createFloatingWindow();
	}

	public void onStart(Intent intent, int startId) {
		Log.d("Kyle", "backservice started");
	}
}
