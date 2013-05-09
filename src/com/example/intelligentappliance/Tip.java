package com.example.intelligentappliance;

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
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Tip extends Dialog {
	private LinearLayout layoutWork;
	private LinearLayout layoutWait;
	private String deviceName;
	private int position;
	public static BluetoothSocket btSocket;
	private Switch switch1;
	
	public Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			int status = Integer.parseInt(msg.getData().getString("v"));
			switch (status) {
			case 0:
				layoutWork.setVisibility(View.VISIBLE);
				layoutWait.setVisibility(View.INVISIBLE);
				break;
			case 1:
				layoutWork.setVisibility(View.VISIBLE);
				layoutWait.setVisibility(View.INVISIBLE);
				break;
			case 2:
				break;
			case 3:
				break;
			}
			super.handleMessage(msg);
		}
	};

	public Tip(Context context, Bundle bundle) {
		super(context);
		deviceName = bundle.getString("name");
		position = bundle.getInt("position");
		// TODO Auto-generated constructor stub
	}

	public Tip(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.tip);

		layoutWait = (LinearLayout) this
				.findViewById(R.id.layoutWait);
		layoutWork = (LinearLayout) this
				.findViewById(R.id.layoutWork);
		Switch switch1 = (Switch)this.findViewById(R.id.switch1);
		
		layoutWork.setVisibility(View.INVISIBLE);

		new Thread(new ConnectThread(MainActivity.pairedDevices[position],
				Tip.this, position)).start();
		
		switch1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				layoutWait.setVisibility(View.VISIBLE);

				String code = isChecked ? "a1#" : "a0#";
				new Thread(new ConnectedThread(btSocket, code.getBytes(),
						Tip.this)).start();
			}
		});
	}
	
	

}
