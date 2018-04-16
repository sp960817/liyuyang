package com.android.fragment;

import java.util.ArrayList;
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
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.android.activity.LoginActivity;
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

public class SousuoListFragment extends Fragment {

	List<Goods> mListViewData = new ArrayList<Goods>();

	List<Goods> goodslist1;

	@ViewInject(id = R.id.lishilv, itemClick = "ItemClick")
	ListView listView;
	
	@ViewInject(id = R.id.imageView1, click = "fanhuiBtnClick")
	ImageView imageView1;
	
 	private WodegushiListAdapter adapter;
	
	String type="";
	
	String name="";


	public static SousuoListFragment getInstance(String _name,String _type) {

		SousuoListFragment f = new SousuoListFragment();

		Bundle args = new Bundle();

		args.putString("name", _name);
		
		args.putString("type", _type);

		f.setArguments(args);

		return f;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Bundle args = getArguments();

		if (args != null) {

			name = args.getString("name");
			
			type = args.getString("type");

		}
		
		View view = inflater.inflate(R.layout.sousuo_list_fragment, container,
				false);

		FinalActivity.initInjectedView(this, view);

		goodslist1 = new ArrayList<Goods>();

		adapter = new WodegushiListAdapter(getActivity(), goodslist1);

		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ViewHolder vHollder = (ViewHolder) view.getTag();

				// showOprDialog(String.valueOf(vHollder.goodid.getText()));

				Intent intent = new Intent();

				intent.putExtra("goodsid", vHollder.goodsid.getText());

				intent.setClass(getActivity(),
						YouergushiViewActivity.class);

				startActivity(intent);
			}
		});

		initData();

		return view;
	}

	public void initData() {

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams.put("action", "search");

		ajaxParams.put("name", name);

		ajaxParams.put("type", type);

		try {
			finalHttp.get(HttpUtil.BASE_URL + "GoodsService", ajaxParams,
					new AjaxCallBack<Object>() {

						@Override
						public void onSuccess(Object t) {

							String result = (String) t;

							if (result.length() > 0) {

								List<Goods> goodsList = new ArrayList<Goods>();

								goodsList = JSONObject.parseArray(result,
										Goods.class);// 获取商品集合

								goodslist1.clear();

								if (goodsList.size() > 0) {

									goodslist1.addAll(goodsList);

								}
								adapter.notifyDataSetChanged();

							}

						}
					});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

 

	public void fanhuiBtnClick(View v) {

		MainActivity ra = (MainActivity) getActivity();

		Fragment newContent = new SousuoFragment();

		ra.switchContent(newContent);
	}
}
