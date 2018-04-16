package com.android.activity;

import com.android.domain.User;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;

public class MyApplication extends Application {

	private User _user;

	public void onCreate() {
		
		super.onCreate();
		
		 setUser(new User());
	}

	public User getUser() {
		return _user;
	}

	public void setUser(User user) {
		_user = user;
	}

}
