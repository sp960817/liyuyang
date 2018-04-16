package com.android.activity;

import java.io.IOException;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import com.android.common.HttpUtil;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MusicPlayerActivity extends FinalActivity {

	@ViewInject(id = R.id.returnimg, click = "returnClick")
	private ImageView returnimg;

	private ImageButton mb1, mb2, mb3;

	private TextView tv;

	private MediaPlayer mp;

	// 声明一个变量判断是否为暂停,默认为false
	private boolean isPaused = false;

	String filepath;

	String error = "";

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_music);

		Intent intent = getIntent();

		filepath = intent.getStringExtra("filepath");

		// 通过findViewById找到资源
		mb1 = (ImageButton) findViewById(R.id.myButton1);

		mb2 = (ImageButton) findViewById(R.id.myButton2);

		mb3 = (ImageButton) findViewById(R.id.myButton3);

		tv = (TextView) findViewById(R.id.title);

		try {

			String temp = HttpUtil.FILEPATH_URL + filepath;

			mp = MediaPlayer.create(MusicPlayerActivity.this, Uri.parse(temp));// 实例化对象，通过播放本机服务器上的一首音乐

			mp.start();

		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e2) {

			Toast.makeText(MusicPlayerActivity.this, "播放失败，请检查音频是否存在!",
					Toast.LENGTH_LONG).show();
			error = "播放失败，请检查音频是否存在!";
			
		
		}
		if (error.length() > 0) {
		
			finish();
		} else {
			// 增加播放音乐按钮的事件
			mb1.setOnClickListener(new ImageButton.OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						if (mp != null) {
							mp.stop();
						}
						mp.prepare();
						mp.start();
						tv.setText("发声训练播放中...");
					} catch (Exception e) {
						tv.setText("播放发声异常...");
						e.printStackTrace();
					}
				}
			});

			mb2.setOnClickListener(new ImageButton.OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						if (mp != null) {
							mp.stop();
							tv.setText("发声训练停止播放...");
						}
					} catch (Exception e) {
						tv.setText("发声训练停止发声异常...");
						e.printStackTrace();
					}

				}
			});

			mb3.setOnClickListener(new ImageButton.OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						if (mp != null) {
							if (isPaused == false) {
								mp.pause();
								isPaused = true;
								tv.setText("停止播放!");
							} else if (isPaused == true) {
								mp.start();
								isPaused = false;
								tv.setText("开始播发!");
							}
						}
					} catch (Exception e) {
						tv.setText("发声异常...");
						e.printStackTrace();
					}

				}
			});

			/* 当MediaPlayer.OnCompletionLister会运行的Listener */
			mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				// @Override
				/* 覆盖文件播出完毕事件 */
				public void onCompletion(MediaPlayer arg0) {
					try {
						/*
						 * 解除资源与MediaPlayer的赋值关系 让资源可以为其它程序利用
						 */
						mp.release();
						/* 改变TextView为播放结束 */
						tv.setText("发声训练播放结束!");
					} catch (Exception e) {
						tv.setText(e.toString());
						e.printStackTrace();
					}
				}
			});

			/* 当MediaPlayer.OnErrorListener会运行的Listener */
			mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
				@Override
				/* 覆盖错误处理事件 */
				public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
					// TODO Auto-generated method stub
					try {
						/* 发声错误时也解除资源与MediaPlayer的赋值 */
						mp.release();
						tv.setText("播放发声异常!");
					} catch (Exception e) {
						tv.setText(e.toString());
						e.printStackTrace();
					}
					return false;
				}
			});
		}
	}
 

	public void returnClick(View v) {

		mp.release();
		
		MusicPlayerActivity.this.finish();
	}

}
