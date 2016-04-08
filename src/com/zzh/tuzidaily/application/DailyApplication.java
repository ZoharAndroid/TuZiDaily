package com.zzh.tuzidaily.application;

import java.io.File;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

/**
 * 当前的应用程序类
 * 
 * @author Administrator
 * 
 */
public class DailyApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		//初始化UniversalImageLoader框架
		initImageLoader(getApplicationContext());
	}

	/**
	 * 初始化UniversalImageLoader框架
	 * 
	 * @param context
	 */
	private void initImageLoader(Context context) {
		File cacheDir = StorageUtils.getCacheDirectory(context);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPoolSize(3)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.diskCache(new UnlimitedDiskCache(cacheDir)).writeDebugLogs()
				.build();
		ImageLoader.getInstance().init(config);
	}
}
