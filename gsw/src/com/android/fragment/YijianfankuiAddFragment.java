package com.android.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.android.activity.MainActivity;
import com.android.activity.MyApplication;
import com.android.activity.R;
import com.android.activity.WodegushiEditActivity;
import com.android.activity.YouergushiViewActivity;
import com.android.adapter.WodegushiListAdapter;
import com.android.adapter.WodegushiListAdapter.ViewHolder;
import com.android.common.HttpUtil;
import com.android.domain.Goods;
import com.android.domain.GoodsType;
import com.android.domain.Question;
import com.android.domain.User;

public class YijianfankuiAddFragment extends Fragment {

	@ViewInject(id = R.id.savebtn, click = "saveBtnClick")
	private Button savebtn;

	@ViewInject(id = R.id.addquestion_titletxt)
	EditText addquestion_titletxt;

	@ViewInject(id = R.id.addquestion_qstxt)
	EditText addquestion_qstxt;

	MyApplication myApp;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.yijianfankui_add_fragment,
				container, false);

		FinalActivity.initInjectedView(this, view);

		myApp = (MyApplication) getActivity().getApplication();

		return view;
	}

	public void saveBtnClick(View v) {

		if (addquestion_titletxt.getText().length() < 1) {
			Toast.makeText(getActivity(), "标题不能为空!", Toast.LENGTH_LONG).show();
			return;
		}

		if (addquestion_qstxt.getText().toString().length() < 1) {
			Toast.makeText(getActivity(), "内容不能为空!", Toast.LENGTH_LONG).show();
			return;
		}

		Question question = new Question();

		question.setTitle(addquestion_titletxt.getText().toString());

		question.setContent(addquestion_qstxt.getText().toString());

		question.setCreateusername(myApp.getUser().getLoginname());

		User user = myApp.getUser();

		question.setCreateuserid(user.getId());

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date date = new Date();

		question.setCreatetime(f.format(date));

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams.put("action", "add");

		ajaxParams.put("question", JSONObject.toJSONString(question));

		try {
			finalHttp.get(HttpUtil.BASE_URL + "QuestService", ajaxParams,
					new AjaxCallBack<Object>() {

						@Override
						public void onSuccess(Object t) {

							String result = (String) t;

							if (result.equals("ok")) {

								Toast.makeText(getActivity(), "保存成功!",
										Toast.LENGTH_LONG).show();

								addquestion_titletxt.setText("");

								addquestion_qstxt.setText("");

							} else {

								Toast.makeText(getActivity(), "保存失败!",
										Toast.LENGTH_LONG).show();

							}

						}
					});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Toast.makeText(getActivity(), "保存成功!", Toast.LENGTH_LONG).show();

	}
}
