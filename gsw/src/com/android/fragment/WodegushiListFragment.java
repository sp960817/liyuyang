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

public class WodegushiListFragment extends Fragment {

	List<Goods> mListViewData = new ArrayList<Goods>();

	List<Goods> goodslist1;

	@ViewInject(id = R.id.lishilv, itemClick = "ItemClick")
	ListView listView;

	private WodegushiListAdapter adapter;

	private FinalDb db;

	MyApplication myApp;

	private String[] opr = new String[] { "删除" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.wodegushi_list_fragment,
				container, false);

		FinalActivity.initInjectedView(this, view);

		myApp = (MyApplication) getActivity().getApplication();

		goodslist1 = new ArrayList<Goods>();

		adapter = new WodegushiListAdapter(getActivity(), goodslist1);

		listView.setAdapter(adapter);

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {

				ViewHolder vHollder = (ViewHolder) view.getTag();

				showOprDialog(String.valueOf(vHollder.goodsid.getText()));

				return true;

			}
		});

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ViewHolder vHollder = (ViewHolder) view.getTag();

				// showOprDialog(String.valueOf(vHollder.goodid.getText()));

				Intent intent = new Intent();

				intent.putExtra("goodsid", vHollder.goodsid.getText());

				intent.setClass(getActivity(), WodegushiEditActivity.class);

				startActivity(intent);
			}
		});

		initData();

		return view;
	}

	public void initData() {

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams.put("action", "mylist");

		ajaxParams.put("userid", String.valueOf(myApp.getUser().getId()));

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

	private void showOprDialog(String id) {

		final String _id = id;

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setTitle("请选择操作:");

		builder.setItems(opr, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				if (which == 0) {

					FinalHttp finalHttp = new FinalHttp();

					AjaxParams ajaxParams = new AjaxParams();

					ajaxParams.put("action", "delete");

					ajaxParams.put("goodsid", _id);

					try {
						finalHttp.get(HttpUtil.BASE_URL + "GoodsService", ajaxParams,
								new AjaxCallBack<Object>() {
									@Override
									public void onSuccess(Object t) {
										
										initData();
										
										Toast.makeText(getActivity(), "删除成功!",
												Toast.LENGTH_LONG).show();
									}
								});

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					

				}

			}
		});

		builder.create().show();
	}
 

	@Override
	public void onResume() {

		super.onResume();

		handler.post(runnable);
	}

	private Runnable runnable = new Runnable() {

		public void run() {
			// 做操作
			handler.sendEmptyMessage(1);
		}
	};

	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {

			case 1:
				// 更新UI
				initData();

				break;
			}
		};
	};
}
