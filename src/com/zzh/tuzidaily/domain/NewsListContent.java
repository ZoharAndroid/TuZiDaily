package com.zzh.tuzidaily.domain;

import java.util.List;

/**
 * 包含知乎日报API提供的最新的消息
 * 
 * {"date":"20160414","stories":对应的内容在StoriesNewsItems中}
 * 
 * @author Administrator
 *
 */
public class NewsListContent {
	String date;
	List<StoriesNewsItems> stories;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<StoriesNewsItems> getStories() {
		return stories;
	}
	public void setStories(List<StoriesNewsItems> stories) {
		this.stories = stories;
	}
	
}
