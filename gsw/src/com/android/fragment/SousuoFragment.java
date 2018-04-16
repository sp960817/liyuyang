package com.android.fragment;

import java.util.List;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.alibaba.fastjson.JSONObject;
import com.android.activity.MainActivity;
import com.android.activity.MyApplication;
import com.android.activity.R;
import com.android.activity.RegestActivity;
import com.android.activity.WodegushiEditActivity;
import com.android.common.HttpUtil;
import com.android.domain.GoodsType;
import com.android.domain.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SousuoFragment extends Fragment {

	@ViewInject(id = R.id.sousuo_btn, click = "sousuoBtnClick")
	private Button sousuo_btn;

	@ViewInject(id = R.id.name)
	private EditText name;

	@ViewInject(id = R.id.type)
	private Spinner type;

	List<GoodsType> goodsListType;

	private String[] goodstypeListString;

	private ArrayAdapter<String> adapter;
	
	String typeid="";

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater
				.inflate(R.layout.sousuo_fragment, container, false);

		FinalActivity.initInjectedView(this, view);

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

	public void sousuoBtnClick(View v) {

		if (name.getText().toString().length() < 1
				&& name.getText().toString().length() < 1) {
			Toast.makeText(getActivity(), "发声训练名称不能为空!", Toast.LENGTH_LONG)
					.show();
			return;
		}
	

		if (type.getSelectedItem().toString() != "") {

			for (GoodsType gt : goodsListType) {

				if (type.getSelectedItem().toString()

				.equals(gt.getTypename())) {

					typeid=String.valueOf(gt.getId());

				}
			}

		}


		MainActivity ra = (MainActivity) getActivity();
	
		Fragment newContent = SousuoListFragment.getInstance(name.getText().toString(),typeid);

		ra.switchContent(newContent);
	}

}
