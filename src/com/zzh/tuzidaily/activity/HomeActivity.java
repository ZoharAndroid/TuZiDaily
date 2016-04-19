package com.zzh.tuzidaily.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zzh.tuzidaily.R;
import com.zzh.tuzidaily.SlidingMenu.SlidingMenu;
import com.zzh.tuzidaily.adapter.NewsListAdapter;
import com.zzh.tuzidaily.domain.NewsListItem;
import com.zzh.tuzidaily.domain.StoriesNewsItems;
import com.zzh.tuzidaily.utils.HttpUtils;
import com.zzh.tuzidaily.utils.MyContants;
import com.zzh.tuzidaily.view.XListView;
import com.zzh.tuzidaily.view.XListView.IXListViewListener;

/**
 * 主界面显示
 * 
 * @author zohar
 * @date 2016年3月24日
 * @version 1.0
 * 
 */
public class HomeActivity extends Activity {
	private static final int FINISH = 1;
	protected static final int LOADINGFINISH = 2;
	private ImageView iv_menu;
	private TextView tv_title;
	private ImageView iv_login;
	private ImageView iv_popupwindow;
	private PopupWindow popWin;
	private LinearLayout ll_root;
	private XListView xlv_show;

	private ArrayList<String> items = new ArrayList<String>();
	private int start = 0;
	private int refreshCnt = 0;

	private ArrayAdapter<String> mAdapter;
	private Handler mHandler;

	private SlidingMenu mSlidingMenu;
	private RelativeLayout rl_menu_login;
	private TextView tv_psycology;
	private TextView tv_usersuggestion;
	private TextView tv_movie;
	private TextView tv_nobored;
	private TextView tv_design;
	private TextView tv_bigcorporation;
	private TextView tv_finance;
	private TextView tv_internetsafe;
	private TextView tv_begingrame;
	private TextView tv_music;
	private TextView tv_cartoon;
	private TextView tv_sport;
	private RelativeLayout rl_title;
	private LinearLayout ll_first;
	
