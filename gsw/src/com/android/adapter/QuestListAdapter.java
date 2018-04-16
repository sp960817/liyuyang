package com.android.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.activity.R;
import com.android.domain.Goods;
import com.android.domain.Question;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class QuestListAdapter extends BaseAdapter {
	
	private List<Question> mData;

	private LayoutInflater mInflater;

	public static Map<Integer, Boolean> isSelected;

	private Context context;// 用于接收传递过来的Context对象

	public QuestListAdapter(Context context, List<Question> mData) {

		this.context = context;

		mInflater = LayoutInflater.from(context);

		this.mData = mData;
	}

	@Override
	public int getCount() {
		System.out.print("mData.size()=" + mData.size());
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		// convertView为null的时候初始化convertView。
		if (convertView == null) {

			holder = new ViewHolder();

			convertView = mInflater.inflate(R.layout.yijianfankui_list_item_fragment,
					null);

			holder.title = (TextView) convertView.findViewById(R.id.title);

			holder.id = (TextView) convertView.findViewById(R.id.id);

			holder.id.setVisibility(View.GONE);

			holder.state = (TextView) convertView.findViewById(R.id.state);

			holder.createuser = (TextView) convertView
					.findViewById(R.id.createuser);

			holder.createTime = (TextView) convertView
					.findViewById(R.id.createTime);

			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();

		}

		holder.title.setText(mData.get(position).getTitle());

		holder.id.setText(String.valueOf(mData.get(position).getId()));

		holder.createuser.setText(mData.get(position).getCreateusername());

		String createTime = mData.get(position).getCreatetime();

		String state = mData.get(position).getState();

		if (createTime.length() > 0) {

			createTime = createTime.substring(0, createTime.indexOf(" "))
					.replace("-", "");
		}

		holder.createTime.setText(createTime);

		holder.state.setText(state);

		return convertView;
	}

	public final class ViewHolder {

		public TextView title;

		public TextView id;

		public TextView state;

		public TextView createTime;

		public TextView createuser;
	}

}
