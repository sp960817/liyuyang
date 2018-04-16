package com.android.activity;

import com.alibaba.fastjson.JSONObject;
import com.android.common.HttpUtil;
import com.android.domain.User;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends FinalActivity {

	@ViewInject(id = R.id.loginbtn, click = "loginBtnClick")
	private Button loginBtn;

	@ViewInject(id = R.id.registbtn, click = "registBtnClick")
	private Button registbutton;

	@ViewInject(id = R.id.etUsername)
	private EditText loginname;

	@ViewInject(id = R.id.etPwd)
	private EditText loginpsw;

	MyApplication myApp;

	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.login);

		myApp = (MyApplication) getApplication();
	}


	public void registBtnClick(View v) {

		Intent intent = new Intent();

		intent.setClass(LoginActivity.this, RegestActivity.class);

		startActivity(intent);
	}
	
	public void loginBtnClick(View v) {

		if (loginname.getText().toString().length() < 1) {

			Toast.makeText(LoginActivity.this, "登陆账号不能为空!", Toast.LENGTH_LONG)
					.show();
			return;
		}

		if (loginpsw.getText().toString().length() < 1) {
			Toast.makeText(LoginActivity.this, "登陆密码不能为空!", Toast.LENGTH_LONG)
					.show();
			return;
		}

		user = new User();

		user.setLoginname(loginname.getText().toString());

		user.setLoginpsw(loginpsw.getText().toString());

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams.put("action", "login");

		ajaxParams.put("user", JSONObject.toJSONString(user));

		try {
			finalHttp.get(HttpUtil.BASE_URL + "UserService", ajaxParams,
					new AjaxCallBack<Object>() {

						@Override
						public void onSuccess(Object t) {

							String result = (String) t;

							if (result.length() > 0) {

								User user1 = JSONObject.parseObject(result,
										User.class);

								myApp.setUser(user1);

								Intent intent = new Intent();

								intent.setClass(LoginActivity.this,
										MainActivity.class);

								startActivity(intent);

							} else {

								Toast.makeText(LoginActivity.this,
										"请检查用户名和密码是否正确!", Toast.LENGTH_LONG)
										.show();
								return;
							}

						}
					});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
