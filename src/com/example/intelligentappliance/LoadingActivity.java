package com.example.intelligentappliance;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;

public class LoadingActivity extends Activity {

	private Context loadingContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.loading);

		this.loadingContext = this.getApplicationContext();

		Thread versionManager = new Thread(new VersionManager(
				LoadingActivity.this));
		versionManager.start();
	}

	private void downLoadApk() {
		final ProgressDialog pd; // �������Ի���
		pd = new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("�������ظ���");
		pd.show();
		new Thread() {
			@Override
			public void run() {
				try {
					File file = FileManager
							.getFileFromServer((String) getText(R.string.get_apk_url),
									pd);
					sleep(5000);
					installApk(file);
					pd.dismiss(); // �������������Ի���
				} catch (Exception e) {
				}
			}
		}.start();
	}

	private void installApk(File file) {
		Intent intent = new Intent();
		// ִ�ж���
		intent.setAction(Intent.ACTION_VIEW);
		// ִ�е���������
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}

	protected void showUpdataDialog() {
		AlertDialog.Builder builer = new Builder(this);
		builer.setTitle("�汾����");
		// ����ȷ����ťʱ�ӷ����������� �µ�apk Ȼ��װ
		builer.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				downLoadApk();
			}
		});
		// ����ȡ����ťʱ���е�¼
		builer.setNegativeButton("ȡ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				LoginMain();
			}
		});
		AlertDialog dialog = builer.create();
		dialog.show();
	}

	private void LoginMain() {
		Intent intent = new Intent();
		intent.setClass(LoadingActivity.this, MainActivity.class);
		LoadingActivity.this.startActivity(intent);
	}

	public Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			int status = 0;
			try {
				status = Integer.parseInt(msg.getData().getString("value"));
			} catch (Exception e) {
				Log.v("diyMessage", e.getMessage());
			}
			switch (status) {
			case 0:
				showUpdataDialog();
				break;
			case 1:
				LoginMain();
				break;
			}
			super.handleMessage(msg);
		}
	};
}