	private List<StoriesNewsItems> news = new ArrayList<StoriesNewsItems>();
	private NewsListAdapter adapter;;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initView();
		getData();//获取网络数据
		initData();
		initClickEvent();
	}

	/**
	 * 从网络上获取数据，JSON数据解析
	 */
	private void getData() {
		new Thread() {
			public void run() {
				try {
					HttpClient httpClient = new DefaultHttpClient();
					HttpGet mHttpGet = new HttpGet(MyContants.BASEURL
							+ MyContants.LATESTNEWSURL);
					HttpResponse mHttpreResponse = httpClient.execute(mHttpGet);
					if (mHttpreResponse.getStatusLine().getStatusCode() == 200) {
						HttpEntity entity = mHttpreResponse.getEntity();
						String reponse = EntityUtils.toString(entity, "utf-8");
						parseJSONWithJSONObject(reponse);
					}
				} catch (Exception e) {
					
				}
			};
		}.start();
		
		
	}
	

	protected void parseJSONWithJSONObject(String reponse) throws JSONException {
		JSONArray jsonObjects  = new JSONObject(reponse).getJSONArray("stories");
		for (int i = 0; i < jsonObjects.length(); i++) {
			JSONObject jsonObject = jsonObjects.getJSONObject(i);
			StoriesNewsItems item  = new StoriesNewsItems();
			item.setImages(jsonObject.getString("images"));
			item.setType(jsonObject.getInt("type"));
			item.setGa_prefix(jsonObject.getString("ga_prefix"));
			item.setTitle(jsonObject.getString("title"));
			item.setId(jsonObject.getInt("id"));
			news.add(item);
System.out.println(item.getImages());
		}
		
	}
 
	private void initData() {

		// 获取条目
		//getItems();
		// 设置XListView中的参数
		xlv_show.setPullLoadEnable(true);
		adapter = new NewsListAdapter(this, news);
		//mAdapter = new ArrayAdapter<String>(this, R.layout.list_item, items);
		xlv_show.setAdapter(adapter);
		mHandler = new Handler();
		// 设置xlistview的监听效果
		xlv_show.setXListViewListener(new IXListViewListener() {
			/**
			 * 下拉刷新
			 */
			@Override
			public void onRefresh() {
				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						start = ++refreshCnt;
						news.clear();
						//items.clear();
						//getItems();
						getData();
						// mAdapter.notifyDataSetChanged();
						adapter = new NewsListAdapter(HomeActivity.this, news);
						//mAdapter = new ArrayAdapter<String>(HomeActivity.this,
						//		R.layout.list_item, items);
						xlv_show.setAdapter(adapter);
						onLoad();
					}
				}, 2000);
			}

			/**
			 * 上拉：加载更多
			 */
			@Override
			public void onLoadMore() {
				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						//getItems();
						getData();
						adapter.notifyDataSetChanged();
						onLoad();
					}
				}, 2000);
			}
		});

	}

	private void onLoad() {
		xlv_show.stopRefresh();
		xlv_show.stopLoadMore();
		xlv_show.setRefreshTime("刚刚");
	}

	/**
	 * 获得显示的条目
	 */
	private void getItems() {
		for (int i = 0; i != 20; ++i) {
			items.add("refresh cnt " + (++start));
		}
	}

	/**
	 * 主界面的点击事件
	 */
	private void initClickEvent() {
		ll_root.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		/**
		 * 图标：点击出现菜单的选项
		 */
		iv_menu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 点击打开侧滑菜单
				mSlidingMenu.toggle();
			}
		});

		/**
		 * 图标：点击注册
		 */
		iv_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this,
						LoginActivity.class);
				startActivity(intent);
			}
		});

		/**
		 * 图标：点击显示popupwindow
		 */
		iv_popupwindow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popWinSet(v);
			}
		});

		/**
		 * 菜单栏的登录按钮
		 */
		rl_menu_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this,
						LoginActivity.class);
				startActivity(intent);
			}
		});

		/**
		 * 点击侧滑菜单栏的首页选项
		 */
		ll_first.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setClickMenuTitleToFirst("首页");
			}
		});

		/**
		 * 侧滑菜单栏：日常心理学
		 */
		tv_psycology.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setClickMenuTitle("日常心理学");
			}
		});

		tv_usersuggestion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setClickMenuTitle("用户推荐日报");
			}
		});

		tv_movie.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setClickMenuTitle("电影日报");
			}
		});

		tv_nobored.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setClickMenuTitle("不准无聊");
			}
		});

		tv_design.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setClickMenuTitle("设计日报");
			}
		});
		
		tv_bigcorporation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setClickMenuTitle("大公司日报");
			}
		});

		tv_finance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setClickMenuTitle("财经日报");
			}
		});

		tv_internetsafe.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setClickMenuTitle("互联网安全");
			}
		});

		tv_begingrame.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setClickMenuTitle("开始游戏");
			}
		});

		tv_cartoon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setClickMenuTitle("动漫日报");
			}
		});

		tv_music.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setClickMenuTitle("音乐日报");
			}
		});

		tv_sport.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setClickMenuTitle("体育日报");
			}
		});
	}

	/**
	 * 点击侧滑菜单栏中的条目信息重新出现到主界面中
	 * 
	 * @param name
	 */
	private void setClickMenuTitle(String name) {
		// 关闭侧滑菜单栏
		mSlidingMenu.closeMenu();
		tv_title.setText(name);
		iv_login.setVisibility(View.INVISIBLE);
		iv_popupwindow.setImageResource(R.drawable.theme_add);
		refreshCnt = 0;
		start = 0;
		news.clear();
		getData();
		initData();
		adapter.notifyDataSetChanged();
	}

	/**
	 * 点击侧滑菜单栏中的条目信息重新出现到主界面中首页
	 * 
	 * @param name
	 */
	private void setClickMenuTitleToFirst(String name) {
		// 关闭侧滑菜单栏
		mSlidingMenu.closeMenu();
		tv_title.setText(name);
		iv_login.setVisibility(View.VISIBLE);
		iv_popupwindow
				.setImageResource(R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha);
		refreshCnt = 0;
		start = 0;
		news.clear();
		//items.clear();
		getData();
		initData();
		adapter.notifyDataSetChanged();
	}

	/**
	 * popupwindow显示设置
	 * 
	 * @param v
	 */
	private void popWinSet(View v) {

		View view = View.inflate(HomeActivity.this, R.layout.popupwindow_home,
				null);
		final TextView tv_nightmode = (TextView) view
				.findViewById(R.id.tv_popupwindow_nightmode);
		TextView tv_setting = (TextView) view
				.findViewById(R.id.tv_popupwindow_setting);

		if (popWin == null) {
			popWin = new PopupWindow(view, -2, -2);
			popWin.setOutsideTouchable(true);// 点击其他的地方就会消除这个popupwindow
			popWin.setFocusable(true);
			popWin.setTouchable(true);
			popWin.setBackgroundDrawable(new BitmapDrawable(getResources()));
			view.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (popWin.isShowing()) {
						popWin.dismiss();
					}
					return false;
				}
			});
		}
		if (popWin.isShowing()) {
			popWin.dismiss();
		} else {
			popWin.showAsDropDown(v, 1, 0);
		}

		/**
		 * popupwindow中的白天/夜间模式
		 */
		tv_nightmode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 首先关闭popupwindow
				closePopWin();
				tv_nightmode.setText("日间模式");

			}
		});
		/**
		 * popupwindow中的设置选项
		 */
		tv_setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 首先关闭popupwindow
				closePopWin();
				// 进入设置界面
				Intent mIntentSetting = new Intent(HomeActivity.this,
						SettingActivity.class);
				startActivity(mIntentSetting);
			}
		});
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		closePopWin();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	/**
	 * 关闭popupwindow
	 */
	private void closePopWin() {
		if (popWin != null && popWin.isShowing()) {
			popWin.dismiss();
			popWin = null;
		}
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingmenu_home_activity);
		iv_login = (ImageView) findViewById(R.id.iv_home_activity_login);
		tv_title = (TextView) findViewById(R.id.tv_home_activity_title);
		iv_menu = (ImageView) findViewById(R.id.iv_home_activity_menu);
		iv_popupwindow = (ImageView) findViewById(R.id.iv_home_activity_popupwindow);
		ll_root = (LinearLayout) findViewById(R.id.ll_home_root);
		xlv_show = (XListView) findViewById(R.id.lv_home_activity_show);
		rl_title = (RelativeLayout) findViewById(R.id.rl_home_activity_title);

		rl_menu_login = (RelativeLayout) findViewById(R.id.rl_menu_login);
		ll_first = (LinearLayout) findViewById(R.id.ll_menu_item_first);
		tv_psycology = (TextView) findViewById(R.id.tv_menu_item_psycology);
		tv_usersuggestion = (TextView) findViewById(R.id.tv_menu_item_usersuggestion);
		tv_movie = (TextView) findViewById(R.id.tv_menu_item_movie);
		tv_nobored = (TextView) findViewById(R.id.tv_menu_item_nobored);
		tv_design = (TextView) findViewById(R.id.tv_menu_item_design);
		tv_bigcorporation = (TextView) findViewById(R.id.tv_menu_item_bigcorporation);
		tv_finance = (TextView) findViewById(R.id.tv_menu_item_finance);
		tv_internetsafe = (TextView) findViewById(R.id.tv_menu_item_internetsafe);
		tv_begingrame = (TextView) findViewById(R.id.tv_menu_item_begingrame);
		tv_music = (TextView) findViewById(R.id.tv_menu_item_music);
		tv_cartoon = (TextView) findViewById(R.id.tv_menu_item_cartoon);
		tv_sport = (TextView) findViewById(R.id.tv_menu_item_sport);

	}
}
