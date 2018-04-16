package com.android.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.android.common.HttpUtil;
import com.android.domain.Goods;
import com.android.domain.GoodsType;
import com.android.domain.Question;
import com.android.domain.User;
import com.android.fragment.YouergushiListFragment;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class YijianfankuiViewActivity extends FinalActivity {

	@ViewInject(id = R.id.returnimg, click = "returnClick")
	private ImageView returnimg;

	@ViewInject(id = R.id.addquestion_titletxt)
	TextView addquestion_titletxt;

	@ViewInject(id = R.id.addquestion_qstxt)
	TextView addquestion_qstxt;

	@ViewInject(id = R.id.addquestion_fktxt)
	TextView addquestion_fktxt;

	String questid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_yijianfankui_view);

		Intent intent = getIntent();

		questid = intent.getStringExtra("questid");

		initPage();

	}

	public void initPage() {

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams.put("action", "view");

		ajaxParams.put("questid", questid);

		try {
			finalHttp.get(HttpUtil.BASE_URL + "QuestService", ajaxParams,
					new AjaxCallBack<Object>() {

						@Override
						public void onSuccess(Object t) {

							String result = (String) t;

							if (result.length() > 0) {

								Question question = JSONObject.parseObject(
										result, Question.class);

								addquestion_titletxt.setText(question
										.getTitle());

								addquestion_qstxt.setText(question.getContent());

								addquestion_fktxt.setText(question
										.getReplaycontent());

							}

						}
					});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	public void returnClick(View v) {

		YijianfankuiViewActivity.this.finish();
	}

}
