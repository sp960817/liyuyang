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

public class WodegushiEditActivity extends FinalActivity {

	@ViewInject(id = R.id.returnimg, click = "returnClick")
	private ImageView returnimg;

	
	@ViewInject(id = R.id.savebtn, click = "saveBtnClick")
	private Button savebtn;

	@ViewInject(id = R.id.selectimgbtn, click = "selImgBtnClick")
	private Button selectimgbtn;

	@ViewInject(id = R.id.selectfilebtn, click = "selFileBtnClick")
	private Button selectfilebtn;

	@ViewInject(id = R.id.goodsimg)
	private ImageView goodsimg;

	@ViewInject(id = R.id.name)
	private TextView name;

	@ViewInject(id = R.id.description)
	private TextView description;

	@ViewInject(id = R.id.type)
	private Spinner type;

	private String selImgPath = "";

	private String selFilePath = "";

	private String newImgName = "";

	private String newFileName = "";

	MyApplication myApp;

	private static int RESULT_LOAD_IMAGE = 1;

	private static int RESULT_LOAD_FILE = 2;

	List<GoodsType> goodsListType;

	private String[] goodstypeListString;

	private ArrayAdapter<String> adapter;

	String goodsid = "";

	@ViewInject(id = R.id.filepath)
	private TextView filepath;

	Goods goods;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_wodegushi_edit);

		Intent intent = getIntent();

		goodsid = intent.getStringExtra("goodsid");

		initPage();

	}

	public void initPage() {

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams.put("action", "list");

		try {
			finalHttp.get(HttpUtil.BASE_URL + "GoodsTypeService", ajaxParams,
					new AjaxCallBack<Object>() {

						@Override
						public void onSuccess(Object t) {

							String result = (String) t;

							if (result.length() > 0) {

								goodsListType = JSONObject.parseArray(result,
										GoodsType.class);

								if (goodsListType.size() > 0) {
									goodstypeListString = new String[goodsListType
											.size()];
								}
								for (int i = 0; i < goodsListType.size(); i++) {
									goodstypeListString[i] = String
											.valueOf(goodsListType.get(i)
													.getTypename());
								}

								adapter = new ArrayAdapter<String>(
										WodegushiEditActivity.this,
										android.R.layout.simple_spinner_item,
										goodstypeListString);

								adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

								type.setAdapter(adapter);

								type.setVisibility(View.VISIBLE);

								initPage1();

							}

						}
					});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void initPage1() {

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

								goods = JSONObject.parseObject(result,
										Goods.class);

								FinalBitmap bitmap = FinalBitmap
										.create(WodegushiEditActivity.this);

								bitmap.display(goodsimg, HttpUtil.FILEPATH_URL
										+ goods.getImgpath());

								name.setText(goods.getName());

								for (int i = 0; i < goodsListType.size(); i++) {

									if (String
											.valueOf(goods
											.getTypeid()).equals(String
													.valueOf(goodsListType.get(
															i).getId()))) {

										type.setSelection(i, true);

									}
								}

								description.setText(goods.getDescription());
							 
								filepath.setText(goods.getFilepath());

								newImgName = goods.getImgpath();

								newFileName = goods.getFilepath();
							}
						}
					});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void selImgBtnClick(View v) {
		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

		startActivityForResult(i, RESULT_LOAD_IMAGE);
	}

	public void selFileBtnClick(View v) {

		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

		intent.setType("audio/*"); // 选择音频

		intent.addCategory(Intent.CATEGORY_OPENABLE);

		startActivityForResult(intent, RESULT_LOAD_FILE);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {

			Uri selectedImage = data.getData();

			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

			selImgPath = cursor.getString(columnIndex);

			cursor.close();

			if (selImgPath == null) {
				Toast.makeText(WodegushiEditActivity.this, "只能选择SD卡中的图片文件!",
						Toast.LENGTH_LONG).show();
				return;
			}

			FinalHttp finalHttp = new FinalHttp();

			AjaxParams ajaxParams = new AjaxParams();

			try {

				ajaxParams.put("file", new File(selImgPath));

				finalHttp.post(HttpUtil.UPLOAD_URL,

				ajaxParams, new AjaxCallBack<Object>() {
					@Override
					public void onSuccess(Object t) {

						if ((String) t != "error") {

							newImgName = (String) t;

							goodsimg.setImageBitmap(BitmapFactory
									.decodeFile(selImgPath));
						}
					}
				});
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (requestCode == RESULT_LOAD_FILE && resultCode == RESULT_OK
				&& null != data) {

			Uri selectedFile = data.getData();

			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedFile,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

			selFilePath = cursor.getString(columnIndex);

			if (selFilePath == null) {
				Toast.makeText(WodegushiEditActivity.this, "只能选择SD卡中的音频文件!",
						Toast.LENGTH_LONG).show();

				return;
			}
			cursor.close();

			FinalHttp finalHttp = new FinalHttp();

			AjaxParams ajaxParams = new AjaxParams();

			try {

				ajaxParams.put("file", new File(selFilePath));

				finalHttp.post(HttpUtil.UPLOAD_URL,

				ajaxParams, new AjaxCallBack<Object>() {
					@Override
					public void onSuccess(Object t) {

						if ((String) t != "error") {

							newFileName = (String) t;

							filepath.setText(selFilePath);

						}
					}
				});
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void returnClick(View v) {

		WodegushiEditActivity.this.finish();
	}

	public void saveBtnClick(View v) {

		if (newImgName.length() < 1) {
			Toast.makeText(WodegushiEditActivity.this, "发声训练图片不能为空!",
					Toast.LENGTH_LONG).show();
			return;
		}

		if (name.getText().toString().length() < 1) {
			Toast.makeText(WodegushiEditActivity.this, "发声训练名称不能为空!",
					Toast.LENGTH_LONG).show();
			return;
		}

		if (newFileName.length() < 1) {
			Toast.makeText(WodegushiEditActivity.this, "音频文件不能为空!",
					Toast.LENGTH_LONG).show();
			return;
		}

		if (type.getSelectedItem().toString() != "") {

			for (GoodsType gt : goodsListType) {

				if (type.getSelectedItem().toString()

				.equals(gt.getTypename())) {

					goods.setTypeid(String.valueOf(gt.getId()));

				}
			}

		}

		goods.setName(name.getText().toString());

		goods.setImgpath(newImgName);

		goods.setFilepath(newFileName);

		goods.setDescription(description.getText().toString());

		JSONObject paraObj = new JSONObject();

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams.put("action", "editsave");

		ajaxParams.put("goods", JSONObject.toJSONString(goods));

		try {
			finalHttp.get(HttpUtil.BASE_URL + "GoodsService", ajaxParams,
					new AjaxCallBack<Object>() {

						@Override
						public void onSuccess(Object t) {

							String result = (String) t;

							if (result.equals("ok")) {

								Toast.makeText(WodegushiEditActivity.this,
										"保存成功!", Toast.LENGTH_LONG).show();

								name.setText("");

								goodsimg.setImageBitmap(null);

								filepath.setText("");

								description.setText("");

								newImgName = "";

								newImgName = "";

								initPage();

							} else {

								Toast.makeText(WodegushiEditActivity.this,
										"保存失败!", Toast.LENGTH_LONG).show();

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
