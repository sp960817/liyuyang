package com.android.fragment;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.alibaba.fastjson.JSONObject;
import com.android.activity.MyApplication;
import com.android.activity.R;
import com.android.activity.RegestActivity;
import com.android.activity.WodegushiEditActivity;
import com.android.common.HttpUtil;
import com.android.domain.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserInfoFragment extends Fragment {

	@ViewInject(id = R.id.update_btn, click = "updateBtnClick")
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

	MyApplication myApp;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.userinfo_fragment, container,
				false);

		FinalActivity.initInjectedView(this, view);

		myApp = (MyApplication) getActivity().getApplication();

		User user = myApp.getUser();

		loginname.setText(user.getLoginname());

		loginpsw.setText(user.getLoginpsw());

		username.setText(user.getUsername());

		tel.setText(user.getTel());

		email.setText(user.getEmail());

		return view;
	}

	public void updateBtnClick(View v) {

		if (loginpsw.getText().toString().length() < 1) {
			Toast.makeText(getActivity(), "登陆密码不能为空!", Toast.LENGTH_LONG)
					.show();
			return;
		}
		if (username.getText().toString().length() < 1) {
			Toast.makeText(getActivity(), "姓名不能为空!", Toast.LENGTH_LONG).show();
			return;
		}

		if (tel.getText().toString().length() < 1) {
			Toast.makeText(getActivity(), "电话不能为空!", Toast.LENGTH_LONG).show();
			return;
		}

		myApp = (MyApplication) getActivity().getApplication();

		User user = myApp.getUser();

		user.setLoginpsw(loginpsw.getText().toString());

		user.setUsername(username.getText().toString());

		user.setTel(tel.getText().toString());

		user.setEmail(email.getText().toString());
		JSONObject paraObj = new JSONObject();

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams.put("action", "editsave");

		ajaxParams.put("user", JSONObject.toJSONString(user));

		try {
			finalHttp.get(HttpUtil.BASE_URL + "UserService", ajaxParams,
					new AjaxCallBack<Object>() {

						@Override
						public void onSuccess(Object t) {

							String result = (String) t;

							if (result.equals("ok")) {

								Toast.makeText(getActivity(), "修改成功,请重新登陆!",
										Toast.LENGTH_LONG).show();

								getActivity().finish();

							} else {

								Toast.makeText(getActivity(), "修改失败!",
										Toast.LENGTH_LONG).show();

							}

						}
					});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
