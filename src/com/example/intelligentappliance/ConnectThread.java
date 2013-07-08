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
	private BluetoothDevice cwjDevice;
	private Tip activity;

	public ConnectThread(BluetoothDevice device, Tip activityTemp,
			int positionTemp) {
		cwjDevice = device;
		activity = activityTemp;
	}

	public void run() {
		Message message = new Message();
		Bundle result = new Bundle();
		try {
			TipWork.btSocket = cwjDevice.createRfcommSocketToServiceRecord(UUID
					.fromString("00001101-0000-1000-8000-00805F9B34FB"));
			try {
				TipWork.btSocket.connect();
				result.putString("v", String.valueOf(1));
			} catch (IOException connectException) {
				result.putString("v", String.valueOf(0));
			}
		} catch (IOException e) {
			result.putString("v", String.valueOf(0));
		}
		message.setData(result);
		activity.myHandler.sendMessage(message);
	}
}