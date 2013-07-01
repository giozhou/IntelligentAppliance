package com.example.intelligentappliance;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class TipWork extends Activity {
	private Button btn1;
	private Button btn2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.tip_work);
		
		btn1 = (Button)this.findViewById(R.id.button1);
		btn2 = (Button)this.findViewById(R.id.button2);
		
		btn2.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Toast.makeText(TipWork.this, "adsf", Toast.LENGTH_LONG).show();
				
			}
		});
	}
}
