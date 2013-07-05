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
	private LinearLayout layoutWait;
	private TextView textViewShowMessage;
	private String deviceName;
	private ProgressBar progressBar1;
	private int position;
	private LinearLayout tipLayout;

	public Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			int status = 0;
			try{
				status = Integer.parseInt(msg.getData().getString("v"));
			}
			catch(Exception e){
				Log.v("diyMessage", e.getMessage());
			}
			String content = msg.getData().getString("content");
			LayoutParams params = tipLayout.getLayoutParams();
			switch (status) {
			case 0:
				textViewShowMessage.setText("¡¨Ω” ß∞‹!");
				progressBar1.setVisibility(View.INVISIBLE);
				break;
			case 1:
				Tip.this.finish();
				Intent intent = new Intent();
				intent.setClass(Tip.this, TipWork.class);
				Tip.this.startActivity(intent);
				new Thread(new ConnectedInput()).start();
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
		tipLayout = (LinearLayout)this.findViewById(R.id.tip_layout);
		textViewShowMessage = (TextView) this.findViewById(R.id.textView2);
		progressBar1 = (ProgressBar) this.findViewById(R.id.tip_progressBar);
		progressBar1.setProgress(5);
		new Thread(new ConnectThread(MainActivity.pairedDevices[position], Tip.this, position)).start();
	}

}
