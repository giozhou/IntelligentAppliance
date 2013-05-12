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
import android.view.MotionEvent;
import android.view.View;
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

	public Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			int status = Integer.parseInt(msg.getData().getString("v"));
			switch (status) {
			case 0:
				textViewShowMessage.setText("连接失败!");
				progressBar1.setVisibility(View.INVISIBLE);
				break;
			case 1:
				layoutWork.setVisibility(View.VISIBLE);
				layoutWait.setVisibility(View.INVISIBLE);
				break;
			case 2:
				layoutWait.setVisibility(View.INVISIBLE);
				break;
			case 3:
				layoutWait.setVisibility(View.INVISIBLE);
				break;
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
		switch1 = (Switch) this.findViewById(R.id.switch1);
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

				String code = isChecked ? "a1#" : "a0#";
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
