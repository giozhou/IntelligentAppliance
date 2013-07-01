package com.example.intelligentappliance;

import java.io.IOException;

import com.example.intelligentappliance.ConnectedThread;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Tip extends Activity {
	private LinearLayout layoutWork;
	private LinearLayout layoutWait;
	private TextView textViewShowMessage;
	private String deviceName;
	private ProgressBar progressBar1;
	private int position;
	public static BluetoothSocket btSocket;
	private Switch switch1;
	private Switch switch2;
	private LinearLayout tipLayout;

	public Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			int status = Integer.parseInt(msg.getData().getString("v"));
			String content = msg.getData().getString("content");
			LayoutParams params = tipLayout.getLayoutParams();
			switch (status) {
			case 0:
				textViewShowMessage.setText("连接失败!");
				progressBar1.setVisibility(View.INVISIBLE);
				params.height = 140;
				break;
			case 1:
				Tip.this.finish();

				Intent intent = new Intent();
				intent.setClass(Tip.this, TipWork.class);
				Tip.this.startActivity(intent);
				break;
			case 2:
				layoutWait.setVisibility(View.INVISIBLE);
				break;
			case 3:
				layoutWait.setVisibility(View.INVISIBLE);
				break;
			case -1:
				Log.v("diyMessage", content);
				if(content == "01 00"){
					
				}
				else if(content == ""){
					
				}
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.tip);

		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		deviceName = b.getString("name");
		position = b.getInt("position");

		layoutWait = (LinearLayout) this.findViewById(R.id.layoutWait);
		layoutWork = (LinearLayout) this.findViewById(R.id.layoutWork);
		tipLayout = (LinearLayout)this.findViewById(R.id.tip_layout);
		switch1 = (Switch) this.findViewById(R.id.switch1);
		switch2 = (Switch) this.findViewById(R.id.switch2);
		textViewShowMessage = (TextView) this.findViewById(R.id.textView2);
		progressBar1 = (ProgressBar) this.findViewById(R.id.progressBar1);

		layoutWork.setVisibility(View.INVISIBLE);

		new Thread(new ConnectThread(MainActivity.pairedDevices[position],
				Tip.this, position)).start();

		switch1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				layoutWait.setVisibility(View.VISIBLE);
				textViewShowMessage.setText("正在切换……");

				String code = "AA 02";
				new Thread(new ConnectedThread(btSocket, code.getBytes(),
						Tip.this)).start();
			}
		});
		
		switch2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				layoutWait.setVisibility(View.VISIBLE);
				textViewShowMessage.setText("正在切换……");

				String code = "AA 02";
				new Thread(new ConnectedThread(btSocket, code.getBytes(),
						Tip.this)).start();
			}
		});
	}

	@Override
	protected void onStop() {
		try {
			if (btSocket != null) {
				btSocket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.onStop();
	}
}
