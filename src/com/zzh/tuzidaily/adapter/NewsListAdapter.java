package com.zzh.tuzidaily.adapter;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzh.tuzidaily.R;
import com.zzh.tuzidaily.domain.StoriesNewsItems;
import com.zzh.tuzidaily.utils.HttpUtils;
import com.zzh.tuzidaily.utils.URLAddressTool;

/**
 * ListView的适配器
 * 
 * @author Administrator
 * 
 */
public class NewsListAdapter extends BaseAdapter {
	protected static final int LOADING = 1;
	private Context context;
	List<StoriesNewsItems> items = new ArrayList<StoriesNewsItems>();
	List<String> imageURLAddress = new ArrayList<String>();
	List<byte[]> imageDatas = new ArrayList<byte[]>();

	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case LOADING:
				imageDatas.add((byte[]) msg.obj);
				break;

			default:
				break;
			}
		};
	};
	
	public NewsListAdapter(Context context, List<StoriesNewsItems> news) {
		items = news;
		this.context = context;

		for (StoriesNewsItems item : items) {
			imageURLAddress
					.add(URLAddressTool.URLAddressMake(item.getImages()));
		}
		new Thread() {
			public void run() {

				try {
					for (String imageURL : imageURLAddress) {
						byte[] data = HttpUtils.getImage(imageURL);
						Message msg = Message.obtain();
						msg.what=LOADING;
						msg.obj=data;
						handler.sendMessage(msg);
						
System.out.println(imageDatas.size()+"<<<<<<<<<<");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			};
		}.start();
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	private ViewHolder holder;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			// 如果没有缓存,就重新加载
			convertView = View.inflate(context, R.layout.item_news_home, null);
			holder = new ViewHolder();
			holder.iv_image = (ImageView) convertView
					.findViewById(R.id.iv_item_news_image);
			holder.tv_content = (TextView) convertView
					.findViewById(R.id.tv_item_news_title);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tv_content.setText(items.get(position).getTitle());

System.out.println(imageDatas.size()+">>>>>>>>>>>");
		// 获取图片
		if (!imageDatas.isEmpty()) {
			Bitmap mBitmap = BitmapFactory.decodeByteArray(
					imageDatas.get(position), 0,
					imageDatas.get(position).length);
			holder.iv_image.setImageBitmap(mBitmap);
		}
		return convertView;
	}

	class ViewHolder {
		TextView tv_content;
		ImageView iv_image;
	}
}
