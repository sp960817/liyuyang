package com.android.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.android.common.HttpUtil;
import com.android.domain.Goods;
import com.android.domain.GoodsType;
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
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class YouergushiViewActivity extends FinalActivity {

	private User user;

	@ViewInject(id = R.id.returnimg, click = "returnClick")
	private ImageView returnimg;

	@ViewInject(id = R.id.bofang, click = "bofangClick")
	private Button bofang;

	@ViewInject(id = R.id.goodsimg)
	private ImageView goodsimg;

	@ViewInject(id = R.id.name)
	private TextView name;

	@ViewInject(id = R.id.description)
	private TextView description;

	@ViewInject(id = R.id.type)
	private TextView type;

	@ViewInject(id = R.id.createusername)
	private TextView createusername;
	
	String goodsid="";
	
	String filepath="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_youergushi_view);

		Intent intent = getIntent();

		goodsid = intent.getStringExtra("goodsid");
		
		initPage();

	}

	public void initPage() {

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams.put("action", "view");
		
		ajaxParams.put("goodsid", goodsid);

		try {
			finalHttp.get(HttpUtil.BASE_URL + "GoodsService", ajaxParams,
					new AjaxCallBack<Object>() {

						@Override
						public void onSuccess(Object t) {

							String result = (String) t;

							if (result.length() > 0) {

								Goods goods = JSONObject.parseObject(result,
										Goods.class);

								FinalBitmap bitmap = FinalBitmap
										.create(YouergushiViewActivity.this);

								bitmap.display(goodsimg, HttpUtil.FILEPATH_URL
										+ goods.getImgpath());

								name.setText(goods.getName());

								type.setText(goods.getTypename());

								description.setText(goods.getDescription());

								createusername.setText(goods.getCreateuser());
								
								filepath=goods.getFilepath();
							}
						}
					});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void returnClick(View v) {

		YouergushiViewActivity.this.finish();
	}

	public void bofangClick(View v) {

		Intent intent = new Intent();

		intent.putExtra("filepath", filepath);

		intent.setClass(YouergushiViewActivity.this,
				MusicPlayerActivity.class);

		startActivity(intent);
	}
}
