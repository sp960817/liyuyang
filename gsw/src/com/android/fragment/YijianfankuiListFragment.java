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
import com.android.activity.YijianfankuiViewActivity;
import com.android.adapter.QuestListAdapter;
import com.android.adapter.QuestListAdapter.ViewHolder;
import com.android.common.HttpUtil;
import com.android.domain.Goods;
import com.android.domain.GoodsType;
import com.android.domain.Question;

public class YijianfankuiListFragment extends Fragment {

	List<Question> mListViewData = new ArrayList<Question>();

	List<Question> questionlist1;

	@ViewInject(id = R.id.quest_lv, itemClick = "ItemClick")
	ListView listView;

	private QuestListAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.yijianfankui_list_fragment,
				container, false);

		FinalActivity.initInjectedView(this, view);

		questionlist1 = new ArrayList<Question>();

		adapter = new QuestListAdapter(getActivity(), questionlist1);

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ViewHolder vHollder = (ViewHolder) view.getTag();

				// showOprDialog(String.valueOf(vHollder.goodid.getText()));

				Intent intent = new Intent();

				intent.putExtra("questid",
						String.valueOf(vHollder.id.getText().toString()));

				intent.setClass(getActivity(), YijianfankuiViewActivity.class);

				startActivity(intent);
			}
		});

		initData();

		return view;
	}

	public void initData() {

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams.put("action", "list");

		try {
			finalHttp.get(HttpUtil.BASE_URL + "QuestService", ajaxParams,
					new AjaxCallBack<Object>() {

						@Override
						public void onSuccess(Object t) {

							String result = (String) t;

							if (result.length() > 0) {

								List<Question> questionList = new ArrayList<Question>();

								questionList = JSONObject.parseArray(result,
										Question.class);// 获取商品集合

								questionlist1.clear();

								if (questionList.size() > 0) {

									questionlist1.addAll(questionList);

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

}
