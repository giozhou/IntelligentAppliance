package com.example.intelligentappliance;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.UUID;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private BluetoothAdapter mBluetoothAdapter;
	private ListView ls;
	private Context c;
	public static BluetoothDevice[] pairedDevices = null;
	private ConnectThread ct = null;
	private int times = 0;
	private ProgressDialog dialog;
	private ArrayList<String> ss = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	public Hashtable<Integer, ConnectedThread> hashConnectedThread = new Hashtable<Integer, ConnectedThread>();
	private String Address;
	private Dialog dialogTip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		Log.v("diyMessage", "apk started");

		ls = (ListView) this.findViewById(R.id.listView1);
		c = this.getApplicationContext();

		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		mBluetoothAdapter.enable();
		BluetoothDevice tmpBD[] = new BluetoothDevice[mBluetoothAdapter
				.getBondedDevices().size()];
		pairedDevices = (BluetoothDevice[]) mBluetoothAdapter
				.getBondedDevices().toArray(tmpBD);

		if (pairedDevices.length > 0) // 如果获取的结果大于0，则开始逐个解析
		{
			int position = 0;
			for (BluetoothDevice device : pairedDevices) {
				Hashtable<String, String> rowData = new Hashtable<String, String>();
				rowData.put("name", device.getName());
				rowData.put("currentStatus", "close");
				rowData.put("pic", "");
				// ss.add(rowData);
				ss.add(device.getName());
				// mBluetoothAdapter.cancelDiscovery();
				// new Thread(new ConnectThread(device, MainActivity.this,
				// position)).start();
				position++;
			}
		}
		adapter = new ArrayAdapter<String>(c,
				android.R.layout.simple_list_item_1, ss);
		Reset();
		ls.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int pos,
					long id) {
				Bundle bundle = new Bundle();
				bundle.putString("name", "aa");
				bundle.putInt("position", pos);
				dialogTip = new Tip(MainActivity.this, bundle);
				dialogTip.show();
			}
		});

		// dialog = ProgressDialog
		// .show(MainActivity.this, "", "正在连接蓝牙设备...", true);
	}

	private void Reset() {
		ls.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
