package com.example.intelligentappliance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends ListActivity  {

	private BluetoothAdapter mBluetoothAdapter;
	private ListView ls;
	private Context c;
	public static BluetoothDevice[] pairedDevices = null;
	private ConnectThread ct = null;
	private int times = 0;
	private ProgressDialog dialog;
	private List<Map<String, Object>> ss;
	private SimpleAdapter adapter;
	public Hashtable<Integer, ConnectedThread> hashConnectedThread = new Hashtable<Integer, ConnectedThread>();
	private String Address;
	private Dialog dialogTip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		Log.v("diyMessage", "apk started");

		ls = (ListView) this.findViewById(android.R.id.list);
		c = this.getApplicationContext();
		ss =  new ArrayList<Map<String, Object>>();
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
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("name", device.getName());
				map.put("currentStatus", "close");
				map.put("pic", "");
				ss.add(map);
				position++;
			}
		}
		adapter = new SimpleAdapter(this, ss, R.layout.main_list,
			            new String[]{"name"},
			            new int[]{R.id.list_content});
		Reset();

	}
	
	@Override
    /**
     * When the user selects an item in the list, do an action
     * @param ListView l
     * @param View v
     * @param int position
     * @param long id
     */ 
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
		Bundle bundle = new Bundle();
		bundle.putString("name", "aa");
		bundle.putInt("position", position);

		Intent intent = new Intent();
		intent.putExtras(bundle);
		intent.setClass(MainActivity.this, Tip.class);
		MainActivity.this.startActivity(intent);
    }

	private void Reset() {
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
