package com.example.intelligentappliance;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Tip extends Activity {
	private TextView textViewShowMessage;
	private ProgressBar progressBar1;
	private int position;
	private ConnectDeviceTask connectDeviceTask;

	public Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			int status = 0;
			try{
				status = Integer.parseInt(msg.getData().getString("v"));
			}
			catch(Exception e){
				Log.v("diyMessage", e.getMessage());
			}
			connectDeviceTask.cancel(true);
			switch (status) {
			case 0:
				textViewShowMessage.setText("Á¬½ÓÊ§°Ü!");
				progressBar1.setVisibility(View.INVISIBLE);
				break;
			case 1:
				Tip.this.finish();
				Intent intent = new Intent();
				intent.setClass(Tip.this, TipWork.class);
				Tip.this.startActivity(intent);
				new Thread(new ConnectedInput()).start();
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.v("diyMessage", "Create Start 1");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.tip);

		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		position = b.getInt("position");
		textViewShowMessage = (TextView) this.findViewById(R.id.textView2);
		this.progressBar1 = (ProgressBar)this.findViewById(R.id.tip_progressBar);
		new Thread(new ConnectThread(MainActivity.pairedDevices[position], Tip.this, position)).start();
		connectDeviceTask = new ConnectDeviceTask();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			connectDeviceTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
		}
		else{
			connectDeviceTask.execute("a");
		}
		drawProgressBar();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	private void drawProgressBar() {
		final float[] roundedCorners = new float[] { 5, 5, 5, 5, 5, 5, 5, 5 };
		ShapeDrawable pgDrawable = new ShapeDrawable(new RoundRectShape(roundedCorners, null,null));
		String MyColor = "#1CB3FF";
		pgDrawable.getPaint().setColor(Color.parseColor(MyColor));
		ClipDrawable progress = new ClipDrawable(pgDrawable, Gravity.LEFT, ClipDrawable.HORIZONTAL);
		this.progressBar1.setProgressDrawable(progress);
	}

	private class ConnectDeviceTask extends AsyncTask<Object, Integer, String> {

		private int progressWidth = 0;

		@Override
		protected String doInBackground(Object... o) {
			while(true){
				publishProgress(1);
				try{
					Thread.sleep(500);
				}
				catch(Exception e){

				}
			}
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			progressWidth = (this.progressWidth + 20) > 100 ? 0 : this.progressWidth + 20;
			progressBar1.setProgress(progressWidth);
		}

		@Override
		protected void onPostExecute(String result) {

		}
	 }

}
