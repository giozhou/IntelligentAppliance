package com.example.intelligentappliance;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

public class VersionManager extends Thread {
	Context context;
	LoadingActivity loadingActivity;
	
	public VersionManager(LoadingActivity loadingActivity){
		this.context = loadingActivity.getApplicationContext();
		this.loadingActivity = loadingActivity;
	}
	
	public void run(){
		int verCode = -1;
        try {
			verCode = this.context.getPackageManager().getPackageInfo(
			        "com.example.intelligentappliance", 0).versionCode;
			URL myURL;
			try {
				myURL = new URL((String) this.context.getText(R.string.get_version_url));
	            URLConnection ucon = myURL.openConnection();
	            InputStream is = ucon.getInputStream();
	            BufferedInputStream bis = new BufferedInputStream(is);
	            ByteArrayBuffer baf = new ByteArrayBuffer(50);
	            int current = 0;
	            while ((current = bis.read()) != -1) {
	                baf.append((byte)current);
	            }
	            String versionCodeCurrent = new String(baf.toByteArray());
	    		Message message = new Message();
	    		Bundle result = new Bundle();
	            if (Integer.parseInt(versionCodeCurrent.trim()) > verCode){
					result.putString("value", String.valueOf(0));
	            }
	            else{
					result.putString("value", String.valueOf(1));
	            }
	    		message.setData(result);
	    		this.loadingActivity.myHandler.sendMessage(message);
			} catch (Exception e) {
				Log.v("diyMessage", e.getMessage());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.v("diyMessage", "ee");
		}  
	}
	
	private static String convertStreamToString(InputStream is) {
	    /*
	     * To convert the InputStream to String we use the BufferedReader.readLine()
	     * method. We iterate until the BufferedReader return null which means
	     * there's no more data to read. Each line will appended to a StringBuilder
	     * and returned as String.
	     */
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}
}
