package com.android.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.alibaba.fastjson.JSONObject;
import com.android.activity.LoginActivity;
import com.android.activity.MainActivity;
import com.android.activity.MyApplication;
import com.android.activity.R;
import com.android.common.HttpUtil;
import com.android.domain.Goods;
import com.android.domain.GoodsType;
import com.android.domain.User;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class WodegushiAddFragment extends Fragment {
	@ViewInject(id = R.id.savebtn, click = "saveBtnClick")
	private Button savebtn;

	@ViewInject(id = R.id.selectimgbtn, click = "selImgBtnClick")
	private Button selectimgbtn;

	@ViewInject(id = R.id.selectfilebtn, click = "selFileBtnClick")
	private Button selectfilebtn;

	@ViewInject(id = R.id.goodsimg)
	private ImageView goodsimg;

	@ViewInject(id = R.id.name)
	private EditText name;

	@ViewInject(id = R.id.description)
	private EditText description;

	@ViewInject(id = R.id.filepath)
	private TextView filepath;

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.wodegushi_add_fragment,
				container, false);

		FinalActivity.initInjectedView(this, view);

		myApp = (MyApplication) getActivity().getApplication();

		initPage();

		return view;
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
										getActivity(),
										android.R.layout.simple_spinner_item,
										goodstypeListString);

								adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

								type.setAdapter(adapter);

								type.setVisibility(View.VISIBLE);

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
		if (requestCode == RESULT_LOAD_IMAGE
				&& resultCode == getActivity().RESULT_OK && null != data) {

			Uri selectedImage = data.getData();

			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getActivity().getContentResolver().query(
					selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

			selImgPath = cursor.getString(columnIndex);

			cursor.close();

			if (selImgPath == null) {
				Toast.makeText(getActivity(), "只能选择SD卡中的图片文件!",
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

		} else if (requestCode == RESULT_LOAD_FILE
				&& resultCode == getActivity().RESULT_OK && null != data) {

			Uri selectedFile = data.getData();

			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getActivity().getContentResolver().query(
					selectedFile, filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

			selFilePath = cursor.getString(columnIndex);

			if (selFilePath == null) {
				Toast.makeText(getActivity(), "只能选择SD卡中的音频文件!",
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

	public void saveBtnClick(View v) {

		if (newImgName.length() < 1) {
			Toast.makeText(getActivity(), "发声训练图片不能为空!", Toast.LENGTH_LONG)
					.show();
			return;
		}

		if (name.getText().toString().length() < 1) {
			Toast.makeText(getActivity(), "发声训练名称不能为空!", Toast.LENGTH_LONG)
					.show();
			return;
		}

		if (newFileName.length() < 1) {
			Toast.makeText(getActivity(), "音频文件不能为空!", Toast.LENGTH_LONG)
					.show();
			return;
		}

		Goods goods = new Goods();

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

		User user = myApp.getUser();

		goods.setCreateuseid(String.valueOf(user.getId()));

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date date = new Date();

		goods.setCreatetime(f.format(date));

		goods.setIsdel("0");

		JSONObject paraObj = new JSONObject();

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams.put("action", "add");

		ajaxParams.put("goods", JSONObject.toJSONString(goods));

		try {
			finalHttp.get(HttpUtil.BASE_URL + "GoodsService", ajaxParams,
					new AjaxCallBack<Object>() {

						@Override
						public void onSuccess(Object t) {

							String result = (String) t;

							if (result.equals("ok")) {

								Toast.makeText(getActivity(), "保存成功!",
										Toast.LENGTH_LONG).show();

								name.setText("");

								goodsimg.setImageBitmap(null);

								filepath.setText("");

								description.setText("");

								newImgName = "";

								newImgName = "";

								initPage();

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
