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
		final ProgressDialog pd; // 进度条对话框
		pd = new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("正在下载更新");
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
					pd.dismiss(); // 结束掉进度条对话框
				} catch (Exception e) {
				}
			}
		}.start();
	}

	private void installApk(File file) {
		Intent intent = new Intent();
		// 执行动作
		intent.setAction(Intent.ACTION_VIEW);
		// 执行的数据类型
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}

	protected void showUpdataDialog() {
		AlertDialog.Builder builer = new Builder(this);
		builer.setTitle("版本升级");
		// 当点确定按钮时从服务器上下载 新的apk 然后安装
		builer.setPositiveButton("确定", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				downLoadApk();
			}
		});
		// 当点取消按钮时进行登录
		builer.setNegativeButton("取消", new OnClickListener() {
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
