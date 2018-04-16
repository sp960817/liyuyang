package com.android.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.activity.R;
import com.android.domain.User;
import com.android.fragment.SousuoFragment;
import com.android.fragment.UserInfoFragment;
import com.android.fragment.WodegushiAddFragment;
import com.android.fragment.WodegushiListFragment;
import com.android.fragment.YijianfankuiAddFragment;
import com.android.fragment.YijianfankuiListFragment;
import com.android.fragment.YouergushiFragment;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuInfo;
import com.special.ResideMenu.ResideMenuItem;

public class MainActivity extends FragmentActivity implements
		View.OnClickListener {

	private Fragment mContent;

	public ResideMenu resideMenu;

	private ResideMenuItem itemYOUERGUSHI;

	private ResideMenuItem itemSOUSUOGUSHI;
	
	private ResideMenuItem itemTIANJIAYIJIAN;

	private ResideMenuItem itemYIJIANFANKUI;

	private ResideMenuItem itemWODEGUSHI;

	private ResideMenuItem itemSHANGCHUANGUSHI;

	private ResideMenuItem itemGERENXINXI;

	private ResideMenuInfo info;

	private boolean is_closed = false;

	private long mExitTime;

	private TextView maintitle;

	MyApplication myApp;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		maintitle = (TextView) findViewById(R.id.main_txt);

		myApp = (MyApplication) getApplication();

		setUpMenu();
		// changeFragment(new HuDongFragment());
		setListener();

		initView();// 初始化页面信息
	}

	private void setUpMenu() {

		// attach to current activity;
		resideMenu = new ResideMenu(this);

		resideMenu.setBackground(R.drawable.menubg);

		resideMenu.attachToActivity(this);

		resideMenu.setMenuListener(menuListener);

		resideMenu.setScaleValue(0.6f);
		// 禁止使用右侧菜单
		resideMenu.setDirectionDisable(ResideMenu.DIRECTION_RIGHT);

		// create menu items;
		itemYOUERGUSHI = new ResideMenuItem(this, "幼儿发声训练");

		itemSOUSUOGUSHI = new ResideMenuItem(this, "搜索发声训练");
		
		itemTIANJIAYIJIAN = new ResideMenuItem(this, "添加意见");

		itemYIJIANFANKUI = new ResideMenuItem(this, "意见反馈");

		itemWODEGUSHI = new ResideMenuItem(this, "我的发声训练");

		itemSHANGCHUANGUSHI = new ResideMenuItem(this, "上传发声训练");

		itemGERENXINXI = new ResideMenuItem(this, "个人信息");

		resideMenu.addMenuItem(itemYOUERGUSHI, ResideMenu.DIRECTION_LEFT);

		resideMenu.addMenuItem(itemSOUSUOGUSHI, ResideMenu.DIRECTION_LEFT);
		
		resideMenu.addMenuItem(itemTIANJIAYIJIAN, ResideMenu.DIRECTION_LEFT);

		resideMenu.addMenuItem(itemYIJIANFANKUI, ResideMenu.DIRECTION_LEFT);

		resideMenu.addMenuItem(itemWODEGUSHI, ResideMenu.DIRECTION_LEFT);

		resideMenu.addMenuItem(itemSHANGCHUANGUSHI, ResideMenu.DIRECTION_LEFT);

		resideMenu.addMenuItem(itemGERENXINXI, ResideMenu.DIRECTION_LEFT);

		User user = myApp.getUser();

		info = new ResideMenuInfo(this, R.drawable.logo, user.getUsername());

	}

	private void setListener() {

		resideMenu.addMenuInfo(info);

		itemYOUERGUSHI.setOnClickListener(this);

		itemSOUSUOGUSHI.setOnClickListener(this);
		
		itemTIANJIAYIJIAN.setOnClickListener(this);

		itemYIJIANFANKUI.setOnClickListener(this);

		itemWODEGUSHI.setOnClickListener(this);

		itemSHANGCHUANGUSHI.setOnClickListener(this);

		itemGERENXINXI.setOnClickListener(this);

	}

	// 点击按钮显示左边侧滑栏
	public void onClickLiftMenu(View v) {
		resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return resideMenu.dispatchTouchEvent(ev);
	}

	@Override
	public void onClick(View view) {

		if (view == itemYOUERGUSHI) {

			maintitle.setText("幼儿发声训练");

			mContent = new YouergushiFragment();

			switchContent(mContent);

		} else if (view == itemSOUSUOGUSHI) {
			
			maintitle.setText("搜索发声训练");

			mContent = new SousuoFragment();

			switchContent(mContent);

		} else if (view == itemTIANJIAYIJIAN) {

			maintitle.setText("添加意见");

			mContent = new YijianfankuiAddFragment();

			switchContent(mContent);

		} else if (view == itemYIJIANFANKUI) {
			
			maintitle.setText("意见反馈");

			mContent = new YijianfankuiListFragment();

			switchContent(mContent);

		} else if (view == itemWODEGUSHI) {

			maintitle.setText("我的发声训练");

			mContent = new WodegushiListFragment();

			switchContent(mContent);

		} else if (view == itemSHANGCHUANGUSHI) {

			maintitle.setText("上传发声训练");

			mContent = new WodegushiAddFragment();

			switchContent(mContent);

		} else if (view == itemGERENXINXI) {

			maintitle.setText("个人信息");

			mContent = new UserInfoFragment();

			switchContent(mContent);
		}
	}

	private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
		@Override
		public void openMenu() {
			is_closed = false;
			// leftMenu.setVisibility(View.GONE);
		}

		@Override
		public void closeMenu() {
			is_closed = true;
			// leftMenu.setVisibility(View.VISIBLE);
		}
	};

	// What good method is to access resideMenu？
	public ResideMenu getResideMenu() {
		return resideMenu;
	}

	// 监听手机上的BACK键
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 判断菜单是否关闭
			if (is_closed) {
				// 判断两次点击的时间间隔（默认设置为2秒）
				if ((System.currentTimeMillis() - mExitTime) > 2000) {
					Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();

					mExitTime = System.currentTimeMillis();
				} else {
					finish();
					System.exit(0);
					super.onBackPressed();
				}
			} else {
				resideMenu.closeMenu();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void initView() {

		maintitle.setText("幼儿发声训练");

		mContent = new YouergushiFragment();

		// 显示主页面
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mContent).commit();

	}

	public void switchContent(Fragment fragment) {
		// TODO Auto-generated method stub
		mContent = fragment;

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();

		resideMenu.closeMenu();

	}

}
