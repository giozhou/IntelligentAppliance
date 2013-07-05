package com.example.intelligentappliance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import android.R.string;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

public class ConnectedInput extends Thread {
	private Tip activity;

	public ConnectedInput() {
//		this.socket = socket;
//		this.activity = activity;
	}

	public void run() {
		Message message = new Message();
		Bundle result = new Bundle();
		try {
			InputStream inStream = TipWork.btSocket.getInputStream();
			byte[] msgIn = new byte[15];
			   
			while(true){
				InputStreamReader inStreamReader = new InputStreamReader(inStream);
				char c = (char)inStreamReader.read();
				String s = Character.toString(c);
				Log.v("diyMessage", s);
//				result.putInt("v", -1);
//				result.putString("content", String.valueOf(s));
//				message.setData(result);
//				activity.myHandler.sendMessage(message);
			}
		} catch (IOException e) {
			result.putString("v", String.valueOf(2));
		}
		message.setData(result);
		try{
			activity.myHandler.sendMessage(message);
		}
		catch(Exception e){
			
		}
	}
}