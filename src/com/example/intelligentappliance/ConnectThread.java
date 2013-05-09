package com.example.intelligentappliance;

import java.io.IOException;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

public class ConnectThread extends Thread {
	private BluetoothSocket cwjSocket;
	private BluetoothDevice cwjDevice;
	private ConnectedThread cdtWrite;
	private DetailActivity activity;
	private int position;

	public ConnectThread(BluetoothDevice device, DetailActivity activityTemp,
			int positionTemp) {
		BluetoothSocket tmp = null;
		cwjDevice = device;
		activity = activityTemp;
		position = positionTemp;

	}

	public void run() {
		Message message = new Message();
		Bundle result = new Bundle();
		try {
			cwjSocket = cwjDevice.createRfcommSocketToServiceRecord(UUID
					.fromString("00001101-0000-1000-8000-00805F9B34FB"));
			try {
				
				cwjSocket.connect();
				result.putString("v", String.valueOf(1));
//				activity.CurrentAddress = cwjDevice.getAddress();
				activity.btSocket = cwjSocket;
			} catch (IOException connectException) {
				Log.v("diyMessage", connectException.getMessage());
				result.putString("v", String.valueOf(0));
			}
		} catch (IOException e) {
			Log.v("diyMessage", "ss");
			result.putString("v", String.valueOf(0));
		}
		message.setData(result);
		activity.myHandler.sendMessage(message);
	}
}