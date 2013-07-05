package com.example.intelligentappliance;

import java.io.IOException;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TipWork extends Activity {
	private Button btn1;
	private Button btn2;
	private Boolean btn1Bool = false;
	private Boolean btn2Bool = false;
	private TextView tip_work_title;
	public static BluetoothSocket btSocket;
	
	public Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			int status = 0;
			try{
				status = Integer.parseInt(msg.getData().getString("v"));
			}
			catch(Exception e){
				Log.v("diyMessage", e.getMessage());
			}

			Log.v("diyMessage", String.valueOf(status));
			switch (status) {
			case 1:
				tip_work_title.setText("请选择操作");
				break;
			case 2:
				tip_work_title.setText("请选择操作");
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
		this.setContentView(R.layout.tip_work);
		
		tip_work_title = (TextView)this.findViewById(R.id.tip_work_title);
		btn1 = (Button)this.findViewById(R.id.button1);
		btn2 = (Button)this.findViewById(R.id.button2);

		btn1.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				String code = "AA01";
				Log.v("diyMessage", btn2.getBackground().toString());
				new Thread(new ConnectedThread(btSocket, Converter.hexStringToByteArray(code), TipWork.this)).start();
				if (btn1Bool == true){
					btn1Bool = false;
					btn1.setBackgroundResource(R.drawable.btn_open);
					btn1.setText("打 开");
				}
				else{
					btn1Bool = true;
					btn1.setBackgroundResource(R.drawable.btn_close);
					btn1.setText("关 闭");
				}
			}
		});
		
		btn2.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				String code = "AA02";
				Log.v("diyMessage", btn2.getBackground().toString());
				new Thread(new ConnectedThread(btSocket, Converter.hexStringToByteArray(code), TipWork.this)).start();
				if (btn2Bool == true){
					btn2Bool = false;
					btn2.setBackgroundResource(R.drawable.btn_open);
					btn2.setText("打 开");
				}
				else{
					btn2Bool = true;
					btn2.setBackgroundResource(R.drawable.btn_close);
					btn2.setText("关 闭");
				}
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
