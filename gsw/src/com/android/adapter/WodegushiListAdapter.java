package com.android.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.activity.R;
import com.android.common.HttpUtil;
import com.android.domain.Goods;

public class WodegushiListAdapter extends BaseAdapter {

	private List<Goods> mData;

	private LayoutInflater mInflater;

	private Context context;// 用于接收传递过来的Context对象

	public WodegushiListAdapter(Context context, List<Goods> mData) {

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

			convertView = mInflater.inflate(
					R.layout.wodegushi_list_item_fragment, null);

			FinalActivity.initInjectedView(this, convertView);

			holder.goodsid = (TextView) convertView.findViewById(R.id.goodsid);

			holder.goodsid.setVisibility(View.GONE);

			holder.goodsimg = (ImageView) convertView.findViewById(R.id.goodsimg);

			holder.title = (TextView) convertView.findViewById(R.id.title);

			holder.createusername = (TextView) convertView
					.findViewById(R.id.createusername);

			holder.createtime = (TextView) convertView
					.findViewById(R.id.createtime);

			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();

		}

		holder.goodsid.setText(String.valueOf(mData.get(position).getId()));

		String imgpath = mData.get(position).getImgpath().toString();

		if (imgpath.length() > 0) {

			FinalBitmap bitmap = FinalBitmap.create(context);

			bitmap.display(holder.goodsimg, HttpUtil.FILEPATH_URL+imgpath);

		}

		holder.title.setText(mData.get(position).getName());

		
		holder.createusername.setText(mData.get(position).getCreateuser());
		
		String createtime1 = (mData.get(position).getCreatetime());

		if (createtime1.length() > 0) {

			Date d = new Date(Long.valueOf(createtime1));

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			createtime1 = sdf.format(d);

		}

		holder.createtime.setText(createtime1);

		return convertView;
	}

	public final class ViewHolder {

		public TextView goodsid;

		public ImageView goodsimg;

		public TextView title;

		public TextView createusername;

		public TextView createtime;

	}
}
