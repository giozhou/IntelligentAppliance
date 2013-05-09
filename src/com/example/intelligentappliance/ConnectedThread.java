package com.example.intelligentappliance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

public class ConnectedThread extends Thread {
	private BluetoothSocket socket;
	private byte[] bytes;
	private Tip activity;

	public ConnectedThread(BluetoothSocket socket, byte[] bytes,
			Tip activity) {
		this.socket = socket;
		this.bytes = bytes;
		this.activity = activity;
	}

	public void run() {
		Message message = new Message();
		Bundle result = new Bundle();
		try {
			OutputStream outStream = socket.getOutputStream();
			try {
				outStream.write(bytes);
				Log.v("diyMessage", "writed");
				result.putString("v", String.valueOf(3));
			} catch (IOException e) {
				result.putString("v", String.valueOf(2));
				Log.v("diyMessage", "writting is error");
			}
		} catch (IOException e) {
			result.putString("v", String.valueOf(2));
		}
		message.setData(result);
		activity.myHandler.sendMessage(message);
	}
}