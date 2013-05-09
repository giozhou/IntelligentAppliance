package com.example.intelligentappliance;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class Tip extends Dialog {

	public Tip(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public Tip(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.tip);
    }

}
