package com.example.intelligentappliance;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends Activity {
	private int position;
	private Context c;
	private ProgressDialog dialog1;
	private ProgressDialog dialog2;
	private TextView textView2;
	private TextView textView3;
	private Button btn1;
	private android.widget.Switch switch1;
	public static BluetoothSocket btSocket;
	public static String CurrentAddress;

	public Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			int status = Integer.parseInt(msg.getData().getString("v"));
			switch (status) {
			case 0:
				dialog1.cancel();
				btn1.setVisibility(View.VISIBLE);
				switch1.setVisibility(View.INVISIBLE);
				Toast.makeText(c, "连接失败！ 请检查" + textView3.getText() + "是否打开！",
						Toast.LENGTH_SHORT).show();
				break;
			case 1:
				dialog1.cancel();
				btn1.setVisibility(View.INVISIBLE);
				switch1.setVisibility(View.VISIBLE);
				textView2.setText("已连接");
				break;
			case 2:
				dialog2.cancel();
				Toast.makeText(c, "切换" + textView3.getText() + "失败！",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
				dialog2.cancel();
				Toast.makeText(c, "切换" + textView3.getText() + "成功！",
						Toast.LENGTH_SHORT).show();
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		try {
			// btSocket.close();
		} catch (Exception ex) {
			Log.v("diyMessage", "close error");
		}
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		c = getApplicationContext();
		textView2 = (TextView) this.findViewById(R.id.textView2);
		textView3 = (TextView) this.findViewById(R.id.textView3);
		btn1 = (Button) this.findViewById(R.id.button1);
		switch1 = (android.widget.Switch) this.findViewById(R.id.switch2);
		position = b.getInt("position");

		textView3.setText(b.getString("name"));
		if (!b.getString("address").equals(CurrentAddress)) {
			textView2.setText("未连接");
			btn1.setVisibility(View.VISIBLE);
		} else {
			textView2.setText("已连接");
			btn1.setVisibility(View.INVISIBLE);
			switch1.setVisibility(View.VISIBLE);
		}

		btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog1 = new ProgressDialog(DetailActivity.this);
				dialog1.setMessage("正在连接设备...");
				dialog1.setIndeterminate(true);
				dialog1.setCancelable(true);
				dialog1.show();
				new Thread(new ConnectThread(
						MainActivity.pairedDevices[position],
						DetailActivity.this, position)).start();
			}
		});

		switch1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				dialog2 = new ProgressDialog(DetailActivity.this);
				dialog2.setMessage("正在" + (isChecked ? "打开" : "关闭")
						+ textView3.getText());
				dialog2.setIndeterminate(true);
				dialog2.setCancelable(true);
				dialog2.show();

				String code = isChecked ? "a1#" : "a0#";
				new Thread(new ConnectedThread(btSocket, code.getBytes(),
						DetailActivity.this)).start();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			try {
				btSocket.close();
			} catch (Exception e) {
			}
		}
		super.onKeyDown(keyCode, event);
		return true;
	}
}
