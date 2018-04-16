package com.android.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.android.common.HttpUtil;
import com.android.domain.User;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegestActivity extends FinalActivity {

	@ViewInject(id = R.id.regist_btn, click = "registBtnClick")
	private Button registbutton;

	@ViewInject(id = R.id.loginname)
	private EditText loginname;

	@ViewInject(id = R.id.loginpsw)
	private EditText loginpsw;

	@ViewInject(id = R.id.username)
	private EditText username;

	@ViewInject(id = R.id.tel)
	private EditText tel;

	@ViewInject(id = R.id.email)
	private EditText email;

	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.regest);

	}

	public void registBtnClick(View v) {

		if (loginname.getText().toString().length() < 1) {

			Toast.makeText(RegestActivity.this, "登陆账号不能为空!", Toast.LENGTH_LONG)
					.show();
			return;
		}

		if (loginpsw.getText().toString().length() < 1) {
			Toast.makeText(RegestActivity.this, "登陆密码不能为空!", Toast.LENGTH_LONG)
					.show();
			return;
		}
		if (username.getText().toString().length() < 1) {
			Toast.makeText(RegestActivity.this, "姓名不能为空!", Toast.LENGTH_LONG)
					.show();
			return;
		}

		if (tel.getText().toString().length() < 1) {
			Toast.makeText(RegestActivity.this, "电话不能为空!", Toast.LENGTH_LONG)
					.show();
			return;
		}

	    user = new User();

		user.setLoginname(loginname.getText().toString());

		user.setLoginpsw(loginpsw.getText().toString());

		user.setUsername(username.getText().toString());

		user.setTel(tel.getText().toString());

		user.setEmail(email.getText().toString());

		user.setIsdel("0");

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date date = new Date();

		user.setCreatetime(f.format(date));

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams.put("action", "vali");

		ajaxParams.put("user", JSONObject.toJSONString(user));

		try {
			finalHttp.get(HttpUtil.BASE_URL + "UserService", ajaxParams,
					new AjaxCallBack<Object>() {

						@Override
						public void onSuccess(Object t) {

							String result = (String) t;

							if (result.equals("true")) {

								Toast.makeText(RegestActivity.this, "用户名已存在!",
										Toast.LENGTH_LONG).show();
								return;

							} else {
								regest();
							}

						}
					});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void regest() {
		
		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams = new AjaxParams();

		ajaxParams.put("action", "reg");

		ajaxParams.put("user", JSONObject.toJSONString(user));

		try {
			finalHttp.get(HttpUtil.BASE_URL + "UserService", ajaxParams,

			new AjaxCallBack<Object>() {

				@Override
				public void onSuccess(Object t) {

					String result = (String) t;

					if (result.equals("ok")) {

						Toast.makeText(RegestActivity.this, "注册成功!",
								Toast.LENGTH_LONG).show();

						RegestActivity.this.finish();

					}

				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
