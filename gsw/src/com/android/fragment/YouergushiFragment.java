package com.android.fragment;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.alibaba.fastjson.JSONObject;
import com.android.activity.LoginActivity;
import com.android.activity.R;
import com.android.common.HttpUtil;
import com.android.domain.GoodsType;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuInfo;
import com.special.ResideMenu.ResideMenuItem;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

public class YouergushiFragment extends Fragment {

	List<GoodsType> goodsListType;

	FragmentTabHost tabs;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.youergushi_fragment, container,
				false);

		tabs = (FragmentTabHost) view.findViewById(android.R.id.tabhost);

		tabs.setup(getActivity(), getChildFragmentManager(),
				R.id.realtabcontent);
		if (android.os.Build.VERSION.SDK_INT > 10) {
			tabs.getTabWidget().setShowDividers(0);
		}

		initData();

		return view;
	}

	class OnTabChangedListener implements OnTabChangeListener {

		@Override
		public void onTabChanged(String tabId) {
			tabs.setCurrentTabByTag(tabId);
			System.out.println("tabid " + tabId);
			System.out.println("curreny after: " + tabs.getCurrentTabTag());
			updateTab(tabs);
		}
	}

	/**
	 * 更新Tab标签的颜色，和字体的颜色
	 * 
	 * @param tabHost
	 */
	private void updateTab(final TabHost tabHost) {
		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			View view = tabHost.getTabWidget().getChildAt(i);
			TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i)
					.findViewById(R.id.main_tab_label);
			if (tabHost.getCurrentTab() == i) {// 选中
				tv.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.bg_view_pager_scroll_selected));// 选中后的背景

			} else {// 不选中
				tv.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.bg_view_pager_scroll_unselect));// 非选择的背景

			}
		}
	}

	/**
	 * 给Tab按钮设置图标和文字
	 */
	public View getTabItemView(String text) {

		View view = getActivity().getLayoutInflater().inflate(R.layout.tag,
				null);

		TextView textView = (TextView) view.findViewById(R.id.main_tab_label);

		if (text.equals("初始化")) {

			textView.setText(text);

			view.setVisibility(View.GONE);

		} else {

			textView.setText(text);

		}
		return view;
	}

	private void initData() {

		TabSpec tabSpec = tabs.newTabSpec("初始化");

		tabs.setup(getActivity(), getChildFragmentManager(),
				R.id.realtabcontent);

		tabSpec.setIndicator(getTabItemView("初始化"));

		tabs.addTab(tabSpec, IndexFragment.class, null);

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

								for (int i = 0; i < goodsListType.size(); i++) {

									TabSpec tabSpec = tabs.newTabSpec(String
											.valueOf(goodsListType.get(i)
													.getTypename()));

									tabSpec.setIndicator(getTabItemView(String
											.valueOf(goodsListType.get(i)
													.getTypename())));

									Bundle bundle = new Bundle();

									bundle = new Bundle();

									bundle.putString("typeid", String
											.valueOf(goodsListType.get(i)
													.getId()));

									tabs.addTab(tabSpec,
											YouergushiListFragment.class,
											bundle);

								}
								tabs.setCurrentTab(1);

								updateTab(tabs);// 初始化Tab的颜色，和字体的颜色

								tabs.setOnTabChangedListener(new OnTabChangedListener()); // 选择监听器
							}

						}
					});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
